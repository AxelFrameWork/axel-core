package org.xmlactions.code.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.xmlactions.pager.actions.Param;

public class ParseParams {
	


	
	protected String removeBrackets(String code) {
		int indexFrom = code.indexOf('(');
		int indexTo = code.lastIndexOf(')');
		
		if (indexFrom < 0 || indexTo < 0 || indexTo < indexFrom) {
			return null;
		}
		
		return code.substring(indexFrom+1, indexTo);
	}
	
	protected List<Param> getParams(String paramsString) {
		List<Param> list = new ArrayList<>();
		if (StringUtils.isBlank(paramsString)) {
			return list;
		}
		int position = 0;
		do {
			Pos pos = getParamString(paramsString, position);
			if (pos != null) {
				Param param = buildParam(pos);
				list.add(param);
			} else {
				break;
			}
			position = pos.getEnd()+1;
		} while (true);
		return list;
	}
	
	protected Pos getParamString(String code, int start) {
		if (start >= code.length()) {
			return null;
		}
		String value = null;
		boolean inString = false;
		int index = start;
		while(index < code.length()) {
			char c = code.charAt(index);
			if (inString) {
				if (c == '"' && code.charAt(index-1) != '\\') {
					inString = false;
				}
			} else {
				if (c == '"') {
					inString = true;
				} else {
					if (c == ',') {
						value = code.substring(start, index);
						break;
					}
				}
			}
			index++;
		}
		if (value == null) {
			value = code.substring(start, index);
		}
		Pos pos = new Pos(start, index, value.trim());
		return pos;
	}
	
	protected Param buildParam(Pos pos) {
		String paramString = pos.getParamString();
		Param param = new Param();
		String type = "object";
		String value = "";
		int quoteStart = 0;
		int colonStart = 0;
		boolean inString = false;
		for (int index = 0; index < paramString.length(); index++) {
			char c = paramString.charAt(index);
			if (inString == false) {
				if (c == '"') {
					if (index > 0) {
						if (paramString.charAt(index-1) != '\\') {
							inString = true;
							quoteStart = index+1;
						}
					} else {
						inString = true;
						quoteStart = index+1;
					}
				} else if (c == ':') {
					colonStart = index;
					if (value.length() == 0) {
						if (quoteStart > 0 ) {
							value = paramString.substring(quoteStart,index-1);
						} else {
							value = paramString.substring(0, index);
						}
					}
					break;
				}
			} else if (c == '"' && paramString.charAt(index-1) != '\\') {
				value = paramString.substring(quoteStart,index); 
				inString = false;
			}
		}
		if (value.length() == 0) {
			value = paramString;
		}
		param.setValue(value.trim());
		if (colonStart > 0) {
			type = paramString.substring(colonStart+1);
		} else if (quoteStart > 0) {
			type = "string";
		} else {
			// not quoted see if double, or number or boolean else string
			type = Param.toNearestType(param.getValue());
		}
		param.setType(type);
		return param;
	}
	

	private class Pos {
		private int start;
		private int end;
		private String paramString;
		
		Pos() {};
		Pos(int start, int end, String paramString) {
			setStart(start);
			setEnd(end);
			setParamString(paramString);
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public String getParamString() {
			return paramString;
		}
		public void setParamString(String paramString) {
			this.paramString = paramString;
		}
		
		public String toString() {
			return(paramString);
		}
	}

}

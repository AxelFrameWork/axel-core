
package org.xmlactions.pager.actions.string;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/**
 * &lt;axel:text_formatter text="123.22" format="%.20"/&gt;
 * @author mike.murphy
 * 
 * TODO - enhance this action to use multiple inputs using List&lt;Param&gt; params
 *
 */
public class TextFormatterAction extends BaseAction {

	private String text;
	private String format;
	private String key;					// if we want to put the result back into the execContext

	public String execute(IExecContext execContext) 
	{
		validate(execContext);
		
		String result = format(getFormat(), getText(execContext));
		
		
		if (StringUtils.isNotBlank(getKey())) {
			execContext.put(getKey(), result);
			return "";
		} else {
			return result;
		}
	}
	
	private String format(String format, String num) {
		String result = num;;
		if (isDouble(num) ) {
			result = tryFormat(format, Double.parseDouble(num));
		} else  if (isFloat(num) ) {
			result = tryFormat(format, Float.parseFloat(num));
		} else if (isNumber(num) ) {
			result = tryFormat(format, Integer.parseInt(num));
		} else {
			result = tryFormat(format, num);
		}
		return result;
	}
	
	private boolean isNumber(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (Exception ex) {}
		return false;
	}
	private boolean isDouble(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (Exception ex) {}
		return false;
	}
	
	private boolean isFloat(String num) {
		try {
			Float.parseFloat(num);
			return true;
		} catch (Exception ex) {}
		return false;
	}
	
	private String tryFormat(String format, Object num) {
		try {
			String s = String.format(format, num);
			return s;
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
	
	private void validate(IExecContext execContext) {
		Validate.notEmpty(text, "The text attribute must be set for this \"text_formatter\" action.");
		Validate.notEmpty(format, "The format attribute must be set for this  \"text_formatter\" action.");
	}

	public String getText(IExecContext execContext) {
		return execContext.replace(getText());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

	
}


package org.xmlactions.pager.actions.mapping;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.json.GsonUtils;
import org.xmlactions.mapping.json.JSONUtils;
import org.xmlactions.web.RequestExecContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JSONGetAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(JSONGetAction.class);

	private Object json_data;
	private String json_path;
	private int index;
    private String row_map_name;
	private String key;

	
	private IExecContext execContext;
	
	public String execute(IExecContext execContext) throws Exception
	{
		Object result  = "";
		this.execContext = execContext;
		Gson gson = new Gson();
		try {
			// remove any existing map from a previous call
			if (StringUtils.isNotEmpty(getRow_map_name())) {
				execContext.getNamedMaps().remove(getRow_map_name());
			}
			JsonElement jsonElement = null;
			Object data = getJson_data();
			try {
				if (data instanceof String) {
					jsonElement = gson.fromJson((String)data, JsonElement.class);
				} else {
					jsonElement = (JsonElement)data;
				}
			} catch (Exception ex) {
				log.error("Unable to process Gson:[" + data + "]:" + ex.getMessage());
				//throw new IllegalArgumentException("Unable to process Gson:[" + data + "]", ex);
				return "";
			}
			
			int [] newRowCount = {getIndex()};
			Object o = GsonUtils.toMap(jsonElement, getJson_path(), getIndex(), newRowCount);
			// check that we've got back a new row comparing the rowCount to newRowCount || a null response.
			if (o == null || (getIndex() != newRowCount[0] && getIndex() >= 0) ) {
				// exceeded limit or no data
			} else if ( o instanceof String) {
				if (StringUtils.isEmpty(getRow_map_name())) {
					if (this.getKey() != null) {
						execContext.put(getKey(), o);
						return "";
					}
					return (String)o;
				} else {
					execContext.put(getRow_map_name(), o);
				}
			} else if ( o instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)o;
				if (StringUtils.isEmpty(getRow_map_name())) {
					if(map.containsKey("row") && map.size() == 1) {
						result = "" + map.get("row");
					} else if (StringUtils.isNotEmpty(getKey())) {
						result = o;
					} else {
						result = gson.toJson(map);
					}
				} else {
					execContext.addNamedMap(getRow_map_name(), map);
//					Set<Entry<String, Object>> set = map.entrySet();
//					for (Entry<String, Object> entry : set) {
//						execContext.put(entry.getKey(), entry.getValue());
//					}
				}
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unable to process json_get action."
					+ " path:" + getJson_path()
					+ " index:" + getIndex()
					+ " data:" + getJson_data(),
					ex);
		}
		if (this.getKey() != null) {
			execContext.put(getKey(), result);
			return "";
		}
		return "" + result;
	}
	
//	public String execute(IExecContext execContext) throws Exception
//	{
//		String result  = "";
//		this.execContext = execContext;
//		Gson gson = new Gson();
//		JsonElement jsonElement = gson.fromJson(getJson_data(), JsonElement.class);
//		Object o = GsonUtils.getPathObjectSlash(jsonElement, getJson_path(), getIndex());
//		if(o instanceof JsonElement) {
//			JsonElement je = (JsonElement)o;
//			if (je.isJsonPrimitive()) {
//				JsonPrimitive jp = (JsonPrimitive)je;
//				result = jp.getAsString();
//			} else {
//				result = je.toString();
//			}
//		} else if ( o == null) {
//			result = "";
//		} else {
//			result = "" + o;
//		}
//		return result;
//	}
//	
	public String toString()
	{

		return "json_get [" + getJson_data() + "] [" + getJson_path() + "] [" + getIndex( )+ "]";
	}

	public Object getJson_data() {
		if (execContext == null) { 
			execContext = RequestExecContext.get();
		}
		if (json_data instanceof String) {
			return execContext.replace((String)json_data);
		}
		return json_data;
	}

	public void setJson_data(Object json_data) {
		this.json_data = json_data;
	}

	public String getJson_path() {
		return json_path;
	}

	public void setJson_path(String json_path) {
		this.json_path = json_path;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getRow_map_name() {
		return row_map_name;
	}

	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}

	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}

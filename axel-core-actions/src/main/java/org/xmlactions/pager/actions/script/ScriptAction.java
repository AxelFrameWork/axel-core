
package org.xmlactions.pager.actions.script;


import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.scripting.NashornJS;
import org.xmlactions.common.scripting.Scripting;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.pager.actions.Param;

/**
 * Takes 2 parameters
 * 1) content of the script action element which is the script to execute
 * 1) attribute key to store result if there is any back into execContext - Optional
 * @author mike.murphy
 *
 */
public class ScriptAction extends BaseAction {

	private static Logger log = LoggerFactory.getLogger(ScriptAction.class);

	private String key;					// if we want to put the result back into the execContext
	
	private String fileName;			// we can use the fileName in place of the script content.

	private String function;			// if this is set we invoke this function
	
	private List<Param> params = new ArrayList<Param>();	// params are used to pass to the loaded script - these can not be used when using the content in place of a fileName.

	public String execute(IExecContext execContext) {
		Object result = null;
		if (fileName != null) {
			result = executeScriptFile(execContext);
		} else {
			result = executeInlineScript(execContext);
		}
		if (StringUtils.isBlank(getKey())) {
			if (result != null) {
				return result.toString();
			} else {
				return "";
			}
		} else {
			if (result == null) {
				execContext.remove(getKey());
			} else {
				execContext.put(getKey(), result);
			}
			return "";
		}
	}
	
	private Object executeInlineScript(IExecContext execContext) {
		try {
			Object result = Scripting.getInstance().evaluate(execContext.replace(XmlCData.removeCData(getContent())));
			return result;
		} catch (Exception ex) {
			throw new IllegalArgumentException("Error processing script:[" + getContent() + "]", ex);
		}
	}
	
	private Object executeScriptFile(IExecContext execContext) {
		Object result = null;
		
		
		try {
			ScriptEngine se = NashornJS.getScriptEngine();
			if (getFunction() != null) {
				result = se.eval("load('" + this.getFileName() + "');");
				Invocable invocable = (Invocable)se;
				if (getParams().size() > 0) {
					result = invocable.invokeFunction(getFunction(), paramsToArray(getParams(), execContext));
				} else {
					result = invocable.invokeFunction(getFunction());
				}
				
				
			} else {
				if (getParams().size() > 0) {
					Bindings bindings = se.createBindings();
					for (Param param : getParams()) {
						bindings.put(param.getKey(), param.getResolvedValue(execContext));
					}
					se.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
				}
				result = se.eval("load('" + this.getFileName() + "');");
			}
			return result;
		} catch (ScriptException ex) {
			throw new IllegalArgumentException("Error processing script:[" + getFileName() + "]", ex);
		} catch (NoSuchMethodException ex) {
			throw new IllegalArgumentException("Error processing script:[" + getFileName() + "]", ex);
		}
	}
	
	private Object[] paramsToArray(List<Param> params, IExecContext execContext) {
		Object [] objs = new Object[params.size()];
		int index = 0;
		for (Param param: params) {
			objs[index] = param.getResolvedValue(execContext);
		}
		return objs;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	public void setParam(Param param) {
		params.add(param);
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	
}

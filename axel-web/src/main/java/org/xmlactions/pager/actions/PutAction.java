
package org.xmlactions.pager.actions;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ReplacementHandlerAction;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class PutAction extends BaseAction implements ReplacementHandlerAction
{

	private static Logger log = LoggerFactory.getLogger(PutAction.class);

	private String key;

	private String value;

	public String execute(IExecContext execContext) throws Exception
	{
		validate();
		Action action = new Action();
		String page = action.processPage(execContext, getContent());
		this.clearActions();
		// execContext.put(getKey(), 
		getReplacementData(execContext, page);
		// execContext.put(getKey(), StrSubstitutor.replace(getContent(), execContext));
		// return 
		return "";
		// setReplacementContent("");
		// return null;
	}
	
	private void validate() {
		if (StringUtils.isEmpty(getKey())) {
			throw new IllegalArgumentException("Missing key for put action.");
		}
	}

	public String toString()
	{

		return "put [" + getKey() + "] = [" + getContent() + "]";
	}

	public void setKey(String key)
	{

		this.key = key;
	}

	public String getKey()
	{

		return key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object getReplacementData(IExecContext execContext, Object innerContent) {
		if (StringUtils.isNotEmpty((String)innerContent)) {
			execContext.put(getKey(), innerContent);
		} else  if (StringUtils.isNotEmpty(getValue())) {
			execContext.put(getKey(),  execContext.replace(getValue()));
		} else {
			execContext.put(getKey(), execContext.replace(getContent()));
		}
		return null;
	}


}

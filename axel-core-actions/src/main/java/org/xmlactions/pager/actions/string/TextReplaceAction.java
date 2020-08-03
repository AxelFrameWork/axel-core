
package org.xmlactions.pager.actions.string;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/**
 * &lt;axel:text_formatter text="123.22" format="%.20"/&gt;
 * @author mike.murphy
 * 
 * TODO - enhance this action to use multiple inputs using List&lt;Param&gt; params
 *
 */
public class TextReplaceAction extends BaseAction {

	private String text;	// this is the input text
	private String pattern;	// this is the pattern that will be replaced. 
	private String replace;	// this is what the pattern will be replaced with.
	private String key;					// if we want to put the result back into the execContext

	public String execute(IExecContext execContext) 
	{
		validate(execContext);
		
		String result = replace(execContext);
		
		
		if (StringUtils.isNotBlank(getKey())) {
			execContext.put(getKey(), result);
			return "";
		} else {
			return result;
		}
	}
	
	private void validate(IExecContext execContext) {
		String error = "";
		if (StringUtils.isBlank(text)) {
			error += "'text'";
		}
		if (StringUtils.isBlank(pattern)) {
			error += " 'pattern'";
		}
		if (null == replace) {
			replace = "";
		}
		if (error.length() > 0) {
			throw new IllegalArgumentException("Milling fields [" + error + "]");
		}
	}
	
	private String replace(IExecContext execContext) {
		
		return(getText().replaceAll(getPattern(), getReplace()));
	}
	
	public String getText() {
		return text;
	}

	public String getText(IExecContext execContext) {
		return execContext.replace(getText());
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}

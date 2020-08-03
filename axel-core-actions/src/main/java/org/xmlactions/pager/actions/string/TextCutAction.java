package org.xmlactions.pager.actions.string;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/*
 * &lt;axel:text_cut text="asdasd" from="0" to="10" key="akey"/&gt;
 */
public class TextCutAction extends BaseAction {

	private static Logger log = LoggerFactory.getLogger(TextCutAction.class);

	private String text;
	private Integer from;	// default = 0
	private Integer to;		// default = end of text
	private String key;

	private String _text;

	public String execute(IExecContext execContext)
	{
		String cut = "";
		validate(execContext);
		if (getFrom() != null && getFrom() >= _text.length()) {
			return "";
		}
		if (getFrom() != null && getTo() != null && getFrom() >= getTo()) {
			return "";
		}
		if (getFrom() != null) {
			if (getTo() != null) {
				cut = _text.substring(getFrom(), getTo());
			} else {
				cut = _text.substring(getFrom());
			}
		} else if (getTo() != null) {
			cut = _text.substring(0, getTo());
		}
		
		if (StringUtils.isNotBlank(getKey())) {
			execContext.put(getKey(), cut);
			cut = "";
		}
		return cut;
	}
	
	private void validate(IExecContext execContext) {
		if (StringUtils.isBlank(getText())) {
			throw new IllegalArgumentException("Missing text attribute for text_cut");
		}
		_text = getText(execContext);

		if (getFrom() == null && getTo() == null) {
			throw new IllegalArgumentException("Must set either from or the to attribure for text_cut");
		}
		
		if (getFrom() != null && getFrom() < 0) {
			setFrom(0);
		}
		
		if (getTo() != null && getTo() > _text.length()) {
			setTo(_text.length());
		}
		if (getTo() != null && getTo() < 0) {
			setTo(0);
		}
	}

	public String toString()
	{
		return ("text_cut(" + getText() + "," + getFrom() + "," + getTo() + ")");
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

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	


}


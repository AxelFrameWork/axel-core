
package org.xmlactions.pager.actions;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Html;
import org.xmlactions.common.text.Text;

public class InsertAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(InsertAction.class);

	/** Name of file we want to load */
	private String page;

	/** Where the web pages are stored */
	private String path;

	/** Namespace used for pager actions. eg. &lt;pager:action...&gt; */
	private String namespace;
	
	/** When adding pages we may want to remove the outer html element. */
	private boolean remove_html = true;	// true or false, yes or no.

	/** this will be the name of the storage map for parameters if used */
	private String param_map_name = "map";

	private List<Param> params = new ArrayList<Param>();

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public void setParam(Param param) {
		params.add((Param) param);
	}

	/**
	 * gets the last param in the list or null if none found.
	 * 
	 * @return
	 */
	public Param getParam() {

		if (params.size() == 0) {
			return null;
		}
		return params.get(params.size() - 1);
	}

	public void setChild(Param param) {
		params.add(param);
	}

	public void _setChild(BaseAction param) {
		Validate.isTrue(param instanceof Param, "Parameter must be a " + Param.class.getName());
		params.add((Param) param);
	}


	public String execute(IExecContext execContext) throws Exception {

		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
		if (namespace == null) {
			namespace = (String) execContext.get(ActionConst.PAGE_NAMESPACE_BEAN_REF);
			if (namespace == null || namespace.trim().length() == 0) {
				namespace = new String(ActionConst.DEFAULT_PAGER_NAMESPACE[0]);
			}
		}
		
		if (getParams() != null && getParams().size() > 0) {
			Map<String, Object> map = Param.toMap(getParams());
			execContext.addNamedMap(getParam_map_name(), map);
		}
		Action action = new Action(path, page, namespace);
		String page = action.processPage(execContext);
		if (isRemove_html()) {
			page = Html.removeOuterHtml(page);
		}
		return page;
	}
	
	public String getPage()
	{

		return page;
	}

	public void setPage(String page)
	{

		this.page = page;
	}

	public String getNamespace()
	{

		return namespace;
	}

	public void setNamespace(String namespace)
	{

		this.namespace = namespace;
	}

	public String getPath()
	{

		return path;
	}

	public void setPath(String path)
	{

		this.path = path;
	}

	public boolean isRemove_html() {
		return remove_html;
	}

	public void setRemove_html(boolean remove_html) {
		this.remove_html = remove_html;
	}

	public String getParam_map_name() {
		return param_map_name;
	}

	public void setParam_map_name(String param_map_name) {
		this.param_map_name = param_map_name;
	}

}

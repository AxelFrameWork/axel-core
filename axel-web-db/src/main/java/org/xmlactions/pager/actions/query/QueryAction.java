package org.xmlactions.pager.actions.query;

import java.io.File;

import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.query.Query;
import org.xmlactions.db.query.QueryBuilder;
import org.xmlactions.mapping.json.JSONUtils;
import org.xmlactions.pager.actions.TransformAction;
import org.xmlactions.pager.actions.form.CommonFormFields;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

public class QueryAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(TransformAction.class);

    /** The xml mapping file that conforms to schema.xsd */
    private String query_xml_file_name;

    /** This is a reference to a query defined in the db_specific database storage schema. */
    private String sql_ref;

    /** This is the query to execute if the sql_ref is not set. */
    private String sql;

    /** Where we store the result of the query. */
    private String key;
    
    /** Set the output - default is xml, can be 'xml' or 'json'. */
    private String output = "xml";

    String path;


    public String execute(IExecContext execContext) throws Exception {

    	int PRETTY_PRINT_INDENT_FACTOR = 2;
        validate(execContext);

        XMLObject xo = processQuery(execContext);
        if (xo != null) {
            String xml = xo.mapXMLObject2XML(xo);
            if (! "xml".equalsIgnoreCase(output)) {
            	JSONObject xmlJSONObj = JSONUtils.mapXmlToJson(xml, true);
        		String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            	execContext.put(getKey(), jsonPrettyPrintString);
            } else {
            	execContext.put(getKey(), xml);
            }
        } else {
            execContext.put(getKey(), null);
        }
        return "";
    }

    public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getQuery_xml_file_name()) && StringUtils.isEmpty(getSql_ref()) && StringUtils.isEmpty(getSql())) {
            throw new IllegalArgumentException("The query_xml_file_name or the sql_ref or the sql attribute must be set.");
        }
        if (StringUtils.isEmpty(getKey())) {
            throw new IllegalArgumentException("Missing key attribute in query");
        }
        if (StringUtils.isEmpty(getStorage_config_ref(execContext))) {
            throw new IllegalArgumentException("Missing storage_config_ref attribute in query");
        }
        if (path == null) {
            path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
        }
    }


    private XMLObject processQuery(IExecContext execContext) {

    	StorageConfig storageConfig = (StorageConfig) execContext.get(getStorage_config_ref(execContext));
        Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext for key ["
                + getStorage_config_ref(execContext) + "]");

        QueryBuilder qb = new QueryBuilder();
        XMLObject xo = null;
        
        if (StringUtils.isNotBlank(getQuery_xml_file_name())) {
	        File file = new File(path, execContext.replace(getQuery_xml_file_name()));
	        if (!file.exists() || file.isDirectory()) {
	            throw new IllegalArgumentException("Missing or invalid file name [" + file.getAbsolutePath()
	                    + "] for query_xml_file_name attribute [" + getQuery_xml_file_name() + "]");
	        }
	        String resourceName = new File(path, execContext.replace(getQuery_xml_file_name())).getAbsolutePath();
	        Query query = qb.loadQuery(resourceName);
	        xo = qb.buildQuery(execContext, storageConfig, query);
        } else {
        	if (StringUtils.isNotBlank(getSql_ref())) {
        		xo = qb.loadFromDB(execContext, storageConfig, getSql_ref(), false);
        	} else {
        		String sql = execContext.replace(getSql());
        		xo = qb.loadFromDBWithSql(execContext, storageConfig, sql, false);
        	}
        }

        return xo;
    }


    public void setQuery_xml_file_name(String query_xml_file_name) {
        this.query_xml_file_name = query_xml_file_name;
    }

    public String getQuery_xml_file_name() {
        return query_xml_file_name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

	/**
	 * @return the sql_ref
	 */
	public String getSql_ref() {
		return sql_ref;
	}

	/**
	 * @param sql_ref the sql_ref to set
	 */
	public void setSql_ref(String sql_ref) {
		this.sql_ref = sql_ref;
	}

	public String getSql() {
		return this.sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getOutput() {
		return this.output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
}
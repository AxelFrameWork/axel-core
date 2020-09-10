package org.xmlactions.pager.actions.props;

import java.util.Iterator;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/*
 * &lt;axel:props file="fileName"/&gt;
 */
public class PropsAction extends BaseAction {

	private static Logger log = LoggerFactory.getLogger(PropsAction.class);

	private String file;


	public String execute(IExecContext execContext) {
		validate(execContext);
		loadProps(execContext, getFile());
		return "";
	}
	
	public void loadProps(IExecContext execContext, String fileName) {
		try {
			CompositeConfiguration config = new CompositeConfiguration();
			//config.addConfiguration(new SystemConfiguration());
			config.addConfiguration(new PropertiesConfiguration(fileName));
			Iterator<String> iterator = config.getKeys();
			while(iterator.hasNext()) {
				String key = iterator.next();
				execContext.put(key, config.getProperty(key));
				// log.debug("key:{}", key);
			}
		} catch (ConfigurationException ex) {
			throw new IllegalArgumentException("Unable to load props for [" + fileName + "]", ex);
		}
	}

	private void validate(IExecContext execContext) {
		if (StringUtils.isBlank(getFile())) {
			throw new IllegalArgumentException("Missing file attribute for props");
		}
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	
	


}


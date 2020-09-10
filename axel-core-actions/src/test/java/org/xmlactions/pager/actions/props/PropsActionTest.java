package org.xmlactions.pager.actions.props;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;

public class PropsActionTest {
	
	private static Logger log = LoggerFactory.getLogger(PropsActionTest.class);

	@Test
	public void loadFileProps() {
		PropsAction pa = new PropsAction();
		pa.setFile("src/test/resources/config/props/prop1.properties");
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		pa.execute(execContext);
		Set<String> set = execContext.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			// log.debug("key:{}", key + " value:" + execContext.get(key));
		}
		assertEquals("This is the value for key 1", execContext.get("prop2.key1"));
	}
	
	@Test
	public void loadHttpProps() {
		PropsAction pa = new PropsAction();
		pa.setFile("http://axelframework.org/code/log4j.properties");
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		pa.execute(execContext);
		Set<String> set = execContext.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			// log.debug("key:{}", key + " value:" + execContext.get(key));
		}
		assertEquals("axel-run.log", execContext.get("log4j.appender.R.File"));
	}
}

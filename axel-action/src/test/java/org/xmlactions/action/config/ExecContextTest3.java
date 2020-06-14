
package org.xmlactions.action.config;


import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionConsts;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.locale.LocaleUtils;
import org.xmlactions.common.theme.Theme;

import com.google.gson.Gson;
import com.google.gson.JsonElement;


public class ExecContextTest3 extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ExecContextTest3.class);
	
	private static final String [] configFiles = {
			"/config/spring/spring-config.xml"
		};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
	
	private String json =
			"{\r\n" + 
			"	\"rates\": [{\n" + 
			"			\"type\": \"FIT\",\n" + 
			"			\"packageType\": \"FIT\",\n" + 
			"			\"price\": {\n" + 
			"				\"currency\": \"GBP\",\n" + 
			"				\"amount\": 42900\n" + 
			"			},\n" + 
			"			\"pkgPrice\": {\n" + 
			"				\"currency\": \"GBP\",\n" + 
			"				\"amount\": 858.00\n" + 
			"			},\n" + 
			"			\"adultPrice\": {\n" + 
			"				\"currency\": \"GBP\",\n" + 
			"				\"amount\": 42900\n" + 
			"			},\n" + 
			"			\"childPrice\": {\n" + 
			"				\"currency\": \"GBP\",\n" + 
			"				\"amount\": 0\n" + 
			"			}\n" +
			"      }]\n" +
			"}";
	

	public void testSplit() throws InvalidObjectException, ConfigurationException, MalformedURLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("splitTestKey", "fred");
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put("splitTestMap", map);
		Object obj = execContext.get("splitTestMap");
		assertNotNull(obj);
		obj = execContext.get("splitTestMap/splitTestKey");
		assertEquals(obj, "fred");
	}
    
	public void testMapArray() throws InvalidObjectException, ConfigurationException, MalformedURLException {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("T1", "fred");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("T1", "barney");
		list.add(map);
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put("list", list);
		Object obj = execContext.get("list:T1");
		assertNotNull(obj);
		assertEquals(obj, "fred");
	}
	
	public void testMapJson() throws InvalidObjectException, ConfigurationException, MalformedURLException {
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		Map<String, Object> map = new HashMap();
		map.put("dara", json);
		execContext.addNamedMap("data", map);
		Object obj = execContext.get("data:dara/rates/type");
		assertNotNull(obj);
		assertEquals("FIT", obj);
		obj = execContext.replace("${data:dara/rates/type}");
		assertNotNull(obj);
		assertEquals("FIT", obj);
	}
	
	public void testJson() {
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		Map<String, Object> map = new HashMap();

		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
		map.put("dara", json);
		execContext.addNamedMap("data", map);
		Object obj = execContext.get("data:dara/rates/type");
		assertEquals("FIT", obj);
		obj = execContext.get("data:dara/rates/price/amount");
		assertEquals(42900,obj);
		obj = execContext.get("data:dara/rates/pkgPrice/amount");
		assertEquals(858.00,obj);
	}
	
    
}

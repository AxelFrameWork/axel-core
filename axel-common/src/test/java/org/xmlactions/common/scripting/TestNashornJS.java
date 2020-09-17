package org.xmlactions.common.scripting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestNashornJS {

	private static final Logger log = LoggerFactory.getLogger(TestNashornJS.class);

	@Test
	public void testCreate() {
		ScriptEngine se = NashornJS.getScriptEngine();
		assertNotNull(se);
	}
	
	@Test
	public void testExecuteJS() throws ScriptException {
		ScriptEngine se = NashornJS.getScriptEngine();
		Object x = se.eval( "var greeting='hello world';" +
							"print(greeting);" +
							"greeting");
		assertNotNull(x);
		assertEquals("hello world", x);
	}

	@Test
	public void testLoadJS() throws ScriptException {
		ScriptEngine se = NashornJS.getScriptEngine();
		Object x = se.eval( "load('classpath:js/test.js');");
		assertNotNull(x);
		assertEquals("hello world", x);
	}
	@Test
	public void testLoadFunctionJS() throws ScriptException {
		ScriptEngine se = NashornJS.getScriptEngine();
		Object x = se.eval("load('classpath:js/test2.js');");
		assertNotNull(x);
		assertEquals("hello world", x);
	}
}

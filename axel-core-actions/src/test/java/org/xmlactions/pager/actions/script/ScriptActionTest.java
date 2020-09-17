package org.xmlactions.pager.actions.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.junit.Test;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.scripting.NashornJS;
import org.xmlactions.pager.actions.Param;

public class ScriptActionTest {
	
	@Test
	public void testInlineScript() {
		
		ScriptAction sa = new ScriptAction();
		sa.setContent(
					"var content = 'Hello World!!!';\n" +
					"print(content);\n" +
					"content"
				);

		IExecContext execContext = new NoPersistenceExecContext(null, null);
		Object obj = sa.execute(execContext);
		assertNotNull(obj);
		assertEquals("Hello World!!!", obj);
	}
	
	
	@Test
	public void testLoadClasspathJS() throws ScriptException {
		
		ScriptAction sa = new ScriptAction();
		sa.setFileName("classpath:js/test.js");
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		Object obj = sa.execute(execContext);
		assertNotNull(obj);
		assertEquals("Hello World!!!", obj);
	}
	
	@Test
	public void testLoadJS() throws ScriptException {
		
		ScriptAction sa = new ScriptAction();
		sa.setFileName("src/test/resources/js/test.js");
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		Object obj = sa.execute(execContext);
		assertNotNull(obj);
		assertEquals("Hello World!!!", obj);
	}
	
	@Test
	public void testBindingJS() throws ScriptException {
		
		ScriptAction sa = new ScriptAction();
		sa.setFileName("src/test/resources/js/testBindings.js");
		Param param = new Param();
		param.setKey("msg");
		param.setValue("My World");
		sa.getParams().add(param);
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		Object obj = sa.execute(execContext);
		assertNotNull(obj);
		assertEquals("Hello My World!!!", obj);
	}
	

}

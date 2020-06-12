package org.xmlactions.pager.actions.string;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

public class TextCutActionTest {

	private static IExecContext execContext;

	@Before
	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	@Test
	public void testFrom() {
		String s = "this is a string";
		
		TextCutAction tca = new TextCutAction();
		tca.setText(s);
		tca.setFrom(0);
		assertEquals(s, tca.execute(execContext));
		tca.setFrom(10);
		assertEquals("string", tca.execute(execContext));
		tca.setFrom(-10);
		assertEquals(s, tca.execute(execContext));
	}

	@Test
	public void testTo() {
		String s = "this is a string";
		
		TextCutAction tca = new TextCutAction();
		tca.setText(s);
		tca.setTo(200);
		assertEquals(s, tca.execute(execContext));
		tca.setTo(16);
		assertEquals(s, tca.execute(execContext));
		tca.setTo(10);
		assertEquals( "this is a ", tca.execute(execContext));
		tca.setTo(0);
		assertEquals( "", tca.execute(execContext));
		tca.setTo(-1);
		assertEquals( "", tca.execute(execContext));
	}

	@Test
	public void testFromTo() {
		String s = "this is a string";
		
		TextCutAction tca = new TextCutAction();
		tca.setText(s);
		tca.setFrom(0);
		tca.setTo(200);
		assertEquals(s, tca.execute(execContext));
		tca.setFrom(1);
		tca.setTo(3);
		assertEquals("hi", tca.execute(execContext));
		tca.setFrom(10);
		tca.setTo(3);
		assertEquals( "", tca.execute(execContext));
		tca.setFrom(15);
		tca.setTo(1000);
		assertEquals( "g", tca.execute(execContext));
		tca.setFrom(-100);
		tca.setTo(1);
		assertEquals( "t", tca.execute(execContext));
		tca.setFrom(-100);
		tca.setTo(4);
		assertEquals( "this", tca.execute(execContext));
		tca.setFrom(4);
		tca.setTo(5);
		assertEquals( " ", tca.execute(execContext));
		tca.setFrom(5);
		tca.setTo(7);
		assertEquals( "is", tca.execute(execContext));
	}

}

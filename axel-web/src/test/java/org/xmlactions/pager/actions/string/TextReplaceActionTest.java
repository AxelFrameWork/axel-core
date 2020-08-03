package org.xmlactions.pager.actions.string;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

public class TextReplaceActionTest {

	private static IExecContext execContext;
	private TextReplaceAction tra = new TextReplaceAction();

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
		String in = "this is a string";
		String out = "this is b string";
		
		TextReplaceAction tra = new TextReplaceAction();
		tra.setText(in);
		tra.setPattern("a");
		tra.setReplace("b");
		String r = tra.execute(execContext);
		assertEquals(out, r);
	}


}

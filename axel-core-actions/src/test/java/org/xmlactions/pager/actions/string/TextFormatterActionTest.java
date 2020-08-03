package org.xmlactions.pager.actions.string;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

public class TextFormatterActionTest {

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
	public void testFormats() {
		String result;
		
		result = format("%.2f", "5");
		assertEquals("5.00", result);
		result = format("£ %.2f", "5.018");
		assertEquals("£ 5.02", result);
		result = format("£ %06.2f", "5.018");
		assertEquals("£ 005.02", result);
		result = format("£ %s", "asdasd");
		assertEquals("£ asdasd", result);
		result = format("£ %2.0f", "asdasd");
		assertEquals("f != java.lang.String", result);
		
	}
	
	private String format(String format, String num) {
		TextFormatterAction nfa = new TextFormatterAction();
		nfa.setFormat(format);
		nfa.setText(num);
		
		return nfa.execute(execContext);
		
	}

	
}

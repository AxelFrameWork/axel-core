package org.xmlactions.common.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class NashornJS {
	
	public final static ScriptEngine getScriptEngine() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		return engine;
	}

}

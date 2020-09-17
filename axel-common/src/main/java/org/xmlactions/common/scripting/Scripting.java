
package org.xmlactions.common.scripting;

import javax.script.ScriptEngine;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;

/**
 * 
 * @author MichaelMurphy
 */
public class Scripting {

    private static Scripting scripting = null;
    // private BSFManager bsfManager;
	private ScriptEngine scriptEngine = NashornJS.getScriptEngine();


    public static Scripting getInstance() {

        if (scripting == null) {

            scripting = new Scripting();
        }
        return scripting;
    }
    
    private Scripting () {
    	scriptEngine = NashornJS.getScriptEngine();
        // bsfManager = new BSFManager();
    }

    /**
     * deprecated replaced with Apache Commons BSF
     * @return a Java V6 ScriptEngine
    public static ScriptEngine getJSEngine() {

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("js");
        return (engine);
    }
     */

    public Object evaluate(String code) throws BSFException {
    	try {
    		// return bsfManager.eval("javascript", "debug infos", 0, 0, code);
    		return this.scriptEngine.eval(code);
    	} catch (Exception ex) {
    		throw new IllegalArgumentException("Error eval [" + code + "]", ex);
    	}
    }

    public void execute(String code) throws BSFException {
    	try {
    		// bsfManager.exec("javascript", "debug infos", 0, 0, code);
    		this.scriptEngine.eval(code);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Error exec [" + code + "]", ex);
		}
    }

}

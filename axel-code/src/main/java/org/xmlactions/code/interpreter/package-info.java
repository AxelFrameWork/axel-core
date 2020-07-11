/**
 * The implementation of the code interpreter.
 * <p>
 * 
 * How the interpreter is intended to work as a scripting language on the pages.
 * <p>
 * 
 * The code script supports the core actions provided by the axel framework.  Based on popularity and over
 * time this may change to become the primary options for implementing the axel framework actions.
 * <p> 
 * 
 * Code script is identified in the page when wrapped in {code{ ... }code} 
 * <p>
 * 
 * Each code section may contain one or more instructions.
 * 
 * <h3>Examples:</h3>
 * 
 * <h5>Get a value from the execContext and store in x</h5>
 * <pre>
 * {code{
 * 	x:string = get("key");
 * }code} 
 * </pre>
 * <p>
 * 
 * <h5>Conditiona</h5>
 * <pre>
 * {code{
 * 	x:int = 20;
 * 	if (x <= 20) {
 * 		result:string = "x <= 20";
 * 	} else if (x <= 30) {
 * 		result:string = "x > 20 && <= 30";
 * 	} else {
 * 		result:string = "x > 30";
 *  }
 * }code} 
 * </pre>
 * <p>
 * 
 * <h5>Evaluate</h5>
 * <pre>
 * {code{
 * 	x:int = 100;
 *  y:int = 10;
 *  z:int = x * y;
 * }code} 
 * </pre>
 * <p>
 * 
 * <h5>Loop</h5>
 * <pre>
 * {code{
 * 	x:int = 100;
 * 	do() {
 *  	x = x + 1;
 *  } while (x < 100);
 * }code} 
 * </pre>
 * <p>
 * 
 */
package org.xmlactions.code.interpreter;

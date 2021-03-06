package org.xmlactions.pager.actions;
/**
\page action_param Params

 Params provides parameters for some of the actions such as \ref action_code_action or \ref action_transform 

 An example of how actions are used
 \code
	<axel:code call="org.xmlactions.utils.Class.methodName">
   		<axel:param value="1" type="int"/>
   		<axel:param value="Zoo" type="String"/>
	</axel:code>
 \endcode

 Params have 3 attributes
 <ul>
 	<li>value</li> - this the value to use for the parameter. Can be used to get parameters from the execContext by using the string replacement pattern i.e. ${request:user_name}  
 	<li>type</li> - an optional type that can be used to force a type for the parameter such as int, bool etc. See the full list below \ref action_param_list_types
 	<li>key</li> - required only if mapping the params to a map, will be used as the key in the map.
 </ul>
 
 \section action_param_list_types param types
 
 List of supported param types
 <ul>
 	<li>boolean</li>
 	<li>byte</li>
 	<li>short</li>
 	<li>int</li>
 	<li>long</li>
 	<li>float</li>
 	<li>double</li>
 	<li>char</li>
 	<li>String</li>
 </ul>

*/


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.utils.Convert;

public class Param extends BaseAction
{
	public enum TypeOption {
        _boolean("boolean", Boolean.class),
        _byte("byte", Byte.class),
        _short("short", Short.class),
        _int("int", Integer.class),
        _long("long", Long.class),
        _float("float", Float.class),
        _double("double", Double.class),
        _char("char", Character.class),
        _string("string", String.class),
        _String("String", String.class),
        _object("object", Object.class);
        
        String type;
        Class<?> clazz;
        TypeOption(String type, Class<?> clazz) {
        	this.type = type;
        	this.clazz = clazz;
        }
        private String getType() {
        	return this.type;
        }
        private Class<?> getClazz() {
        	return this.clazz;
        }
        private TypeOption getTypeOption(String type) {
        	for (TypeOption typeOption : values() ) {
        		if (typeOption.getType().equals(type)) {
        			return typeOption;
        		}
        	}
        	return null;
        }
	}
	
	private String name;
    private String key;
    private String value;
    private String type = TypeOption._String.type;


	public String getValue()
	{
		if (StringUtils.isEmpty(value)) {
			return(getContent());
		}
		return value;
	}

	public void setValue(String value)
	{

		this.value = value;
	}

	public Object getResolvedValue(IExecContext execContext)
	{
        Object obj;

        obj = execContext.get(getValue());
        if (obj == null) {
            obj = StrSubstitutor.replace(getValue(), execContext);
        }
		if (obj == null) {
			obj = getValue();
		}
		if (getType() != null && ! TypeOption._String.type.equals(getType())) {
			// need to convert
			TypeOption typeOption = TypeOption._String.getTypeOption(getType());
			if (typeOption == null) {
				throw new IllegalArgumentException("Invalid type [" + getType() + "] for param.   Refer to schema 'param_converter_types' for a list of options.");
			}
			// now double check that the obj class is not the same as the converter class
			if (obj.getClass() != typeOption.getClazz()) {
				// must convert
				obj = ConvertUtils.convert(obj, typeOption.getClazz());
			}
		}
		return obj;
	}

	public String execute(IExecContext execContext) throws Exception
	{

		return null;
	}

	public String toString() {
		return "" + getType() + ":" + getValue();
	}

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @param params
	 * @return a map with all the parameters. The key will either be the param.name or ("param" + index) - index starting from 1
	 */
	public static Map<String, Object> toMap(List<Param> params) {
		Map<String, Object> map = new HashMap<>();
		for (int index = 0 ; index < params.size(); index++ ) {
			Param param = params.get(index);
			String name = "param" + (index + 1);
			if (StringUtils.isNotEmpty(param.getName())) {
				name = param.getName();
			}
			map.put(name, param.getValue());
		}
		return map;
	}
	
	/**
	 * This attempts to match the object to type.  As double, integer, boolean or string
	 * @param o object to convert
	 * @return the nearest matching type
	 */
	public static String toNearestType(String o) {
		Object x = Convert.toInteger(o);
		if (x == null) {
			x = Convert.toDouble(o);
			if (x == null) {
				x = Convert.toBoolean(o);
				if (x == null) {
					return Param.TypeOption._string.type;
				} else {
					return Param.TypeOption._boolean.type;
				}
			} else {
				return Param.TypeOption._double.type;
			}
		} else {
			return Param.TypeOption._int.type;
		}
	}
	
	public Object convert(Object o) {
		if (getType().equalsIgnoreCase(TypeOption._string.type)) {
			return Convert.toString(o);
		} else if (getType().equals(TypeOption._int.type)) {
			return Convert.toInteger(o);
		} else if (getType().equals(TypeOption._double.type)) {
			return Convert.toDouble(o);
		} else if (getType().equals(TypeOption._long.type)) {
			return Convert.toLong(o);
		}
		return o;
	}
	
}

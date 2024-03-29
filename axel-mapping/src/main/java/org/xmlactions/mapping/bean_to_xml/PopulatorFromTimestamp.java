package org.xmlactions.mapping.bean_to_xml;


import java.sql.Timestamp;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.Element;
import org.xmlactions.mapping.KeyValue;
import org.xmlactions.mapping.MappingConstants;

public class PopulatorFromTimestamp extends AbstractPopulateXmlFromClass {

    public Element performElementAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
    		String namespacePrefix, String elementName, String beanRef) {
        if (object instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) object;
            String output;
            String format = getKeyValue(keyvalues, MappingConstants.TIME_FORMAT);
            if (StringUtils.isNotEmpty(format)) {
                output = DateFormatUtils.format(timestamp.getTime(), format);
            } else {
                output = timestamp.toString();
            }
            Element element = BeanToXmlUtils.addElement(parent, namespacePrefix, elementName);
            element.setText(output);
            return element;
        } else {
            throw new IllegalArgumentException("The object parameter must be a " + Timestamp.class.getName() + " not ["
                    + object.getClass().getName() + "]");
        }
    }

    public Element performAttributeAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
    		String namespacePrefix, String attributeName, String beanRef) {
        if (object instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) object;
            String output;
            String format = getKeyValue(keyvalues, MappingConstants.TIME_FORMAT);
            if (StringUtils.isNotEmpty(format)) {
                output = DateFormatUtils.format(timestamp.getTime(), format);
            } else {
                output = timestamp.toString();
            }
            Element attribute = BeanToXmlUtils.addAttribute(parent, namespacePrefix, attributeName, output);
            return attribute;
        } else {
            throw new IllegalArgumentException("The object parameter must be a " + Timestamp.class.getName() + " not ["
                    + object.getClass().getName() + "]");
        }
    }

}

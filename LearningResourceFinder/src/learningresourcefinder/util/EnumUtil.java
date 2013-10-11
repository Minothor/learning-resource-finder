package learningresourcefinder.util;

import org.apache.commons.beanutils.BeanUtils;


public class EnumUtil {
    static public Object[] getValues(Class enumClass) {
    	return enumClass.getEnumConstants();
    }
    
    static public String getDescription(Object enumValue) {
    	try {
			return (String) BeanUtils.getProperty(enumValue, "description");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

}

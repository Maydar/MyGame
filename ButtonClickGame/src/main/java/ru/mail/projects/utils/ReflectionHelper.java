package ru.mail.projects.utils;

import java.lang.reflect.Field;

public class ReflectionHelper {

	public static Object createInstance(String className) {
		Object object =  new Object();
		try {
			object = Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static boolean setFieldValue(Object object, String fieldName, String value) {
		
			Field field;
			try {
				field = object.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				if(field.getType().equals(String.class)){
					field.set(object, value);
				}
				else if(field.getType().equals(Integer.class)){
						field.set(object, Integer.decode(value));
				}
				field.setAccessible(false);
                return true;
			} catch (Exception e) {
				e.printStackTrace();return false;
			}
	}
	
}

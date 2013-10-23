package ru.mail.projects.utils;

import java.lang.reflect.Field;

public class ReflectionHelper {
	public static Object createInstance(String className) {
		Object object =  new Object();
		try {
			object = Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static void setFieldValue(Object object, String fieldName, String value) {
		
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
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		
	}
	
}

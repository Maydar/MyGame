package ru.mail.projects.utils;

import java.util.HashMap;
import java.util.Map;

public class Context {
	public Map<Class<?>, Object> context = new HashMap<Class<?>, Object>();

	public void add(Class<?> clazz, Object object){
		if(context.containsKey(clazz)){
			throw new IllegalArgumentException("this object exists");
		}
		context.put(clazz, object);
	}

	public Object get(Class<?> clazz){
		return context.get(clazz);
	}
}

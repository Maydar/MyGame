package ru.mail.projects.SAX.parser;


import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import ru.mail.projects.utils.ReflectionHelper;

public class SaxEmptyHandler extends DefaultHandler {
	
	private static String CLASSNAME = "class";
	private String thisElement;
	private Object object;
	
	public Object getObject() {
		return object;
	}
	
	public void startDocument() {
		System.out.println("Start parsing...");
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		System.out.println("Start element: " + qName);
		if(qName != CLASSNAME){
			thisElement = qName;
		}
		else {
			String className = attributes.getValue(0);
			System.out.println("Class name :" + className);
			object = ReflectionHelper.createInstance(className);
		}
	}
	public void  characters(char ch[], int start, int length) {
		if(thisElement != null){
			String value = new String(ch, start, length);
			System.out.println(thisElement + "=" + value);
			ReflectionHelper.setFieldValue(object, thisElement, value);
		}
		
	}
	public void endElement(String uri, String localName, String qName) {
		System.out.println("End element: " + qName);
		thisElement = null;
		
	}
	
	public void endDocument() {
		System.out.println("Parsed successfully!");
	}
	
	
		
}

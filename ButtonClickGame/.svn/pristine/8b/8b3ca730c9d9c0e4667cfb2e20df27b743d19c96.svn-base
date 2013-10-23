package ru.mail.projects.resource.system.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import ru.mail.projects.SAX.parser.SaxEmptyHandler;
import ru.mail.projects.base.Resource;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;

public class ResourceSystemImpl implements ResourceSystem{

	private Map<String, Resource> resources = new HashMap<String, Resource>(); 
	
	private VFS vfs;
	private SaxEmptyHandler saxHandler = new SaxEmptyHandler();
	private SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	private SAXParser parser;

	public ResourceSystemImpl(VFS vfs) {
		this.vfs = vfs;
	}
	
	@Override
	public Resource getResource(String resourcePath) {
	
		
		return resources.get(resourcePath);
		
	}

	@Override
	public void loadResources() {
		try {
			
			parser = parserFactory.newSAXParser();
			String tmpString = "";
			Iterator<String> iterator = vfs.getIterator("");
			if(iterator.next() != vfs.getRoot()){
			while (iterator.hasNext()) {
					
					parser.parse(new File(tmpString = iterator.next()), saxHandler);	
					
					resources.put(tmpString, (Resource)saxHandler.getObject());
				}
				
			
			
			}
	
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
		    } catch (SAXException e) {
		    	e.printStackTrace();
		    } catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	
	
	
}

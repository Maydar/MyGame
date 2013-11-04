package ru.mail.projects.base;

import java.util.Iterator;

public interface VFS {
	
	
	String getRoot();
	
	boolean isExist(String path);
	
	boolean isDirectory(String path);
	
	String getAbsolutePath(String file);
	
	byte[] getBytes(String file);
	
	String getUTF8Text(String file);
	
	Iterator<String> getIterator(String startDir);
}

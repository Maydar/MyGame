package ru.mail.projects.vfs.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import ru.mail.projects.base.VFS;

public class VFSImpl implements VFS {

	String root;
	
	
	public VFSImpl(String root) {
		this.root = root;
	}
	
	@Override
	public String getRoot(){
		return root;
	}
	@Override
	public boolean isExist(String path) {
		
		return new File(path).exists();
	}

	@Override
	public boolean isDirectory(String path) {
		return new File(path).isDirectory();
	}

	@Override
	public String getAbsolutePath(String file) {
		return new File(file).getAbsolutePath();
	}

	@Override
	public byte[] getBytes(String file) {
		byte[] result = null;
		String currentLineString;
		StringBuilder builder = new StringBuilder();
		if(isDirectory(file))
			try {
				throw new IOException("is a Directory");
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			try {
				FileReader fr = new FileReader(file);
				
				BufferedReader br = new BufferedReader(fr);
				
				while((currentLineString = br.readLine()) != null)
					builder.append(currentLineString);
				br.close();
				
				//System.out.println(builder.toString());
				result = builder.toString().getBytes();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}	
		return result;
	}

	@Override
	public String getUTF8Text(String file) {
		Charset charset = Charset.forName("UTF-8");
		ByteBuffer buffer = null;
		String resString = null;
		String currentLineString;
		StringBuilder builder = new StringBuilder();
		
		if(isDirectory(file))
			try {
				throw new IOException("is a Directory");
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			try {
				FileReader fr = new FileReader(file);
				
				BufferedReader br = new BufferedReader(fr);
				
				
				while((currentLineString = br.readLine()) != null)
					builder.append(currentLineString);
				
				br.close();
				//System.out.println(builder.toString());
				
				
				buffer = charset.encode(builder.toString());
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		 try {
			resString = charset.newDecoder().decode(buffer).toString();
			
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		 return resString;
	}

	@Override
	public Iterator<String> getIterator(String startDir) {
		return new FileIterator(startDir);
	}
	
	private class FileIterator implements Iterator<String>{

		private Queue<File> files = new LinkedList<File>();
		
		public FileIterator(String path) {
			files.add(new File(root + path));
		}
		
		@Override
		public boolean hasNext() {
			return !files.isEmpty();
		}

	//	сергей загурский файберы;
		@Override
		public String next() {
			File file = files.peek();
			if(file.isDirectory()){
				for(File subFile : file.listFiles()){
					files.add(subFile);
				}
			}
			return files.poll().getPath();
		}

		@Override
		public void remove() {
			
		}
		
	}
	
	//тесты VFS
	
	
	
	
	

}











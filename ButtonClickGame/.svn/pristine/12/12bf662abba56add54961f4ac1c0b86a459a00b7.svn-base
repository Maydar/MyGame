package ru.mail.projects.tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import ru.mail.projects.base.VFS;
import ru.mail.projects.vfs.impl.VFSImpl;

public class VFSTest {
	public VFS vfs = new VFSImpl("./resources");
	String absPath = "/home/maydar/Майдар данные с линукса/Javaprojects/TowerFight/./static/index.html"; 
	// поменять на свой абсолютный путь
	@Test
	public void testDirectory() {
		assertTrue(vfs.isExist("./static"));
		assertTrue(vfs.isDirectory("./static"));
		assertTrue(absPath.equals(vfs.getAbsolutePath("./static/index.html")));
	}
}

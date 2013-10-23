package ru.mail.projects.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.mail.projects.base.VFS;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.resources.TowerResource;
import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.vfs.impl.VFSImpl;
import ru.mail.projects.base.ResourceSystem;

public class RStests {
	VFS vfs = new VFSImpl("./resources");
	ResourceSystem resourceSystem = new ResourceSystemImpl(vfs);
	DbConnectionResource dbResource;
	TowerResource tResource;
	UserSessResource uResource;
	@Test
	public void test() {
		resourceSystem.loadResources();
		dbResource = (DbConnectionResource)resourceSystem.getResource("./resources/DbConnection.xml");
		tResource = (TowerResource)resourceSystem.getResource("./resources/Tower.xml");
		uResource = (UserSessResource)resourceSystem.getResource("./resources/userSess.xml");
		System.out.println(resourceSystem.getResource("./resources/userSess.xml").toString());
		System.out.println("getPlayTime: " + uResource.getPlayTime());
		System.out.println(resourceSystem.getResource("./resources/Tower.xml").toString());
		System.out.println("getHealth: " + tResource.getHealth());
		System.out.println(resourceSystem.getResource("./resources/DbConnection.xml").toString());
		System.out.println("getDriver: " + dbResource.getDriver());
		System.out.println("getPassword: " +  dbResource.getPassword());
		System.out.println("getUrl: " + dbResource.getUrl());
		System.out.println("getUsername: " + dbResource.getUsername());
		
	}
	
}

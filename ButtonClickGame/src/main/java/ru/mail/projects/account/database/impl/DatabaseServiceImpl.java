package ru.mail.projects.account.database.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.messages.MsgUpdateUserId;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.ThreadHelper;
import ru.mail.projects.utils.UserId;
import ru.mail.projects.base.DatabaseService;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.frontend.impl.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseServiceImpl implements Runnable, DatabaseService {
	
	public  static int count = 0;// рефакторинг ----> atomic`
	private static int id = 0; // убрать статик
	private String name = new String();
	private static  int TICK_TIME = 100;
	private Logger log = Logger.getLogger (FrontendImpl.class.getName ());
	private Address AcsAddr = new Address ();
	private MessageSystem MsgSystem;

	private Map<String, Integer> data = new HashMap <String, Integer>();
	
	//Открываем соединение с базой
	private Connection conn = null;
	private Driver driver;
	private UsersDAO uDAO;
	
	public DatabaseServiceImpl (Context context) {
		
		MsgSystem = (MessageSystem)context.get(MessageSystem.class);
		ResourceSystem rSystem = (ResourceSystem)context.get(ResourceSystem.class);
		DbConnectionResource dbConnInfo = (DbConnectionResource)rSystem.getResource("./resources/DbConnection.xml");
		try {
			String userName = dbConnInfo.getUsername();
			String password = dbConnInfo.getPassword();
			String url = dbConnInfo.getUrl(); 
			driver = (Driver) Class.forName (dbConnInfo.getDriver()).newInstance ();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection (url, userName, password);
			uDAO = new UsersDAO (conn);
		}
		catch (Exception e) {
			System.err.println (e.toString());
			e.printStackTrace();
		}
		setName("DatabaseService" + id);
		id++;
		count++;
	}
	
	public void run () {
		
		while (true) {
			long startTime = System.currentTimeMillis();
			MsgSystem.execForAbonent(this);
			long deltaTime = System.currentTimeMillis() - startTime;
			double load = deltaTime/TICK_TIME;
			if(load < 1)
				ThreadHelper.Sleep(TICK_TIME - deltaTime, log);
			
		}
	}
	
	public Address getAddress () {
		return AcsAddr;
	}
	
	public void FindUser (LongId<SessionId> sessionId, String userName, Address to) {
		
		LongId<UserId> Id = null;
		Integer id_user = new Integer (0);
		
		UsersDataSet uDataSet = uDAO.getByName(userName);
		
		if (uDataSet != null) Id = new LongId<UserId> (uDataSet.getId());
		
		ThreadHelper.Sleep(1000, log);
		MsgSystem.sendMessage 
			(new MsgUpdateUserId (getAddress(), to, sessionId, Id));
	}
	
	public void AddUser (LongId<SessionId> sessionId, String userName, Address to) {
		
		LongId<UserId> Id = null;
		if (uDAO.getByName(userName) == null) {
		
			UsersDataSet uDataSet = new UsersDataSet (1, userName);
			uDAO.add(uDataSet);
			uDataSet = uDAO.getByName(userName);
		
			Id = new LongId<UserId> (uDataSet.getId());
		}
			
		MsgSystem.sendMessage 
			(new MsgUpdateUserId (AcsAddr, to, sessionId, Id));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

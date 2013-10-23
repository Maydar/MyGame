package ru.mail.projects.account.database.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import ru.mail.projects.base.Address;
import ru.mail.projects.base.DatabaseService;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.messages.MsgUpdateUserId;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.ThreadHelper;
import ru.mail.projects.utils.UserId;

public class DatabaseServiceCap implements Runnable, DatabaseService {

	public  static int count = 0; // рефакторинг  как в dataserviceImpl
	private static int id = 0;
	private  String name = new String();
	private static  int TICK_TIME = 100;
	private static Logger log = Logger.getLogger (FrontendImpl.class.getName ());
	private static Address AcsAddr = new Address ();
	private static MessageSystem MsgSystem;
	private Map<String, Integer> data = new HashMap <String, Integer>();
	private static Integer UserId = new Integer (10);
	
	public DatabaseServiceCap (Context context) {
		
		MsgSystem = (MessageSystem)context.get(MessageSystem.class);
		data.put("Yura",    1);
		data.put("Roma",    2);
		data.put("Valera",  3);
		data.put("Nadezda", 4);
		data.put("Pasha",   5);
		data.put("Artem",   6);
		data.put("Olya",    7);
		data.put("Bear",    8);
		data.put("Anna",    9);
		data.put("Stas",   10);
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
		if (data.get(userName) != null) Id = new LongId<UserId> (data.get(userName));
		ThreadHelper.Sleep(1000, log);
		MsgSystem.sendMessage 
			(new MsgUpdateUserId (getAddress(), to, sessionId, Id));
		//System.out.print(MsgSystem.getAddressService().getAddress("Frontend", sessionId));
	}
	
	public void AddUser (LongId<SessionId> sessionId, String userName, Address to) {
		
		LongId<UserId> Id = null;
		if (data.get(userName) == null) {
			
			UserId++;
			data.put(userName, UserId);
			Id = new LongId<UserId> (data.get(userName));
		}
		ThreadHelper.Sleep(1000, log);
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

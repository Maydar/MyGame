package ru.mail.projects.message.system.impl;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.HashMap;

import ru.mail.projects.account.database.impl.DatabaseServiceHibernate;
import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.base.Abonent;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.AddressService;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

enum Abonents{
	Frontend,
	GameMechanics,
	DatabaseService
}

public class AddressServiceImpl implements AddressService {
	private String Name;
	private final String frontend = Abonents.Frontend.toString();
	private String gameMechanics = Abonents.GameMechanics.toString();
	private String databaseService = Abonents.DatabaseService.toString();
	public Map <String, Address> addresses = new HashMap <String, Address> ();
	
	public Address getAddress (String className, LongId<SessionId> sessionId) {	
		if(className == Abonents.Frontend.toString())
			Name = className + (sessionId.getLong() % FrontendImpl.count);
		else
			if(className == Abonents.GameMechanics.toString())
				Name = className + (sessionId.getLong() % GameMechanicsImpl.count);
			else
				if(className == Abonents.DatabaseService.toString())
					Name = className + (sessionId.getLong() % DatabaseServiceImpl.count); 
	
		
				
	    return addresses.get (Name);
	}
	public void SetAddress (Abonent abonent) {
		addresses.put(abonent.getName(), abonent.getAddress());
	}
	
	
}

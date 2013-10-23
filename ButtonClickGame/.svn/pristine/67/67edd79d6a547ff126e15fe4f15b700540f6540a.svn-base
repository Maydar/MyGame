package ru.mail.projects.messages;

import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.DatabaseService;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

public class MsgAddUserId extends MsgToAccountServ {
		
	final private LongId<SessionId> sessionId;
	final private String userName;
	final private Address from;
		
	public MsgAddUserId (Address from, Address to, LongId<SessionId> sessionId, String userName) {
			
		super (from, to);
		this.sessionId = sessionId;
		this.userName = userName;
		this.from = from;
	}
		
	public void exec(DatabaseService accserv) {
		accserv.AddUser(sessionId, userName, from);
		}
	}


package ru.mail.projects.messages;

import ru.mail.projects.base.Address;
import ru.mail.projects.base.Frontend;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;


public class MsgUpdateUserId extends MsgToFrontend {
	
	final private LongId<SessionId> sessionId;
	final private LongId<UserId> userId;
	
	public MsgUpdateUserId (Address from, Address to, LongId<SessionId> sessionId, LongId<UserId> userId) {
		
		super (from, to);
		this.sessionId = sessionId;	
		this.userId = userId;
	}
	
	public void exec (Frontend frontend) {
		frontend.updateUserId (sessionId, userId);
	}
}

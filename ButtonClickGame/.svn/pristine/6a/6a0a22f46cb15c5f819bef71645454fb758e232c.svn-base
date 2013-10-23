package ru.mail.projects.messages;

import ru.mail.projects.base.Address;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;

public class MsgClick extends MsgToGM {

	final private UserSession firstSession;
	final private Address fromAddr;
		
	public MsgClick (Address from, Address to, UserSession firstSess) {
		
		super (from, to);
		firstSession = firstSess;
		fromAddr = from;
	}
	
	public void exec (GameMechanicsImpl gmUser) {
		
		gmUser.DoProgress(firstSession);
	}
	
}

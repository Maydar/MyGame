package ru.mail.projects.messages;

import ru.mail.projects.base.Address;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;

public class MsgStartGame extends MsgToGM {
	
	final private UserSession firstSession;
	final private UserSession secondSession;
	final private Address fromAddr;
	
	public MsgStartGame (Address from, Address to, UserSession firstSess, UserSession secondSess) {
		
		super (from, to);
		firstSession = firstSess;
		secondSession = secondSess;
		fromAddr = from;
	}
	
	public void exec (GameMechanicsImpl gmUser) {
		
		gmUser.startGame(firstSession, secondSession, fromAddr);
	}
}

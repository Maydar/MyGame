package ru.mail.projects.messages;

import ru.mail.projects.base.Abonent;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.Msg;
import ru.mail.projects.game.mechanics.impl.*;

public abstract class MsgToGM extends Msg {
	
	public MsgToGM (Address from, Address to) {
		super (from, to);
	}
	
	public void exec (Abonent abonent) {
		if (abonent instanceof GameMechanicsImpl) exec ((GameMechanicsImpl)abonent);
	}
	
	public abstract void exec (GameMechanicsImpl gmser);

}

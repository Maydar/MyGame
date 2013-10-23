package ru.mail.projects.messages;

import ru.mail.projects.base.Abonent;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.Frontend;
import ru.mail.projects.base.Msg;
import ru.mail.projects.frontend.impl.FrontendImpl;


public abstract class MsgToFrontend extends Msg {
	
	public MsgToFrontend (Address from, Address to) {
		super (from, to);
	}
	
	public void exec (Abonent abonent) {
		if (abonent instanceof FrontendImpl) {  //Проверяем, является ли abonent типом FrontEnd
			exec ((FrontendImpl)abonent);
		}
	}
	
	public abstract void exec (Frontend frontend);
}

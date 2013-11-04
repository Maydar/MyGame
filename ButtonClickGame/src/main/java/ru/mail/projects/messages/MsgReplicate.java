package ru.mail.projects.messages;

import ru.mail.projects.base.Address;
import ru.mail.projects.base.Frontend;
import ru.mail.projects.frontend.impl.FrontendImpl;

public class MsgReplicate extends MsgToFrontend {

	public MsgReplicate (Address from, Address to) {
		
		super (from, to);
	}
	
	public void exec (FrontendImpl frontend) {
		
		frontend.replicateFromGM ();
	}
}

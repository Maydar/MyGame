package ru.mail.projects.base;

import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

public interface DatabaseService extends Abonent{
	public void AddUser (LongId<SessionId> sessionId, String userName, Address from);
	public void FindUser (LongId<SessionId> sessionId, String userName, Address from);
	public Address getAddress ();
}

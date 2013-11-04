package ru.mail.projects.base;

import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.utils.GameId;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.UserId;

public interface GameMechanics extends Abonent {
	
	public Address getAddress();
	public void startGame(UserSession firstUs, UserSession secondUs, Address to);
	public void endGame();
	

}

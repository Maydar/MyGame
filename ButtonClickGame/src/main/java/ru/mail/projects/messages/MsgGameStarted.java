package ru.mail.projects.messages;

import java.util.concurrent.atomic.AtomicInteger;

import ru.mail.projects.base.Address;
import ru.mail.projects.base.Frontend;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;

//Сообщение на frontend о том, что игра уже началась
public class MsgGameStarted extends MsgToFrontend {
	
	final private UserSession firstSessionId;
	final private UserSession secondSessionId;
	final private AtomicInteger gameSessionId;
	
	public MsgGameStarted (Address from, Address to, 
						   UserSession firstSess, UserSession secSess, AtomicInteger gameSessId) {
		
		super (from, to);
		firstSessionId = firstSess;
		secondSessionId = secSess;
		gameSessionId = new AtomicInteger (gameSessId.get());
	}
	
	public void exec (FrontendImpl frontend) {
		
		System.out.println ("Diesel");
		frontend.showStartedGame(firstSessionId, secondSessionId, gameSessionId.get());
	}
    public Integer getGameSessionId() {
        return gameSessionId.get();
    }


    public UserSession getFirstSession() {

        return firstSessionId;
    }

}

package ru.mail.projects.frontend.impl;

import java.util.concurrent.atomic.AtomicInteger;

import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;

public class UserSession {
	
	public LongId<UserId> userId;
	public String userName;
	public LongId<SessionId> sessionId;
	static AtomicInteger sessNum = new AtomicInteger ();
	//Показывает, идет игра или нет
	public boolean game_state;
	//Показывает, ожидает ли пользователь след. игры
	public boolean is_wait;  
	public int timeToFinish;
	public int clickByUser;
	public String enemyName;
	public int clickedByEnemy;
	public int IdGameSession;
	private static UserSessResource usr;
	
	public UserSession () {
		sessNum.incrementAndGet();
		sessionId = new LongId<SessionId> (sessNum.get());
		usr = (UserSessResource)FrontendImpl.localRs.getResource("./resources/userSess.xml"); 
		timeToFinish = usr.getPlayTime(); //Время игры в милисекундах
		clickByUser = 0;
		clickedByEnemy = 0;
	}
	
	public static int getPlayTime(){
		return usr.getPlayTime();
	}
}

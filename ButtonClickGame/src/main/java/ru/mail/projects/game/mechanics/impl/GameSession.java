package ru.mail.projects.game.mechanics.impl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.utils.GameId;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.UserId;

public class GameSession {
	private LongId<GameId> gameId;
	static AtomicInteger gameSessNum = new AtomicInteger();
	private UserSession firstUserId;
	private UserSession secondUserId;
//	private LongId<UserId> currentStepUserId;
	private int startTime = 0;
	GameState gameState;
	private static UserSessResource usr;
	
	public GameSession(UserSession firstUser, UserSession secondUser){
		usr = (UserSessResource)GameMechanicsImpl.localRs.getResource("./resources/userSess.xml");
		firstUserId = firstUser;
		secondUserId = secondUser;
		gameSessNum.incrementAndGet();
		gameId = new LongId<GameId>(gameSessNum.get());
		setGameState (GameState.started);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
		      
			public void run() {
				 gameState = GameState.endedFinished;
			}
		};
		
		timer.schedule( task, usr.getPlayTime());  //Время одной игры
		
	}

    public GameSession(UserSession firstUser, UserSession secondUser, UserSessResource resource){
        usr = resource;
        firstUserId = firstUser;
        secondUserId = secondUser;
        gameSessNum.incrementAndGet();
        gameId = new LongId<GameId>(gameSessNum.get());
        setGameState (GameState.started);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {
                gameState = GameState.endedFinished;
            }
        };

        timer.schedule( task, usr.getPlayTime());  //Время одной игры

    }

	public void setGameState(GameState newGameState){
		gameState = newGameState;
	}

    public GameState getGameState(){
        return gameState;
    }
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public UserSession getSecondUserId() {
		return secondUserId;
	}
	
	public UserSession getFirstUserId() {
		return firstUserId;
	}
	
	public LongId<GameId> getGameId() {
		return gameId;
	}
	
	public void SetClickedByEnemyNumFirst () {
		
		firstUserId.clickedByEnemy += 1;
	}
	
	public void SetClickedByEnemyNumSecond () {
		
		secondUserId.clickedByEnemy += 1;
	}
	
}

package ru.mail.projects.game.mechanics.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import ru.mail.projects.base.Address;
import ru.mail.projects.base.GameMechanics;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.messages.MsgGameStarted;
import ru.mail.projects.messages.MsgReplicate;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.GameId;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.ThreadHelper;
import ru.mail.projects.utils.UserId;

public class GameMechanicsImpl implements Runnable, GameMechanics{

	public  static int count = 0;
	private  static int id = 0; 
	private  String name;
	private static int TICK_TIME = 100;
	private Logger log = Logger.getLogger(GameMechanicsImpl.class.getName());
	public static AtomicInteger gameSessId = new AtomicInteger ();
	
	private MessageSystem MsgSystem;
	static ResourceSystem localRs;
	private VFS vfs;
	//resource
	private static Address GmAddr = new Address();
	public Map<Integer, GameSession> gameSessions = new HashMap<Integer, GameSession>();
	
	
	public GameMechanicsImpl(Context context){
		vfs = (VFS)context.get(VFS.class);
		localRs = (ResourceSystem)context.get(ResourceSystem.class);
		MsgSystem = (MessageSystem)context.get(MessageSystem.class);
		setName("GameMechanics" + id);
		id++;
		count++;
	}
	@Override
	public Address getAddress() {
		return GmAddr;
	}

	@Override
	public void startGame(UserSession firstSess, UserSession secondSess, Address to) {
		
		//Создаем игровую сессию и ложим ее в карту
		gameSessions.put(gameSessId.incrementAndGet(), new GameSession (firstSess, secondSess));
		MsgSystem.sendMessage(new MsgGameStarted (GmAddr, to, firstSess, secondSess, gameSessId));
	}
	
	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}
	
	public void DoProgress (UserSession userSessClick) {
		
		
		GameSession curGameSess = gameSessions.get(userSessClick.IdGameSession);
		if (curGameSess.gameState == GameState.endedFinished) {
			return;
		}
		
		userSessClick.clickByUser += 1;
		if (curGameSess.getFirstUserId() == userSessClick) curGameSess.SetClickedByEnemyNumSecond();
		else curGameSess.SetClickedByEnemyNumFirst();
	}
	
	public void replicateGamesToFrontEnd () {

		LongId<SessionId> sessionId = new LongId<SessionId> (1);
		Address frEndAddr = MsgSystem.getAddressService().getAddress("Frontend", sessionId);
		MsgSystem.sendMessage(new MsgReplicate (GmAddr, frEndAddr));
	}

    @Override
	public void run() {
		while (true) {
           act();
		}
	}

    public  boolean act() {
        long startTime = System.currentTimeMillis();
        MsgSystem.execForAbonent(this);
        replicateGamesToFrontEnd ();
        long deltaTime = System.currentTimeMillis() - startTime;
        double load = deltaTime/TICK_TIME;
        if(load < 1){
            ThreadHelper.Sleep(TICK_TIME - deltaTime, log);
            return false;
        } else
            return true;

    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	



}

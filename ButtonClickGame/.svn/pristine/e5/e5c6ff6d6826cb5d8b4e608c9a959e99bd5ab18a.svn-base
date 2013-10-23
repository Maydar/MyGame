package ru.mail.projects.frontend.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.Frontend;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;

import ru.mail.projects.messages.MsgClick;

import ru.mail.projects.messages.MsgGetUserId;
import ru.mail.projects.messages.MsgAddUserId;
import ru.mail.projects.messages.MsgStartGame;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.ThreadHelper;
import ru.mail.projects.utils.UserId;

public class FrontendImpl extends AbstractHandler implements Runnable, Frontend {

	public static int count = 0;
	
	public static AtomicInteger waitUsersNumber = new AtomicInteger (0);//Количество пользователей, ждущих пары на frontend 
	//Id сессии пользователя, который ждет соединения
	private LongId<SessionId> waitSess = new LongId<SessionId> (-1);
	private static int id = 0;
	private  String name;                                    //Переделать балансировщик
	private final static int TICK_TIME = 100;
	private Logger log = Logger.getLogger(FrontendImpl.class.getName());
	public Map<Integer, UserSession> sessions = new HashMap<Integer, UserSession>();
	public MessageSystem MsgSystem;
	private static Address FrAddr = new Address();
	private static PageGeneratorImpl PageGen = new PageGeneratorImpl ();
	static ResourceSystem localRs;
	
	enum AuthorState { End, NotEnd; }  
	
	
	public FrontendImpl(Context context) {
		localRs = (ResourceSystem)context.get(ResourceSystem.class);
		MsgSystem = (MessageSystem)context.get(MessageSystem.class);
		setName("Frontend" + id);
		id++;
		count++;
	}

	@Override
	public Address getAddress() {
		return FrAddr;
	}

	public void run() {

		while (true) {
			long startTime = System.currentTimeMillis();
			MsgSystem.execForAbonent(this);
			long deltaTime = System.currentTimeMillis() - startTime;
			double load = deltaTime/TICK_TIME;
			if(load < 1)
				ThreadHelper.Sleep(TICK_TIME - deltaTime, log);
		}
	}

	public void updateUserId(LongId<SessionId> sessionId, LongId<UserId> userId) {

		UserSession userSes = sessions.get(sessionId.getLong());
		if (userSes == null) {
			System.out.println("Error!!!");
			System.out.println(sessionId.getLong());
		}

		if (userId != null)
			userSes.userId = userId;
		else
			userSes.userId = new LongId<UserId>(-1); // Это означает ошибку
	}
	
	//Показывает, что игра началась
	public void showStartedGame (UserSession firstSession, UserSession secondSession, int idGameSession) {
		
		firstSession.game_state = true;
		secondSession.game_state = true;
		firstSession.is_wait = false;
		secondSession.is_wait = false;
		//Узнаем имя своего противника
		firstSession.enemyName = secondSession.userName;
		secondSession.enemyName = firstSession.userName;
		firstSession.IdGameSession = idGameSession;
		secondSession.IdGameSession = idGameSession;
		waitUsersNumber.decrementAndGet();
		waitUsersNumber.decrementAndGet();
	}
	
	public void replicateFromGM () {
		
		for (UserSession usSes : sessions.values()) {
			if (usSes.game_state == true && usSes.timeToFinish > 0) usSes.timeToFinish -= 100;
		}
	}

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if (doAuthentification (baseRequest, request, response) == AuthorState.NotEnd) return;
		startGame (baseRequest, request, response);
	}
	
	public void startGame (Request baseRequest, HttpServletRequest request,
									HttpServletResponse response) throws IOException {
		
		String IsGame = request.getParameter("StGame");
		String Sess = request.getParameter("Id");
		String PlayGame = request.getParameter("PlayGame");
		//String 
		
		LongId<SessionId> idSess = new LongId<SessionId> (Integer.parseInt(Sess)); 
		UserSession usSess = sessions.get(idSess.getLong());
		//Это когда мы щелкнули на кнопку
		if (PlayGame != null) {
			
			MsgSystem.sendMessage(new MsgClick(getAddress(), 
					MsgSystem.getAddressService().getAddress("GameMechanics", idSess),
					usSess));
			PageGen.generateGamePage(sessions.get(idSess.getLong()), response, baseRequest);
			return;
		}
		if (IsGame.equals("1") && usSess.is_wait == false) {
			
			waitUsersNumber.incrementAndGet();
			usSess.is_wait = true;
			usSess.clickByUser = 0;
			usSess.clickedByEnemy = 0;
			usSess.timeToFinish = UserSession.getPlayTime();
			//В случае, если у нас уже набралось 2 игрока
			if (waitUsersNumber.get() == 1) {
				
				waitSess = usSess.sessionId;
			}
			
			if (waitUsersNumber.get() == 2) {
				
				//Отправляем на игровую механику сообщение о начале игры
				MsgSystem.sendMessage(new MsgStartGame(getAddress(), 
						MsgSystem.getAddressService().getAddress("GameMechanics", idSess),
						usSess, sessions.get(waitSess.getLong())));
			}
		}
		if (sessions.get(idSess.getLong()).game_state == true) 
			PageGen.generateStartGamePage(sessions.get(idSess.getLong()), response, baseRequest);
		
		else PageGen.generateWaitPage(sessions.get(idSess.getLong()), response, baseRequest); 
	}
	
	public AuthorState doAuthentification (Request baseRequest, HttpServletRequest request,
									HttpServletResponse response) 
									throws IOException, ServletException {
		
		//Запрашиваем нужные параметры при аутентификации 
		String Login = request.getParameter("login"); 
		String Reg = request.getParameter("Reg");
		String RegLogin = request.getParameter("Reglogin");
		
		//Это выводится первоначально при регистрации
		if (request.getParameter("StGame") != null || request.getParameter("PlayGame") != null || 
			request.getParameter("ContinueGame") != null)
			return AuthorState.End; 
		if (Login == null && Reg == null && RegLogin == null) showStartPage (baseRequest, response, request);
		//Выводим форму для регистрации
		else if (Reg != null) showRegistrationForm (baseRequest, request, response); 
		//Делаем авторизацию
		else if (Login != null) autorizeUser (baseRequest, request, response, Login);
		//Делаем регистрацию
		else if (RegLogin != null) registrateUser (baseRequest, request, response, RegLogin);
		return AuthorState.NotEnd;
	}
	
	public void showStartPage (Request baseRequest, HttpServletResponse response,
							   HttpServletRequest request)
				throws IOException, ServletException 	
	{
		
		UserSession UsSess = new UserSession();
		sessions.put(UsSess.sessionId.getLong(), UsSess);
		
		
		PageGen.generateStartPage(UsSess, response, baseRequest);
	}
	
	public void showRegistrationForm (Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) 
			throws IOException {
		
		String StringId = request.getParameter("id");
		PageGen.generateRegistrationPage(StringId, response, baseRequest); 
	}
	
	public void autorizeUser (Request baseRequest, HttpServletRequest request,
							  HttpServletResponse response, String Login) 
							  throws IOException	{
		
		String StringId = request.getParameter("id");
		Integer Id = Integer.parseInt(StringId);
		LongId<SessionId> sessionId = new LongId<SessionId>(Id);

		MsgSystem.sendMessage(new MsgGetUserId(getAddress(), 
				MsgSystem.getAddressService().getAddress("DatabaseService", sessionId),
				sessionId, Login));
		(sessions.get(sessionId.getLong())).userName = new String(Login);
		
		PageGen.generateResponsePage(sessions.get(sessionId.getLong()), response, baseRequest, Login);
	}
	
	public void registrateUser (Request baseRequest, HttpServletRequest request,
			  					HttpServletResponse response, String RegLogin)
								throws IOException	{
		
		String StringId = request.getParameter("id");
		Integer Id = Integer.parseInt(StringId);
		LongId<SessionId> sessionId = new LongId<SessionId>(Id);
		
		MsgSystem.sendMessage(new MsgAddUserId(getAddress(), 
				MsgSystem.getAddressService().getAddress("DatabaseService", sessionId),
				sessionId, RegLogin));
		(sessions.get(sessionId.getLong())).userName = new String(RegLogin);
		
		PageGen.generateResponseRegistrPage(sessions.get(sessionId.getLong()), response, baseRequest, RegLogin, StringId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

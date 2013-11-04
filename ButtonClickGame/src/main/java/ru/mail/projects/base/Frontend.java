package ru.mail.projects.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;

public interface Frontend extends Abonent{
	
	
	public Address getAddress();
	public void updateUserId(LongId<SessionId> sessionId, LongId<UserId> userId);
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void showStartedGame (UserSession firstSession, UserSession secondSession, int idGameSession);
	public void replicateFromGM ();
}

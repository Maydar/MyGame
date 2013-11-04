package ru.mail.projects.base;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ru.mail.projects.frontend.impl.UserSession;

public interface PageGenerator {
	
	public void generateResponsePage     (UserSession UserSes, HttpServletResponse response,
			                              Request baseRequest, String Login) throws IOException;
	public void generateResponseRegistrPage (UserSession UserSes, HttpServletResponse response,
			                                 Request baseRequest, String login, String stringId) throws IOException;
	public void generateStartPage (UserSession UserSes, HttpServletResponse response,
			  					   Request baseRequest) throws IOException;
	public void generateRegistrationPage (String StringId, HttpServletResponse response,
			                              Request baseRequest) throws IOException;
	public void generateStartGamePage (UserSession UserSes, HttpServletResponse response,
			  						   Request baseRequest) throws IOException;
}

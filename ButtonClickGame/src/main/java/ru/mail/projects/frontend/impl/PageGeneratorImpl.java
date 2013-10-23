package ru.mail.projects.frontend.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ru.mail.projects.base.PageGenerator;

public class PageGeneratorImpl implements PageGenerator {
	
	public PageGeneratorImpl () {
		
	}
	
	private void generateHeader (HttpServletResponse response, Request baseRequest)
			                     throws IOException {
		
		response.setContentType("text/html;charset=utf8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<html>");
		
		
	}
	
	private void generateEndPage (HttpServletResponse response, Request baseRequest)
			                     throws IOException {
		
		response.getWriter().println("</body");
		response.getWriter().println("</html>");
	}
	
	public void generateResponsePage (UserSession UserSes, HttpServletResponse response, Request baseRequest,
									  String Login)
			                          throws IOException {
		
		generateHeader (response, baseRequest);
		
		if (UserSes.userId == null) {
			
			response.getWriter().println (
					"<script> bodyOnload = setInterval(function(){reload();}, 1000); function reload(){document.forms['Autorform'].submit(); }</script> " 
					);
			response.getWriter().println("<body>");
			response.getWriter().println("<h1> Wait for authorization  </h1>");
			response.getWriter().println("<form id  = 'Autorform' method = 'POST'>");
			response.getWriter().println("<input hidden type = 'text' name = 'login' size = '20' value = " + Login + " >");
			response.getWriter().println("<input type = 'hidden' name = 'id' value = "
							             + UserSes.sessionId.getLong().toString() + " >");
			response.getWriter().println("</input>");
			response.getWriter().println("</form>");
		}
			
		else if (UserSes.userId.getLong() != -1) {
			
			response.getWriter().println("<body>");
			response.getWriter().println("<h1>Authorization is successful </h1>");
			response.getWriter().println("<h1> Hello, " + UserSes.userName + "! </h1>");
			
			response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
			response.getWriter().println("<input type = 'hidden' name = 'StGame' value = '1' >");
			response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
					 					 + UserSes.sessionId.getLong().toString() + " >");
			response.getWriter().println("<button type = 'submit'> <h3> Начать игру </h3> </button>");
			response.getWriter().println("</form>");
		}

		else {
			response.getWriter().println("<html>");
			response.getWriter().println("<body>");
			response.getWriter().println("<h1>Authorization failed </h1>");
			response.getWriter().println("<h1>User is not found </h1>");
		}
		generateEndPage (response, baseRequest);
	}
	
	public void generateResponseRegistrPage (UserSession UserSes, HttpServletResponse response, 
											 Request baseRequest, String login, String stringId)
            						  throws IOException {

		generateHeader (response, baseRequest);
		response.getWriter().println("<body>");
		if (UserSes.userId == null) { 
			response.getWriter().println("<h1> Wait for registration  </h1>");
			response.getWriter().println (
					"<script> bodyOnload = setInterval(function(){reload();}, 1000); function reload(){document.forms['Regform'].submit(); }</script> " 
					);
			response.getWriter().println("<form id  = 'Regform' method = 'POST'>");
			response.getWriter().println("<input hidden type = 'text' name = 'Reglogin' size = '20' value = " + login + " >");
			response.getWriter().println("<input type = 'hidden' name = 'id' value = "
							             + stringId + " >");
			
			response.getWriter().println("</input>");
			response.getWriter().println("</form>");
		}

		else if (UserSes.userId.getLong() != -1) {

			response.getWriter().println("<h1>Registration is successful </h1>");
			response.getWriter().println("<h1> Hello, " + UserSes.userName + "! </h1>");
			
			response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
			response.getWriter().println("<input type = 'hidden' name = 'StGame' value = '1' >");
			response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
		             					 + UserSes.sessionId.getLong().toString() + " >");
			response.getWriter().println("<button type = 'submit'> <h3> Начать игру </h3> </button>");
			response.getWriter().println("</form>");
			
			//response.getWriter().println("<h1> UserID - " + UserSes.userId.getLong()	+ "</h1>");
			//response.getWriter().println("<h1> SessionID - " + UserSes.sessionId.getLong() + "</h1>");
		}

		else {
			response.getWriter().println("<h1>Registration failed </h1>");
			response.getWriter().println("<h1>User already exists </h1>");
		}
		generateEndPage (response, baseRequest);
	}
	
	public void generateStartPage (UserSession UserSes, HttpServletResponse response,
										  Request baseRequest) throws IOException {
		
		generateHeader (response, baseRequest);
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'> Вход в игру </h1> ");
		response.getWriter().println("<form id  = 'Autorform' method = 'POST'>");
		response.getWriter().println("<b> Login </b><br>");
		response.getWriter().println("<input required type = 'text' name = 'login' size = '20' >");
		response.getWriter().println("<input type = 'hidden' name = 'id' value = "
						             + UserSes.sessionId.getLong().toString() + " >");
		//response.getWriter().println("<input type = 'hidden' name = 'SessId' value = '-1' >");
		response.getWriter().println("<button type='submit'> <h3> Войти </h3> </button>");
		response.getWriter().println("</input>");
		response.getWriter().println("</form>");
		
		response.getWriter().println("<form id  = 'Regform' method = 'POST'>");
		response.getWriter().println("<input type = 'hidden' name = 'Reg' value = '1' >");
		response.getWriter().println("<input type = 'hidden' name = 'id' value = "            					  + UserSes.sessionId.getLong().toString() + " >");
		response.getWriter().println("<button> <h3> Зарегистрироваться </h3> </button>");
		response.getWriter().println("</form>");
				
		generateEndPage (response, baseRequest);
	}
	
	public void generateRegistrationPage (String StringId, HttpServletResponse response,
			  Request baseRequest) throws IOException {

		generateHeader (response, baseRequest);
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'> Регистрация </h1> ");
		
		response.getWriter().println("<form id  = 'Regform' method = 'POST'>");
		response.getWriter().println("<b> Ваш логин </b><br>");
		response.getWriter().println("<input required type = 'text' name = 'Reglogin' size = '20' >");
		response.getWriter().println("<input type = 'hidden' name = 'id' value = "
						             + StringId + " >");
		response.getWriter().println("<button type='submit'> <h3> Зарегистрироваться </h3> </button>");
		response.getWriter().println("</input>");
		response.getWriter().println("</form>");
		generateEndPage (response, baseRequest);
	}
	
	public void generateStartGamePage (UserSession UserSes, HttpServletResponse response,
			    Request baseRequest) throws IOException {

		generateHeader (response, baseRequest);
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'> Игра началась! </h1> ");
		
		response.getWriter().println("<h3 align = 'right'> You:" + UserSes.userName + "</h3> ");
		response.getWriter().println("<h3 align = 'right'> Enemy:" + UserSes.enemyName + "</h3> ");
		
		response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
		response.getWriter().println("<input type = 'hidden' name = 'PlayGame' value = '1' >");
		response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
	             					 + UserSes.sessionId.getLong().toString() + " >");
		response.getWriter().println("<button type = 'submit'> <h3> Жми меня </h3> </button>");
		response.getWriter().println("</form>");
		
		generateEndPage (response, baseRequest);
	}
	
	public void generateWaitPage (UserSession UserSes, HttpServletResponse response,
			  Request baseRequest) throws IOException {

		generateHeader (response, baseRequest);
		response.getWriter().println (
				"<script> bodyOnload = setInterval(function(){reload();}, 1000); function reload(){ document.forms['StartGame'].submit(); }</script> " 
				);
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'> Подождите второго игрока </h1> ");
		
		response.getWriter().println("<h3 align = 'right'> " + UserSes.userName + "</h3> ");
		response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
		response.getWriter().println("<input type = 'hidden' name = 'StGame' value = '1' >");
		response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
	             					 + UserSes.sessionId.getLong().toString() + " >");
		response.getWriter().println("</form>");
		
		generateEndPage (response, baseRequest);
	}
	
	public void generateGamePage (UserSession UserSes, HttpServletResponse response,
			  Request baseRequest) throws IOException {
		
		if (UserSes.timeToFinish > 0) generateStartGamePage (UserSes, response, baseRequest);
		else if (UserSes.clickByUser > UserSes.clickedByEnemy) generateWinPage (UserSes, response, baseRequest);
		else if (UserSes.clickByUser < UserSes.clickedByEnemy) generateLostPage (UserSes, response, baseRequest);
		else if (UserSes.clickByUser == UserSes.clickedByEnemy) generateDrawPage (UserSes, response, baseRequest);
	}
	
	public void generateWinPage (UserSession UserSes, HttpServletResponse response,
			  Request baseRequest) throws IOException {
		
		generateHeader (response, baseRequest);
		
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'>" + UserSes.userName + " Вы выиграли </h1> ");
		response.getWriter().println("<h2> Вы успели нажать " + UserSes.clickByUser + " раз </h1> ");
		response.getWriter().println("<h2> Ваш противник " + UserSes.enemyName + 
									 " успел нажать " + UserSes.clickedByEnemy + " раз </h1> ");
		response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
		response.getWriter().println("<input type = 'hidden' name = 'StGame' value = '1' >");
		response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
	             					 + UserSes.sessionId.getLong().toString() + " >");
		response.getWriter().println("<button type = 'submit'> <h3> Играть ещё </h3> </button>");
		response.getWriter().println("</form>");
		
		UserSes.game_state = false;
		generateEndPage (response, baseRequest);
	}
	
	public void generateLostPage (UserSession UserSes, HttpServletResponse response,
			  Request baseRequest) throws IOException {
		
		generateHeader (response, baseRequest);
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'> Вы проиграли </h1> ");
		response.getWriter().println("<h2> Вы успели нажать " + UserSes.clickByUser + " раз </h1> ");
		response.getWriter().println("<h2> Ваш противник " + UserSes.enemyName + 
									 " успел нажать " + UserSes.clickedByEnemy + " раз </h1> ");
		
		response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
		response.getWriter().println("<input type = 'hidden' name = 'StGame' value = '1' >");
		response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
	             					 + UserSes.sessionId.getLong().toString() + " >");
		response.getWriter().println("<button type = 'submit'> <h3> Играть ещё </h3> </button>");
		response.getWriter().println("</form>");
		UserSes.game_state = false;
		
		generateEndPage (response, baseRequest);
	}
	
	public void generateDrawPage (UserSession UserSes, HttpServletResponse response,
			  Request baseRequest) throws IOException {
		
		generateHeader (response, baseRequest);
		response.getWriter().println("<body>");
		response.getWriter().println("<h1 align = 'center'> Ничья </h1> ");
		response.getWriter().println("<h2> Вы успели нажать " + UserSes.clickByUser + " раз </h1> ");
		response.getWriter().println("<h2> Ваш противник " + UserSes.enemyName + 
									 " успел нажать " + UserSes.clickedByEnemy + " раз </h1> ");
		response.getWriter().println("<form id  = 'StartGame' method = 'POST'>");
		response.getWriter().println("<input type = 'hidden' name = 'StGame' value = '1' >");
		response.getWriter().println("<input type = 'hidden' name = 'Id' value = "
	             					 + UserSes.sessionId.getLong().toString() + " >");
		response.getWriter().println("<button type = 'submit'> <h3> Играть ещё </h3> </button>");
		response.getWriter().println("</form>");
		UserSes.game_state = false;
		
		generateEndPage (response, baseRequest);
	}
}

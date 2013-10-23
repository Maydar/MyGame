package tests.FrontendTests;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ru.mail.projects.frontend.impl.PageGeneratorImpl;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PageGeneratorImplTest {
    Request mockBaseRequest;
    HttpServletRequest mockHttpRequestWasAuth;
    HttpServletResponse mockHttpServletResponse;
    PageGeneratorImpl pageGenerator;

    @BeforeTest
    public void setUp() throws Exception {

        mockBaseRequest = mock(Request.class);
        mockHttpRequestWasAuth = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(System.out));

        pageGenerator = new PageGeneratorImpl();
    }

    @Test(timeOut = 1000)
    public void testGenerateResponsePage() throws Exception {

        LongId<SessionId> sessionId1 = new LongId<SessionId>(10);
        UserSession mockUsSess = mock(UserSession.class);
        mockUsSess.sessionId = sessionId1;

        String Login = new String("Roma");
        LongId<UserId> userId_test = new LongId<UserId>(-1);
        LongId<UserId> userIdNotNull = new LongId<UserId>(5);


        pageGenerator.generateResponsePage(mockUsSess, mockHttpServletResponse, mockBaseRequest, Login);
        mockUsSess.userId = userId_test;

        pageGenerator.generateResponsePage(mockUsSess, mockHttpServletResponse, mockBaseRequest, Login);
        mockUsSess.userId = userIdNotNull;
        pageGenerator.generateResponsePage(mockUsSess, mockHttpServletResponse, mockBaseRequest, Login);

    }

    @Test(timeOut = 1000)
    public void testGenerateResponseRegistrPage() throws Exception {

        LongId<SessionId> sessionId1 = new LongId<SessionId>(10);
        UserSession mockUsSess = mock(UserSession.class);
        mockUsSess.userId = null;
        mockUsSess.sessionId = sessionId1;

        String Login = new String("Roma");
        LongId<UserId> userId_test = new LongId<UserId>(-1);
        LongId<UserId> userIdNotNull = new LongId<UserId>(5);
        mockUsSess.userName = new String("roma");


        pageGenerator.generateResponseRegistrPage(mockUsSess, mockHttpServletResponse, mockBaseRequest, Login, "1");
        mockUsSess.userId = userId_test;

        pageGenerator.generateResponseRegistrPage(mockUsSess, mockHttpServletResponse, mockBaseRequest, Login, "1");
        mockUsSess.userId = userIdNotNull;
        pageGenerator.generateResponseRegistrPage(mockUsSess, mockHttpServletResponse, mockBaseRequest, Login, "1");

    }

    @Test(timeOut = 1000)
    public void testGenerateGamePage() throws Exception {

        LongId<SessionId> sessionIdTest = new LongId<SessionId>(10);
        LongId<UserId> userIdNotNull = new LongId<UserId>(5);

        UserSession mockUsSess = mock(UserSession.class);
        mockUsSess.sessionId = sessionIdTest;
        mockUsSess.timeToFinish = 5;
        mockUsSess.userId = userIdNotNull;

        int clickUser = 8;

        try {
            pageGenerator.generateGamePage(null, mockHttpServletResponse, mockBaseRequest);

            pageGenerator.generateGamePage(mockUsSess, mockHttpServletResponse, mockBaseRequest);

            mockUsSess.clickByUser = clickUser;
            mockUsSess.clickedByEnemy = clickUser - 1;
            mockUsSess.timeToFinish = 0;
            pageGenerator.generateGamePage(mockUsSess, mockHttpServletResponse, mockBaseRequest);

            mockUsSess.clickedByEnemy = clickUser + 1;
            pageGenerator.generateGamePage(mockUsSess, mockHttpServletResponse, mockBaseRequest);

            mockUsSess.clickedByEnemy = clickUser;
            pageGenerator.generateGamePage(mockUsSess, mockHttpServletResponse, mockBaseRequest);
        }
        catch (Exception e) {

        }



    }
}

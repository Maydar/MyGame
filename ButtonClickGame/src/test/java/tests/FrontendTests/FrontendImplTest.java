package tests.FrontendTests;


import org.eclipse.jetty.server.Request;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.AddressService;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.frontend.impl.PageGeneratorImpl;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FrontendImplTest {
    private Context mockContext;
    private MessageSystem mockMsgSystem;
    private ResourceSystem mockRsSystem;
    private PageGeneratorImpl mockPageGen;
    private AddressService mockAddressService;
    private UserSession mockUserSessionFirst;
    private UserSession mockUserSessionSecond;
    private Request mockRequest;
    private HttpServletRequest mockHttpServletRequestPlayGame;
    private HttpServletRequest mockHttpServletRequestPlayGame2;
    private HttpServletRequest getMockHttpServletRequestStGame;
    private HttpServletResponse mockHttpServletResponse;
    private UserSessResource mockUserSessResource;
    private HttpServletRequest mockHttpRequestWasAuth;
    private HttpServletRequest mockHttpRequestNotAuth;
    private HttpServletRequest mockHttpRequestRegLogin;
    private HttpServletRequest mockHttpRequestLogin;
    private HttpServletRequest mockHttpRequestRegistry;



    @BeforeClass
    public void setUp() throws Exception {
        mockContext = mock(Context.class);
        mockMsgSystem = mock(MessageSystem.class);
        mockRsSystem = mock(ResourceSystem.class);
        mockPageGen = mock(PageGeneratorImpl.class);
        mockAddressService = mock(AddressService.class);
        mockUserSessionFirst = mock(UserSession.class);
        mockUserSessionSecond = mock(UserSession.class);
        mockUserSessResource = mock(UserSessResource.class);

        when(mockContext.get(ResourceSystem.class)).thenReturn(mockRsSystem);
        when(mockContext.get(MessageSystem.class)).thenReturn(mockMsgSystem);
        when(mockContext.get(AddressService.class)).thenReturn(mockAddressService);
        when(mockMsgSystem.getAddressService()).thenReturn(mockAddressService);
        when(mockUserSessResource.getPlayTime()).thenReturn(1000);
        when(mockRsSystem.getResource("./resources/userSess.xml")).thenReturn(mockUserSessResource);
    }

    @Test
    public void testGetAddress() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        FrontendImpl frontendSecond = new FrontendImpl(mockContext);
        Assert.assertEquals(frontendFirst.getAddress().hashCode(), frontendSecond.getAddress().hashCode());
    }

    @Test
    public void testGetName() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        FrontendImpl frontendSecond = new FrontendImpl(mockContext);
        Assert.assertNotNull(frontendFirst.getName());
        Assert.assertNotNull(frontendSecond.getName());
    }

    @Test
    public void testUpdateUserId() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);

        LongId<SessionId> sessionId = new LongId<SessionId>(1);
        frontendFirst.sessions.put(sessionId.getLong(), mockUserSessionFirst);
        LongId<UserId> userId = new LongId<UserId>(1);

        frontendFirst.updateUserId(sessionId, userId);
        Assert.assertEquals(frontendFirst.sessions.get(sessionId.getLong()).userId.getLong(), userId.getLong());

        Integer expected = new Integer(-1);
        frontendFirst.updateUserId(sessionId, null);
        Assert.assertEquals(frontendFirst.sessions.get(sessionId.getLong()).userId.getLong(), expected);

    }

    @Test
    public void testShowStartedGame() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        FrontendImpl.waitUsersNumber = new AtomicInteger(2);
        int idGameSession = 1;
        AtomicInteger waitUsersNumber = new AtomicInteger(0);

        frontendFirst.showStartedGame(mockUserSessionFirst, mockUserSessionSecond, idGameSession);

        Assert.assertEquals(mockUserSessionFirst.game_state, true);
        Assert.assertEquals(mockUserSessionSecond.game_state, true);
        Assert.assertEquals(mockUserSessionSecond.is_wait, false);
        Assert.assertEquals(mockUserSessionFirst.is_wait, false);
        Assert.assertEquals(mockUserSessionFirst.enemyName, mockUserSessionSecond.userName);
        Assert.assertEquals(mockUserSessionSecond.enemyName, mockUserSessionFirst.userName);
        Assert.assertEquals(mockUserSessionFirst.IdGameSession, idGameSession);
        Assert.assertEquals(mockUserSessionSecond.IdGameSession, idGameSession);
        Assert.assertEquals(FrontendImpl.waitUsersNumber.longValue(), waitUsersNumber.longValue());


    }

    @Test(dependsOnMethods = {"testShowStartedGame"})
    public void testReplicateFromGM() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        int idGameSession = 1;
        LongId<SessionId> sessionId1 = new LongId<SessionId>(1);
        LongId<SessionId> sessionId2 = new LongId<SessionId>(2);
        mockUserSessionFirst.timeToFinish = 100;
        mockUserSessionSecond.timeToFinish = 100;
        frontendFirst.showStartedGame(mockUserSessionFirst, mockUserSessionSecond, idGameSession);
        frontendFirst.sessions.put(sessionId1.getLong(), mockUserSessionFirst);
        frontendFirst.sessions.put(sessionId2.getLong(), mockUserSessionSecond);
        frontendFirst.replicateFromGM();
        Assert.assertEquals(mockUserSessionFirst.timeToFinish, 0);
        Assert.assertEquals(mockUserSessionSecond.timeToFinish, 0);
    }

    @Test
    public void testStartGame() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        int sessionNumber = 12;
        LongId<SessionId> sessionId1 = new LongId<SessionId>(sessionNumber);
        mockUserSessionFirst.userId = null;
        mockUserSessionFirst.sessionId = sessionId1;
        mockUserSessionFirst.is_wait = false;
        mockRequest = mock(Request.class);
        UserSession.usr = mockUserSessResource;
        frontendFirst.sessions.put(sessionNumber, mockUserSessionFirst);

        mockHttpServletRequestPlayGame = mock(HttpServletRequest.class);
        when(mockHttpServletRequestPlayGame.getParameter("Id")).thenReturn(Integer.toString(sessionNumber));
        when(mockHttpServletRequestPlayGame.getParameter("PlayGame")).thenReturn("1");


        mockHttpServletRequestPlayGame2 = mock(HttpServletRequest.class);
        when(mockHttpServletRequestPlayGame2.getParameter("Id")).thenReturn(Integer.toString(sessionNumber));
        when(mockHttpServletRequestPlayGame2.getParameter("PlayGame")).thenReturn("2");

        getMockHttpServletRequestStGame = mock(HttpServletRequest.class);
        when(getMockHttpServletRequestStGame.getParameter("Id")).thenReturn(Integer.toString(sessionNumber));
        when(getMockHttpServletRequestStGame.getParameter("StGame")).thenReturn("1");

        mockHttpServletResponse = mock(HttpServletResponse.class);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(System.out));

        frontendFirst.startGame(mockRequest, mockHttpServletRequestPlayGame, mockHttpServletResponse);
        int tmpWaitUsersNmb = FrontendImpl.waitUsersNumber.get();
        frontendFirst.startGame(mockRequest, getMockHttpServletRequestStGame, mockHttpServletResponse);

        Assert.assertEquals(tmpWaitUsersNmb + 1, FrontendImpl.waitUsersNumber.get());
        Assert.assertEquals(true, mockUserSessionFirst.is_wait);
        Assert.assertEquals(0, mockUserSessionFirst.clickByUser);
        Assert.assertEquals(0, mockUserSessionFirst.clickedByEnemy);


        mockUserSessionFirst.is_wait = false;
        frontendFirst.startGame(mockRequest, getMockHttpServletRequestStGame, mockHttpServletResponse);
        mockUserSessionFirst.is_wait = false;
        frontendFirst.startGame(mockRequest, getMockHttpServletRequestStGame, mockHttpServletResponse);
        Assert.assertEquals(new Integer(sessionNumber), frontendFirst.getWaitSess().getLong());
        mockUserSessionFirst.is_wait = false;
        frontendFirst.startGame(mockRequest, getMockHttpServletRequestStGame, mockHttpServletResponse);
        frontendFirst.startGame(mockRequest, getMockHttpServletRequestStGame, mockHttpServletResponse);

        frontendFirst.startGame(mockRequest, mockHttpServletRequestPlayGame2, mockHttpServletResponse);
        mockUserSessionFirst.game_state = true;
        frontendFirst.startGame(mockRequest, mockHttpServletRequestPlayGame2, mockHttpServletResponse);
    }

    @Test
    public void testDoAuthentification() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        int sessionNumber = 12;
        LongId<SessionId> sessionId1 = new LongId<SessionId>(sessionNumber);
        mockUserSessionFirst.userId = null;
        mockUserSessionFirst.sessionId = sessionId1;
        frontendFirst.sessions.put(sessionNumber, mockUserSessionFirst);
        mockRequest = mock(Request.class);

        mockHttpRequestWasAuth = mock(HttpServletRequest.class);
        when(mockHttpRequestWasAuth.getParameter("StGame")).thenReturn("1");

        mockHttpRequestRegistry = mock(HttpServletRequest.class);
        when(mockHttpRequestRegistry.getParameter("Reg")).thenReturn("1");

        mockHttpRequestLogin = mock(HttpServletRequest.class);
        when(mockHttpRequestLogin.getParameter("login")).thenReturn("Maydar");
        when(mockHttpRequestLogin.getParameter("id")).thenReturn(Integer.toString(sessionNumber));

        mockHttpRequestRegLogin = mock(HttpServletRequest.class);
        when(mockHttpRequestRegLogin.getParameter("Reglogin")).thenReturn("0");
        when(mockHttpRequestRegLogin.getParameter("id")).thenReturn(Integer.toString(sessionNumber));

        mockHttpRequestNotAuth = mock(HttpServletRequest.class);

        mockHttpServletResponse = mock(HttpServletResponse.class);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(System.out));

        FrontendImpl.AuthorState authorStateEnd = frontendFirst.doAuthentification(mockRequest, mockHttpRequestWasAuth, mockHttpServletResponse);
        Assert.assertEquals(FrontendImpl.AuthorState.End, authorStateEnd);

        FrontendImpl.AuthorState authorStateNotEnd = frontendFirst.doAuthentification(mockRequest, mockHttpRequestNotAuth, mockHttpServletResponse);
        Assert.assertEquals(FrontendImpl.AuthorState.NotEnd, authorStateNotEnd);

        authorStateNotEnd = frontendFirst.doAuthentification(mockRequest, mockHttpRequestRegistry, mockHttpServletResponse);
        Assert.assertEquals(FrontendImpl.AuthorState.NotEnd, authorStateNotEnd);

        authorStateNotEnd = frontendFirst.doAuthentification(mockRequest, mockHttpRequestLogin, mockHttpServletResponse);
        Assert.assertEquals(FrontendImpl.AuthorState.NotEnd, authorStateNotEnd);

        authorStateNotEnd = frontendFirst.doAuthentification(mockRequest, mockHttpRequestRegLogin, mockHttpServletResponse);
        Assert.assertEquals(FrontendImpl.AuthorState.NotEnd, authorStateNotEnd);
    }
    @Test
    public void testHandle() throws Exception {
        int sessionNumber = 12;
        FrontendImpl frontend = new FrontendImpl(mockContext);
        LongId<SessionId> sessionId1 = new LongId<SessionId>(sessionNumber);

        UserSession mockedUsSess = mock(UserSession.class);
        mockedUsSess.userId = null;
        mockedUsSess.sessionId = sessionId1;

        frontend.sessions.put(new Integer(sessionNumber), mockedUsSess);

        mockRequest = mock(Request.class);
        mockHttpRequestNotAuth = mock(HttpServletRequest.class);

        getMockHttpServletRequestStGame = mock(HttpServletRequest.class);
        when(getMockHttpServletRequestStGame.getParameter("Id")).thenReturn(Integer.toString(sessionNumber));
        when(getMockHttpServletRequestStGame.getParameter("StGame")).thenReturn("1");

        mockHttpServletResponse = mock(HttpServletResponse.class);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(System.out));



        frontend.handle("", mockRequest, mockHttpRequestNotAuth, mockHttpServletResponse);

        int waitUsNumber = FrontendImpl.waitUsersNumber.get();
        frontend.handle("", mockRequest, getMockHttpServletRequestStGame, mockHttpServletResponse);
        Assert.assertEquals(waitUsNumber + 1, FrontendImpl.waitUsersNumber.get());

    }




    @Test
    public void testSetName() throws Exception {
        FrontendImpl frontendFirst = new FrontendImpl(mockContext);
        frontendFirst.setName("сеттим имя");
        Assert.assertEquals(frontendFirst.getName(), "сеттим имя");
    }
}

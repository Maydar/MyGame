package tests.GameMechanicTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.DatabaseServiceMock;
import ru.mail.projects.base.*;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;
import ru.mail.projects.game.mechanics.impl.GameSession;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.messages.MsgGameStarted;
import ru.mail.projects.messages.MsgReplicate;
import ru.mail.projects.messages.MsgUpdateUserId;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.vfs.impl.VFSImpl;

import java.util.Queue;


public class GameMechanicsImplTest {
    private Context context;
    private VFS vfs;
    private MessageSystem msgSystem;
    private ResourceSystem rsSystem;

    @BeforeClass
    public void setUp() throws Exception {
        context = new Context();
        vfs = new VFSImpl("./resources");
        msgSystem = new MessageSystemImpl();
        rsSystem = new ResourceSystemImpl(vfs);
        rsSystem.loadResources();
        context.add(VFS.class, vfs);
        context.add(MessageSystem.class, msgSystem);
        context.add(ResourceSystem.class, rsSystem);
    }

    @Test
    public void testGetAddress() throws Exception {
        GameMechanicsImpl gameMechanicsFirst = new GameMechanicsImpl(context);
        GameMechanicsImpl gameMechanicsSecond = new GameMechanicsImpl(context);
        Assert.assertEquals(gameMechanicsFirst.getAddress().hashCode(), gameMechanicsSecond.getAddress().hashCode());
    }

    @Test
    public void testStartGame() throws Exception {

        GameMechanicsImpl gameMechanicsFirst = new GameMechanicsImpl(context);
        msgSystem.addService(gameMechanicsFirst);

        UserSession userSession1 = new UserSession((UserSessResource)rsSystem.getResource("./resources/userSess.xml"));
        UserSession userSession2 = new UserSession((UserSessResource)rsSystem.getResource("./resources/userSess.xml"));

        Address to = gameMechanicsFirst.getAddress();
        gameMechanicsFirst.startGame(userSession1, userSession2, to);
        Queue<Msg> queueOfMessages =  msgSystem.getQueue(to);
        Msg lastMessage = queueOfMessages.poll();

        Assert.assertTrue(lastMessage instanceof MsgGameStarted);
        MsgGameStarted msgGameStarted = (MsgGameStarted)lastMessage;
        Assert.assertEquals(msgGameStarted.getTo(), to);
        Assert.assertEquals(msgGameStarted.getGameSessionId(), new Integer(1));
    }

    @Test(dependsOnMethods = {"testStartGame"})
    public void testDoProgress() throws Exception {
        GameMechanicsImpl gameMechanicsFirst = new GameMechanicsImpl(context);
        FrontendImpl frontend = new FrontendImpl(context);
        msgSystem.addService(gameMechanicsFirst);
        msgSystem.addService(frontend);

        UserSession userSession1 = new UserSession();
        UserSession userSession2 = new UserSession();
        userSession1.IdGameSession = 2;

        gameMechanicsFirst.startGame(userSession1, userSession2, gameMechanicsFirst.getAddress());
        Assert.assertNotNull(gameMechanicsFirst.gameSessions.get(GameMechanicsImpl.gameSessId.get()));

        GameSession gameSession = gameMechanicsFirst.gameSessions.get(userSession1.IdGameSession);
        Assert.assertNotNull(gameSession);
        int clicked_enemy = gameSession.getSecondUserId().clickedByEnemy;
        int user_click = userSession1.clickByUser;
        gameMechanicsFirst.DoProgress(userSession1);
        Assert.assertEquals(userSession1.clickByUser, user_click + 1);
        Assert.assertEquals(gameSession.getSecondUserId().clickedByEnemy, clicked_enemy + 1);


    }


    @Test
    public void testGetSetName() throws Exception {
        GameMechanicsImpl gameMechanics = new GameMechanicsImpl(context);
        GameMechanicsImpl gameMechanics1 = new GameMechanicsImpl(context);


        String newName = new String("testName");
        gameMechanics.setName(newName);
        Assert.assertEquals(newName, gameMechanics.getName());

        Assert.assertEquals(gameMechanics.getAddress(), gameMechanics1.getAddress());
        gameMechanics.endGame();
    }

}

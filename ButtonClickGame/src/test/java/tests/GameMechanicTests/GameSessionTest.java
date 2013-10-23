package tests.GameMechanicTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.game.mechanics.impl.GameSession;
import ru.mail.projects.game.mechanics.impl.GameState;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.UserId;
import ru.mail.projects.utils.Context;
import ru.mail.projects.vfs.impl.VFSImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameSessionTest {

    private UserSession userSession;
    private UserSession userSession1;
    private Context context;
    private ResourceSystem RsSystem;
    private VFS vfs;

    @BeforeClass
    public void setUp() throws Exception {
        context = new Context();
        vfs = new VFSImpl("./resources");
        RsSystem = new ResourceSystemImpl(vfs);

        context.add(ResourceSystem.class, RsSystem);
        RsSystem.loadResources();
        userSession = new UserSession((UserSessResource)RsSystem.getResource("./resources/userSess.xml"));
        userSession.userId = new LongId<UserId>(1);
        userSession1 = new UserSession(((UserSessResource)RsSystem.getResource("./resources/userSess.xml")));
        userSession1.userId = new LongId<UserId>(2);
    }

    @Test
    public void testGameSession() throws Exception {
        GameSession gameSession = new GameSession(userSession1, userSession, (UserSessResource)RsSystem.getResource("./resources/userSess.xml"));
        gameSession.setGameState(GameState.started);
        Assert.assertEquals(gameSession.getGameState(), GameState.started);

        gameSession.setStartTime(50);
        Assert.assertEquals(gameSession.getStartTime(), 50);

        Assert.assertEquals(gameSession.getFirstUserId().userId, userSession1.userId);
        Assert.assertEquals(gameSession.getSecondUserId().userId, userSession.userId);

        UserSession firstUserId = gameSession.getFirstUserId();
        gameSession.SetClickedByEnemyNumFirst();
        Assert.assertEquals(gameSession.getFirstUserId().clickedByEnemy, 1);

        UserSession secondUserId = gameSession.getSecondUserId();
        gameSession.SetClickedByEnemyNumSecond();
        Assert.assertEquals(gameSession.getSecondUserId().clickedByEnemy, 1);

    }

}

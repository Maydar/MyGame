package tests.FrontendTests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.utils.Context;
import ru.mail.projects.vfs.impl.VFSImpl;

public class UserSessionTest {
    private Context context;
    private VFS vfs;
    private MessageSystem msgSystem;
    private ResourceSystem rsSystem;

    @Test
    public void createUserSessionTest() throws  Exception {
        context = new Context();
        vfs = new VFSImpl("./resources");
        msgSystem = new MessageSystemImpl();
        rsSystem = new ResourceSystemImpl(vfs);
        rsSystem.loadResources();
        context.add(VFS.class, vfs);
        context.add(MessageSystem.class, msgSystem);
        context.add(ResourceSystem.class, rsSystem);

        FrontendImpl frontend = new FrontendImpl(context);

        UserSession userSession = new UserSession();
        UserSession userSession1 = new UserSession();

        Assert.assertNotSame(userSession.sessionId.hashCode(), null);
        Assert.assertNotSame(userSession1.sessionId.hashCode(), null);
        Assert.assertEquals(userSession.timeToFinish, 1000);
        Assert.assertEquals(userSession1.timeToFinish, 1000);
        Assert.assertEquals(userSession.clickByUser, 0);
        Assert.assertEquals(userSession.clickedByEnemy, 0);
        Assert.assertEquals(userSession1.clickByUser, 0);
        Assert.assertEquals(userSession1.clickedByEnemy, 0);

        Assert.assertEquals(userSession.timeToFinish, UserSession.getPlayTime());
        Assert.assertEquals(userSession1.timeToFinish, UserSession.getPlayTime());



    }
}

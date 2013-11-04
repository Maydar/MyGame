package tests.DatabaseTests;

import com.mysql.jdbc.Driver;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.*;
import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.account.database.impl.DatabaseServiceMock;
import ru.mail.projects.account.database.impl.UsersDAO;
import ru.mail.projects.account.database.impl.UsersDataSet;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.vfs.impl.VFSImpl;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
//import static org.mockito.asm.util.CheckClassAdapter.verify;

public class DatabaseServiceImplTest {

    private Context context;

    private UsersDataSet uDataSet;
    private UsersDAO uDAO;
    private String uName;
    private Address to;
    private LongId<SessionId> sessionId;
    private DatabaseServiceImpl dbService;

    @BeforeClass
    public void setUp() throws Exception {
        context = new Context();
        VFS vfs = new VFSImpl("./resources");
        MessageSystem MsgSys = new MessageSystemImpl();

        context.add(VFS.class, vfs);
        context.add(MessageSystem.class, MsgSys);
        ResourceSystem rsSystem = new ResourceSystemImpl(vfs);
        rsSystem.loadResources();
        context.add(ResourceSystem.class, rsSystem);
        DatabaseServiceMock dbsm = new DatabaseServiceMock(context);

    }

    @Test
    public void getCountTest(){
        DatabaseServiceImpl dbService = new DatabaseServiceImpl(context);
        int x = dbService.count;
        DatabaseServiceImpl dbService2 = new DatabaseServiceImpl(context);
        Assert.assertEquals(x+1, dbService.count);

    }

    @Test
    public void action() {
        DatabaseServiceImpl dbService = new DatabaseServiceImpl(context);
        Assert.assertFalse(dbService.act());
    }

}

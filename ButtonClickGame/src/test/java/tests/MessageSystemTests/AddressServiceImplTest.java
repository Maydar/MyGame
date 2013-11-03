package tests.MessageSystemTests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.DatabaseServiceMock;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;
import ru.mail.projects.message.system.impl.AddressServiceImpl;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.vfs.impl.VFSImpl;
import org.testng.Assert;


public class AddressServiceImplTest {


    @BeforeClass
    public void setUp() throws Exception {
        Context context = new Context();
        VFS vfs = new VFSImpl("./resources");
        MessageSystem MsgSys = new MessageSystemImpl();

        context.add(VFS.class, vfs);
        context.add(MessageSystem.class, MsgSys);
        ResourceSystem rsSystem = new ResourceSystemImpl(vfs);
        rsSystem.loadResources();
        context.add(ResourceSystem.class, rsSystem);

        FrontendImpl frontend = new FrontendImpl (context);
        DatabaseServiceMock AccSer  = new DatabaseServiceMock(context);
        DatabaseServiceMock AccSer2 = new DatabaseServiceMock(context);
        GameMechanicsImpl Gm = new GameMechanicsImpl (context);
    }

    @Test
    public void testGetSetAddress() throws Exception {
        AddressServiceImpl addressService = new AddressServiceImpl();
        LongId<SessionId> sessionId = new LongId<SessionId>(10);

        Address addr = addressService.getAddress("Frontend", sessionId);
        Assert.assertEquals("Frontend" + (sessionId.getLong() % FrontendImpl.count), addressService.getName());

        addr = addressService.getAddress("GameMechanics", sessionId);
        Assert.assertEquals("GameMechanics" + (sessionId.getLong() % GameMechanicsImpl.count), addressService.getName());

        addr = addressService.getAddress("DatabaseService", sessionId);
        Assert.assertEquals("DatabaseService" + (sessionId.getLong() % GameMechanicsImpl.count), addressService.getName());

    }


}

package tests.DatabaseTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.DatabaseServiceMock;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.Msg;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.messages.MsgUpdateUserId;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

import java.util.Queue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DatabaseServiceMockTest {
    private Context mockContext;
    private MessageSystem mockMsgSystem;
    private Context context;
    private MessageSystem msgSystem;
    private ResourceSystem mockRsSystem;
    private DbConnectionResource mockDbConnectionResource;
    @BeforeClass
    public void setUp() throws Exception {
        mockContext = mock(Context.class);
        mockMsgSystem = mock(MessageSystem.class);
        mockRsSystem = mock(ResourceSystem.class);
        mockDbConnectionResource = mock(DbConnectionResource.class);

        when(mockContext.get(ResourceSystem.class)).thenReturn(mockRsSystem);
        when(mockContext.get(MessageSystem.class)).thenReturn(mockMsgSystem);
        when(mockRsSystem.getResource("./resources/DbConnection.xml")).thenReturn(mockDbConnectionResource);
        context = new Context();
        msgSystem = new MessageSystemImpl();
        context.add(MessageSystem.class, msgSystem);

    }

    @Test
    public void testGetSetName() throws Exception {
        DatabaseServiceMock.count = 0;
        DatabaseServiceMock databaseServiceMock1 = new DatabaseServiceMock(context);
        DatabaseServiceMock databaseServiceMock2 = new DatabaseServiceMock(context);
        Assert.assertNotNull(databaseServiceMock1.getName());
        Assert.assertNotNull(databaseServiceMock2.getName());
        databaseServiceMock1.setName("AnotherService");
        Assert.assertEquals(databaseServiceMock1.getName(), "AnotherService");
    }
    @Test(dependsOnMethods = { "testGetSetName"})
    public void testFindUser() throws Exception {

        DatabaseServiceMock databaseServiceMock = new DatabaseServiceMock(context);
        msgSystem.addService(databaseServiceMock);

        LongId<SessionId> sessionId = new LongId<SessionId>(1);
        String username = "Valera";
        Address to = databaseServiceMock.getAddress();
        databaseServiceMock.FindUser(sessionId, username, to);
        Queue<Msg> queueOfMessages =  msgSystem.getQueue(to);
        Msg lastMessage = queueOfMessages.poll();

        Assert.assertTrue(lastMessage instanceof MsgUpdateUserId);
        MsgUpdateUserId msgUpdateUserId = (MsgUpdateUserId)lastMessage;
        Assert.assertEquals(msgUpdateUserId.getTo(), to);
        Assert.assertEquals(msgUpdateUserId.getSessionId(), sessionId);
        Assert.assertEquals(databaseServiceMock.getIdByName("Valera"), msgUpdateUserId.getUserId().getLong());

    }

    @Test
    public void actTest(){
        DatabaseServiceMock databaseServiceMock = new DatabaseServiceMock(context);
        msgSystem.addService(databaseServiceMock);
        Assert.assertFalse(databaseServiceMock.act());
    }

    @Test(dependsOnMethods = { "testGetSetName"})
    public void testAddUser() throws Exception {
        DatabaseServiceMock databaseServiceMock = new DatabaseServiceMock(context);
        msgSystem.addService(databaseServiceMock);

        LongId<SessionId> sessionId = new LongId<SessionId>(1);
        String username = "Maydar";
        Address to = databaseServiceMock.getAddress();
        databaseServiceMock.AddUser(sessionId, username, to);
        Queue<Msg> queueOfMessages =  msgSystem.getQueue(to);
        Msg lastMessage = queueOfMessages.poll();

        Assert.assertTrue(lastMessage instanceof MsgUpdateUserId);
        MsgUpdateUserId msgUpdateUserId = (MsgUpdateUserId)lastMessage;
        Assert.assertEquals(msgUpdateUserId.getTo(), to);
        Assert.assertEquals(msgUpdateUserId.getSessionId(), sessionId);
        Assert.assertEquals(databaseServiceMock.getIdByName("Maydar"), msgUpdateUserId.getUserId().getLong());
    }



}

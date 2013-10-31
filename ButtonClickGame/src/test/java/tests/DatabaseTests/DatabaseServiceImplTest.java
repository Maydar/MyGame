package tests.DatabaseTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.utils.Context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DatabaseServiceImplTest {
    private Context context;
    private ResourceSystem rs;
    private DbConnectionResource dbconn;
    private MessageSystem ms;

    @BeforeMethod
    public void setUp(){
        context = mock(Context.class);
        rs = mock(ResourceSystem.class);
        dbconn = mock(DbConnectionResource.class);
        ms = mock(MessageSystem.class);
        when((MessageSystem)context.get(MessageSystem.class)).thenReturn(ms);
        when(context.get(ResourceSystem.class)).thenReturn(rs);
        when((DbConnectionResource)rs.getResource("./resources/DbConnection.xml")).thenReturn(dbconn);
    }


    @Test
    public void getAddresTest(){
        DatabaseServiceImpl dbService = new DatabaseServiceImpl(context);
        Assert.assertNotNull(dbService.getAddress());
    }

    @Test
    public void getNameTest(){
        DatabaseServiceImpl dbService = new DatabaseServiceImpl(context);
        Assert.assertNotNull(dbService.getName());
    }
}

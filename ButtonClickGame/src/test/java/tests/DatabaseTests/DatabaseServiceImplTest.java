package tests.DatabaseTests;

import com.mysql.jdbc.Driver;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.*;
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
        when(dbconn.getDriver()).thenReturn("com.mysql.jdbc.Driver");
        when(dbconn.getUsername()).thenReturn("maidar1");
        when(dbconn.getPassword()).thenReturn("root");
        when(dbconn.getUrl()).thenReturn("jdbc:mysql://localhost:3306/UsersDb");
        when(context.get(ResourceSystem.class)).thenReturn(rs);
        when((DbConnectionResource) rs.getResource("./resources/DbConnection.xml")).thenReturn(dbconn);
    }

    @Test
    public void getCountTest(){
        DatabaseServiceImpl dbService = new DatabaseServiceImpl(context);
        int x = dbService.count;
        Assert.assertNotNull(dbService.getAddress());

    }
}

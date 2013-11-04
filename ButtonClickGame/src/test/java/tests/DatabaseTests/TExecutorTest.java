package tests.DatabaseTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.account.database.impl.TExecutor;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.utils.Context;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class TExecutorTest {

    private Connection conn;
    private PreparedStatement ps;

    @BeforeMethod
    public void setUp(){
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        try{
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        }
        catch (Exception e) {

        }
    }

    @Test
    public void insertTest(){
        TExecutor tExecutor = new TExecutor();
        Assert.assertTrue(tExecutor.insertUser(conn, "Timur"));
    }

    @Test
    public void updateStatisticsTest(){

    }
}

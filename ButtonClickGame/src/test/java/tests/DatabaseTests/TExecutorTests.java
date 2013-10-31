package tests.DatabaseTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.TExecutor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;


public class TExecutorTests {
    private Connection conn;

/*
    @BeforeMethod
    public void initiate(){
        conn = mock(Connection.class);
        when().thenReturn();

    }

    @Test
    public void updateTableTest(){
        String user1 = "Roma";
        String user2 = "Nadezda";
        TExecutor tExecutor = new TExecutor();
        tExecutor.updateStatistics(conn, user1, user2, true);
        Assert.assertTrue(true);
    }*/

}

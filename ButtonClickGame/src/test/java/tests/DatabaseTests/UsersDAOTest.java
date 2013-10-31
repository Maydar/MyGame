package tests.DatabaseTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.UsersDAO;
import ru.mail.projects.account.database.impl.UsersDataSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;


public class UsersDAOTest {
    private Connection conn;

    @BeforeMethod
    public void initiate(){
        conn = mock(Connection.class);
    }

    @Test
    public void getByNameTest() {
        UsersDAO usersDAO = new UsersDAO(conn);
        Assert.assertNull(usersDAO.getByName("Noname!"));
    }
}

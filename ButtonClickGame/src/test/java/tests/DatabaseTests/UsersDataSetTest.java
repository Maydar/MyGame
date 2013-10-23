package tests.DatabaseTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.UsersDataSet;


public class UsersDataSetTest {

    @Test
    public void testGetName() throws Exception {
        UsersDataSet usersDataSet = new UsersDataSet(5, "Pasha");
        UsersDataSet usersDataSet1 = new UsersDataSet("Roma");
        Assert.assertEquals("Pasha", usersDataSet.getName());
        Assert.assertEquals("Roma", usersDataSet1.getName());

    }

    @Test
    public void testGetId() throws Exception {
        UsersDataSet usersDataSet = new UsersDataSet(5, "Pasha");
        UsersDataSet usersDataSet1 = new UsersDataSet("Roma");
        Assert.assertEquals(usersDataSet.getId(), 5);
        Assert.assertEquals(usersDataSet1.getId(), -1);
    }
}

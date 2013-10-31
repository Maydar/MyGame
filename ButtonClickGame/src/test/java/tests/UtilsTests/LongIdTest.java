package tests.UtilsTests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

import java.util.Random;


public class LongIdTest {

    @Test
    public void testGetLong() throws Exception {

        LongId<SessionId> test = new LongId<SessionId>(4);
        Assert.assertEquals(test.getLong(), new Integer(4));
    }

    @Test
    public void testSetLong() throws Exception {

        LongId<SessionId> test = new LongId<SessionId>(4);
        Random rnd = new Random();
        rnd.setSeed(this.hashCode());
        int x = rnd.nextInt();
        test.setId(x);
        Assert.assertEquals((int)test.getLong(), x);
    }

    @Test
    public void testEquals() throws Exception {
        LongId<SessionId> test = new LongId<SessionId>(4);
        LongId<SessionId> test2 = new LongId<SessionId>(4);
        LongId<SessionId> test3 = new LongId<SessionId>(5);
        Assert.assertTrue(test2.equals(test));
        Assert.assertFalse(test3.equals(test2));




    }

    @Test
    public void testHashCode() throws Exception {
        LongId<SessionId> test = new LongId<SessionId>(4);
        Assert.assertEquals(test.hashCode(), 4);

    }
}

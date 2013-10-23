package tests.UtilsTests;

import junit.framework.Assert;
import org.testng.annotations.Test;
import ru.mail.projects.account.database.impl.DatabaseServiceImpl;
import ru.mail.projects.base.DatabaseService;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.ReflectionHelper;
import ru.mail.projects.utils.SessionId;


public class ReflectionHelperTest {
    @Test
    public void testCreateInstance() throws Exception {
        DatabaseService databaseService;
        Assert.assertTrue(ReflectionHelper.createInstance("ru.mail.projects.account.database.impl.DatabaseServiceImpl") instanceof Object);


    }

    @Test
    public void testSetFieldValue() throws Exception {
        LongId<SessionId> sessionIdLongId = new LongId<SessionId>(12);
        ReflectionHelper.setFieldValue(sessionIdLongId, "id", "13");
        Assert.assertEquals(new Integer(13), sessionIdLongId.getLong());

    }
}

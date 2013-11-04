package tests.MessageSystemTests;

import junit.framework.Assert;

import org.testng.annotations.Test;

import ru.mail.projects.base.Address;
import ru.mail.projects.messages.MsgAddUserId;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

public class MsgAddUserIdTest {

    @Test
    public void testCreateMsg() throws  Exception {

        Address addrFr = new Address();
        Address addrTo = new Address();
        LongId<SessionId> sessionId = new LongId<SessionId>(10);

        MsgAddUserId msgUpd = new MsgAddUserId(addrFr, addrTo, sessionId, "Roma");
        Assert.assertEquals(msgUpd.getAddrFrom(), addrFr);
        Assert.assertEquals(msgUpd.getUserName(), "Roma");
        Assert.assertEquals(msgUpd.getSessionId(), sessionId);
    }
}

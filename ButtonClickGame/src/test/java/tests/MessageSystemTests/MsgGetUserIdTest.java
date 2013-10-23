package tests.MessageSystemTests;

import junit.framework.Assert;

import org.testng.annotations.Test;

import ru.mail.projects.base.Address;
import ru.mail.projects.messages.MsgGetUserId;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;

public class MsgGetUserIdTest {

    @Test
    public void testCreateMsgGetUser() throws Exception {

        Address from = new Address();
        Address to = new Address();
        LongId<SessionId> sessionId = new LongId<SessionId>(10);
        String msgName = "Msg";
        MsgGetUserId msg = new MsgGetUserId(from, to, sessionId, "Msg");

        Assert.assertEquals(msg.getName(), msgName);
        Assert.assertEquals(msg.getSession(), sessionId);
        Assert.assertEquals(msg.getAddress(), from);
    }
}

package tests.MessageSystemTests;

import junit.framework.Assert;

import org.testng.annotations.Test;

import ru.mail.projects.base.Address;
import ru.mail.projects.messages.MsgUpdateUserId;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;
public class MsgUpdateUserIdTest {

    @Test
    public void testCreateMsgUserUpdate() throws Exception {

        Address addrFr = new Address();
        Address addrTo = new Address();
        LongId<SessionId> sessionId = new LongId<SessionId>(10);
        LongId<UserId> userId = new LongId<UserId>(10);

        MsgUpdateUserId msgUpd = new MsgUpdateUserId(addrFr, addrTo, sessionId, userId);
        Assert.assertEquals(msgUpd.getSession(), sessionId);
        Assert.assertEquals(msgUpd.getUserId(), userId);
        Assert.assertEquals(msgUpd.getTo(), addrTo);
        Assert.assertEquals(msgUpd.getFrom(), addrFr);
    }
}

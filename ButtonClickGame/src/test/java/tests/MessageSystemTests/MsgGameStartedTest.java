package tests.MessageSystemTests;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.testng.annotations.Test;

import ru.mail.projects.base.Address;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.frontend.impl.UserSession;
import ru.mail.projects.messages.MsgGameStarted;
import ru.mail.projects.utils.Context;

public class MsgGameStartedTest {

    @Test
    public void testCreateMsgGameStarted() throws Exception {

        Address from = new Address();
        Address to = new Address();
        int number = 5;
        AtomicInteger Id = new AtomicInteger(number);
        UserSession sess1 = null;
        MsgGameStarted msg = new MsgGameStarted(from, to, sess1, sess1, Id);

        Assert.assertEquals(msg.getFirstSession(), null);
        Assert.assertEquals(msg.getGameSessionId().intValue(), Id.get());
    }
}
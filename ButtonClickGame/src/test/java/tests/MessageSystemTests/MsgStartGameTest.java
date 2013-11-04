package tests.MessageSystemTests;

import junit.framework.Assert;

import org.testng.annotations.Test;

import ru.mail.projects.base.Address;
import ru.mail.projects.messages.MsgStartGame;

public class MsgStartGameTest {

    @Test
    public void testMsgStartGame() throws Exception {

        Address from = new Address();
        Address to = new Address();

        MsgStartGame msg = new MsgStartGame(from, to, null, null);

        Assert.assertEquals(msg.getAddress(), from);
        Assert.assertEquals(msg.getFirstSession(), null);
    }
}

package tests.MessageSystemTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.base.Frontend;
import ru.mail.projects.base.GameMechanics;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.Msg;
import ru.mail.projects.frontend.impl.FrontendImpl;
import ru.mail.projects.game.mechanics.impl.GameMechanicsImpl;
import ru.mail.projects.message.system.impl.MessageSystemImpl;
import ru.mail.projects.messages.MsgUpdateUserId;
import ru.mail.projects.utils.Context;
import ru.mail.projects.utils.LongId;
import ru.mail.projects.utils.SessionId;
import ru.mail.projects.utils.UserId;


import java.util.Queue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MessageSystemImplTest {
    private Context context;
    private GameMechanics gameMechanics;
    private Frontend frontend;

    @BeforeMethod
    public void setUp() throws Exception {
        context = mock(Context.class);
        gameMechanics = new GameMechanicsImpl(context);
        frontend = new FrontendImpl(context);
    }

    @Test
    public void testAddServiceAndGetQueue() throws Exception {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.addService(gameMechanics);
        messageSystem.addService(frontend);

        Assert.assertNotNull(messageSystem.getQueue(frontend.getAddress()));
        Assert.assertNotNull(messageSystem.getQueue(gameMechanics.getAddress()));

    }

    @Test
    public void testSendMessage() throws Exception {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.addService(gameMechanics);
        messageSystem.addService(frontend);
        LongId<SessionId> sessionId = new LongId<SessionId>(10);
        LongId<UserId> userId = new LongId<UserId>(10);
        MsgUpdateUserId msgUpd = new MsgUpdateUserId(gameMechanics.getAddress(), frontend.getAddress(), sessionId, userId);
        messageSystem.sendMessage(msgUpd);
        Queue<Msg> msgQueue = messageSystem.getQueue (frontend.getAddress());
        Assert.assertNotNull(msgQueue);
        MsgUpdateUserId message = (MsgUpdateUserId)msgQueue.poll();
        Assert.assertEquals(msgUpd, message);


    }
}

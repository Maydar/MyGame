package tests.GameMechanicTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import ru.mail.projects.game.mechanics.impl.GameState;


public class GameStateTest {
    @BeforeMethod
    public void setUp() throws Exception {
        GameState gameState = GameState.started;
        Assert.assertEquals("Game started!", gameState.toString());

    }
}

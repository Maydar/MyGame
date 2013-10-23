package tests.UtilsTests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.utils.Context;

public class ContextTest {
    Context context = new Context();


    @Test
    public void testAddGet() throws Exception {
        String str1 = new String("dsa");
        String str2 = new String("dsad");
        Double doble = new Double(3.32);

        context.add(String.class, str1);
        context.add(Double.class, doble);
        Assert.assertEquals(context.get(String.class), str1);
        Assert.assertEquals(context.get(Double.class), doble);

        try {
            context.add(String.class, str2);
        }
        catch (IllegalArgumentException e) {

        }


    }
}

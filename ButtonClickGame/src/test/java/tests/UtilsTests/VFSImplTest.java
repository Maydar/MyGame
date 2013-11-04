package tests.UtilsTests;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.projects.base.VFS;
import ru.mail.projects.vfs.impl.VFSImpl;

public class VFSImplTest {

    public VFS vfs = new VFSImpl("./resources");
    @Test
    public void testMainFunctions() throws Exception {

        Assert.assertTrue(vfs.isExist("./src"));
        Assert.assertFalse(vfs.isExist("./srsdfc"));
        Assert.assertTrue(vfs.isDirectory("./src"));
        Assert.assertFalse(vfs.isDirectory("./src/test.txt"));
        Assert.assertEquals(vfs.getRoot(), "./resources");
        Assert.assertNotSame(vfs.getAbsolutePath("./src"), "./src");
    }

    @Test
    public void testGetBytes() throws Exception {

        vfs.getBytes("./src");
        vfs.getBytes("./src/test.txt");
        Assert.assertNotNull(vfs.getBytes("./resources/userSess.xml"));
    }

    @Test
    public void testGetUTF8Text() throws Exception {
        vfs.getUTF8Text("./src/test.txt");
        Assert.assertNotNull(vfs.getUTF8Text("./src/test.txt"));
        Assert.assertNull(vfs.getUTF8Text("./src"));
        Assert.assertNotNull(vfs.getUTF8Text("./resources/userSess.xml"));
    }

    @Test
    public void testFileIterator() throws Exception {

        Assert.assertNotNull(vfs.getIterator("./src").next());
        Assert.assertTrue(vfs.getIterator("./src").hasNext());
        vfs.getIterator("./src").remove();
        Assert.assertTrue(vfs.getIterator("./resources/userSess.xml").hasNext());
        Assert.assertNotNull(vfs.getIterator("./src/main").next());
    }

    @Test
    public void AbsolutePathTest() throws Exception {

        String abs = vfs.getAbsolutePath("./src/test.txt");
        Assert.assertTrue(abs.endsWith("./src/test.txt"));
        Assert.assertTrue(abs.length()>"./src/test.txt".length());
    }
}

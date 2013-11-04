package tests.ResourceTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.mail.projects.base.Resource;
import ru.mail.projects.base.ResourceSystem;
import ru.mail.projects.base.VFS;
import ru.mail.projects.resource.system.impl.ResourceSystemImpl;
import ru.mail.projects.resources.DbConnectionResource;
import ru.mail.projects.resources.UserSessResource;
import ru.mail.projects.vfs.impl.VFSImpl;

import java.util.HashMap;
import java.util.Map;


public class ResourceSystemImplTest {

    private VFS vfs;
    private ResourceSystem resourceSystem;
    private ResourceSystem resourceSystem2;

    @BeforeClass
    public void setUp() {
        vfs = new VFSImpl("./resources");
        resourceSystem = new ResourceSystemImpl(vfs);
    }
    @Test
    public void testGetResource() throws Exception {
        UserSessResource userSessResource;
        resourceSystem.loadResources();
        userSessResource = (UserSessResource)resourceSystem.getResource("./resources/userSess.xml");
        Assert.assertNotNull(userSessResource);
        Assert.assertEquals(userSessResource.getPlayTime(), 1000);

    }

    @Test
    public void testLoadResources() throws Exception {
        resourceSystem.loadResources();
        Assert.assertNotNull(resourceSystem.getResources());
    }

    @Test
    public void testSet(){
        UserSessResource uss = new UserSessResource();
        uss.setHealth(50);
        Assert.assertEquals(uss.getPlayTime(), 50);
    }
}

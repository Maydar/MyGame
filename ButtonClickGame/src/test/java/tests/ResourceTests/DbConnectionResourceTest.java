package tests.ResourceTests;
import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ru.mail.projects.resources.DbConnectionResource;

public class DbConnectionResourceTest {
    DbConnectionResource dbConnectionResource;

    @BeforeTest
    public void setUp() {

        dbConnectionResource = new DbConnectionResource();
    }

    @Test
    public void testSetUsername() throws Exception {

        String name = "Maydar";
        dbConnectionResource.setUsername(name);
        Assert.assertEquals(name, dbConnectionResource.getUsername());
    }

    @Test
    public void testSetPassword() throws Exception {

        String password = "1234";
        dbConnectionResource.setPassword(password);
        Assert.assertEquals(password, dbConnectionResource.getPassword());
    }

    @Test
    public void testSetURL() throws Exception {

        String url = "www.mycom.ru";
        dbConnectionResource.setUrl(url);
        Assert.assertEquals(url, dbConnectionResource.getUrl());
    }

    @Test
    public void testSetDriver() throws Exception {

        String driver = "jdbc";
        dbConnectionResource.setDriver(driver);
        Assert.assertEquals(driver, dbConnectionResource.getDriver());
    }

    @Test
    public void testHibernateDialect() throws Exception {

        String hibernate = "hibernate";
        dbConnectionResource.setHibernateDialect(hibernate);
        Assert.assertEquals(hibernate, dbConnectionResource.getHibernateDialect());
    }

    @Test
    public void testSetHibernateHbm2ddlAuto() throws Exception {

        String HibernateHbm2ddlAuto = "hib_auto";
        dbConnectionResource.setHibernateHbm2ddlAuto(HibernateHbm2ddlAuto);
        Assert.assertEquals(HibernateHbm2ddlAuto, dbConnectionResource.getHibernateHbm2ddlAuto());
    }

}

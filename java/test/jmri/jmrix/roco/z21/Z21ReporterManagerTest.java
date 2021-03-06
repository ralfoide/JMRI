package jmri.jmrix.roco.z21;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for Z21ReporterManager class.
 *
 * @author Paul Bender Copyright (C) 2016
 **/

public class Z21ReporterManagerTest {

   @Test
   public void ConstructorTest(){
      Z21SystemConnectionMemo memo = new Z21SystemConnectionMemo();
      Z21InterfaceScaffold tc = new Z21InterfaceScaffold();
      memo.setTrafficController(tc);
      Assert.assertNotNull("Z21ReporterManager constructor",new Z21ReporterManager(memo));
   }

   @Before
   public void setUp(){
        apps.tests.Log4JFixture.setUp();
        jmri.util.JUnitUtil.resetInstanceManager();
        jmri.util.JUnitUtil.initDefaultUserMessagePreferences();
   }

   @After
   public void tearDown(){
        apps.tests.Log4JFixture.tearDown();
        jmri.util.JUnitUtil.resetInstanceManager();
   }

}

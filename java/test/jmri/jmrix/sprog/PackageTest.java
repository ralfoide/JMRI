package jmri.jmrix.sprog;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * tests for the jmri.jmrix.sprog package
 *
 * @author	Bob Jacobsen
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
   SprogSystemConnectionMemoTest.class,
   SprogTrafficControllerTest.class,
   SprogMessageTest.class,
   SprogCSStreamPortControllerTest.class,
   SprogPowerManagerTest.class,
   SprogTurnoutManagerTest.class,
   SprogTurnoutTest.class,
   SprogCSTurnoutTest.class,
   SprogCommandStationTest.class,
   SprogConnectionTypeListTest.class,
   jmri.jmrix.sprog.pi.PackageTest.class,
   jmri.jmrix.sprog.serialdriver.PackageTest.class,
   jmri.jmrix.sprog.sprog.PackageTest.class,
   jmri.jmrix.sprog.sprogCS.PackageTest.class,
   jmri.jmrix.sprog.sprognano.PackageTest.class,
   jmri.jmrix.sprog.configurexml.PackageTest.class
})
public class PackageTest {
}

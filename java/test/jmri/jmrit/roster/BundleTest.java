package jmri.jmrit.roster;

import java.util.Locale;
import org.junit.Assert;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Tests for the Bundle class
 *
 * @author Bob Jacobsen Copyright (C) 2012
 */
public class BundleTest  {

    @Test public void testGoodKeys() {
        Assert.assertEquals("Roster", Bundle.getMessage("MenuItemRoster"));
        Assert.assertEquals("Tools", Bundle.getMessage("MenuTools"));
        Assert.assertEquals("Turnout", Bundle.getMessage("BeanNameTurnout"));
    }

    @Test public void testBadKey() {
        try {
            Bundle.getMessage("FFFFFTTTTTTT");
        } catch (java.util.MissingResourceException e) {
            return;
        } // OK
        Assert.fail("No exception thrown");
    }

}

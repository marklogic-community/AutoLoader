package com.marklogic.autoloader;

import org.testng.annotations.Test;

import java.io.PrintStream;
import java.io.File;

/**
 * @author Clark D. Richey Jr.
 *         date: Aug 1, 2008
 */
public class AutoLoaderTest {

    private AutoLoader autoLoader;

    @Test(expectedExceptions = AssertionError.class)
    public void testConstructorNullPollingDirectory() {
        new AutoLoader(null, new File("/"), "test.properties");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testConstructorNullCompletedDirectory() {
        new AutoLoader(new File("/"), null, "test.properties");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testConstructorNullConfigFile() {
        new AutoLoader(new File("/"), new File("/"), null);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testConstructorEmptyConfigFile() {
        new AutoLoader(new File("/"), new File("/"), "");
    }
}

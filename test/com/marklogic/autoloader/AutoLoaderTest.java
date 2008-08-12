package com.marklogic.autoloader;
/*
Copyright 2002-2008 Mark Logic Corporation. All Rights Reserved.

This software is licensed to you under the terms and conditions

specified at http://www.apache.org/licenses/LICENSE-2.0. If you

do not agree to those terms and conditions, you must cease use of

and destroy any copies of this software that you have downloaded.
*/
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

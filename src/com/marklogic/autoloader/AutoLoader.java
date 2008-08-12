package com.marklogic.autoloader;

import org.sadun.util.polling.DirectoryPoller;

import java.io.File;

/**
 * @author Clark D. Richey Jr.
 *         date: Jul 30, 2008
 */
public class AutoLoader {
    private DirectoryPoller poller;

    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            displayUsageInstructions();
            return;
        }
        String pollingDirectoryName = args[0];
        final File pollingDirectory = new File(pollingDirectoryName);
        if (!pollingDirectory.isDirectory()) {
            displayUsageInstructions();
            System.out.println(pollingDirectoryName + " is not a valid directory!");
            return;
        }

        String completedDirectoryName = args[1];
        final File completedDirectory = new File(completedDirectoryName);
        if (!completedDirectory.isDirectory()) {
            displayUsageInstructions();
            System.out.println(completedDirectoryName + " is not a valid directory!");
            return;
        }

        String configFileName = args[2];
        final File configFile = new File(configFileName);
        if (configFile.isDirectory()) {
            displayUsageInstructions();
            System.out.println(completedDirectoryName + " is a directory, not a file!");
            return;
        }

        System.out.println("Initiating directory polling, press Ctrl-C to end...");
        final AutoLoader loader = new AutoLoader(pollingDirectory, completedDirectory, configFileName);
    }

    private static void displayUsageInstructions() {
        System.out.println("Usage: java AutoLoader {polling directory} {completed directory} {path to RecordLoader properties file}");
    }

    public AutoLoader(File pollingDirectory, File completedDirectory, String configFileName) {
        assert pollingDirectory != null : "pollingDirectory must not be null";
        assert completedDirectory != null : "completedDirectory must not be null";
        assert configFileName != null && configFileName.trim().length() > 0 : "configFileName must not be null or empty";
     
        poller = new DirectoryPoller(pollingDirectory);
        poller.setAutoMoveDirectory(pollingDirectory, completedDirectory);
        poller.setAutoMove(true);
        poller.addPollManager(new RecordLoaderBridge(configFileName));
        // we need a shutdown hook to ensure that the loader is properly shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                poller.shutdown();
            }
        });
        poller.start();
    }

}

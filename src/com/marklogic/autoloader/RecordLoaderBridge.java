package com.marklogic.autoloader;

import org.sadun.util.polling.*;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import com.marklogic.ps.RecordLoader;

/**
 * @author Clark D. Richey Jr.
 *         date: Jul 30, 2008
 */
public class RecordLoaderBridge implements PollManager {
    private String[] recordLoaderConfigFile;
    private Thread recordLoaderThread;
    private AtomicBoolean filesWaiting;

    public RecordLoaderBridge(String recordLoaderConfigFile) {
        this.recordLoaderConfigFile = new String[] {recordLoaderConfigFile};
        filesWaiting = new AtomicBoolean(false);
    }

    public void cycleStarted(CycleStartEvent event) {
    }

    public void cycleEnded(CycleEndEvent event) {
    }

    public void directoryLookupStarted(DirectoryLookupStartEvent event) {
    }

    public void directoryLookupEnded(DirectoryLookupEndEvent event) {
    }

    public void fileSetFound(FileSetFoundEvent event) {
        initiateRecordLoader(event.getFiles());
    }

    public void fileMoved(FileMovedEvent event) {
    }

    public void fileFound(FileFoundEvent event) {
        initiateRecordLoader(new File[]{event.getFile()});
    }

    public void exceptionDeletingTargetFile(File file) {
        System.err.println("Unable to delete file - " + file.getAbsolutePath());
    }

    public void exceptionMovingFile(File file, File file1) {
        System.err.println("Unable to move file - " + file.getAbsolutePath());
    }

    private boolean initiateRecordLoader(File[] files) {
        /**
         * If there is currently a RecorLoader thread running (i.e. processing files) AND this is the first new
         * notification of new files then we will set the filesWaiting flag. If there is  currently a
         * RecorLoader thread running (i.e. processing files) but there has already been a queued notification of new
         * files then do nothing. If the RecordLoader thread isn't running, then go ahead and run it.
         */

        if(recordLoaderThread == null || !recordLoaderThread.isAlive()) {
            recordLoaderThread = new Thread(new RecordLoaderRunner(), "RecordLoader");
            recordLoaderThread.start();
            return true;
        }
        else if(recordLoaderThread.isAlive() && filesWaiting.get()) {
            return false;
        }
        else {
            filesWaiting.compareAndSet(false, true);
            return true;
        }
    }


    private class RecordLoaderRunner implements Runnable {
        private RecordLoader recordLoader;

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        public void run() {
            try {
                RecordLoader.main(recordLoaderConfigFile);
                if(filesWaiting.compareAndSet(true, false)) {
                    run();
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    
    }
}

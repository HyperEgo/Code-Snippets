package com.ultimate-rad-games;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;

import java.util.Properties;

/**
 * Test file events, listener, and watcher.
 */
public class FileEventTest implements FileListener {

    private final String HDR = "FileEventTest::";

    private Properties cfg = null;    

    private FileWatcher watcher;

    private String source;

    /**
    * Public access constructor.
    */
    public FileEventTest(final Properties prop) { 
        this.cfg = prop;
        this.source = getDirectory(this.cfg.getProperty("source", "/storage/vbone/video/"));
        /**
         * Configure properties file or
         * hard-code File object with source directory,
         * pass to FileWatcher
         * e.g. File directory = new File(/path/to/directory);
         */
        this.watcher = new FileWatcher(new File( this.source ));
        this.watcher.addListener(this).watch();
    }

    /**
    * Get directory location for String Property artifact.
    * @param property String property name.
    * @return String directory name.
    */
    private String getDirectory(final String property) {

        final String DELIM = "/";
        final File DIR = new File(property);        
        if (!DIR.exists()) {
            DIR.mkdirs();            
        }

        return DIR.getAbsolutePath() + DELIM;
    }

    /**
    * FileWatcher listener implementation, on file created for configured directory.
    * @param event FileEvent object input
    */
    public void onCreated(FileEvent event) {

        final String DEBUG = HDR + "onCreated()::";

        File file = event.getFile();

        System.out.println( DEBUG + file.getName() );
    }

    /**
    * FileWatcher listener implementation, on file modififed for configured directory.
    * @param event FileEvent object input
    */
    public void onModified(FileEvent event) {

        final String DEBUG = HDR + "onModified()::";
    }

    /**
    * FileWatcher listener implementation, on file deleted for configured directory.
    * @param event FileEvent object input
    */    
    public void onDeleted(FileEvent event) {

        final String DEBUG = HDR + "onDeleted()::";
    }
} 

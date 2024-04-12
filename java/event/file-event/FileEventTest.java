import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.InterruptedException;

import java.util.Properties;
import java.util.List;
import java.util.Collection;

public class FileEventTest {    

    private final String HDR = "FileEventTest::";

    private File directory;

    /**
    * Public access constructor.
    * @param prop Properties object for configuration file parameter input.
    */
    public FileEventTest(final File dir) { 
	this.directory = dir;
        new SourceCatcher( dir );
	prime();
    }

    /**
    * Initial file monitor and browser, recurse subdirectories.
    */
    private void prime() {        

        final String DEBUG = HDR + "prime()::";

	File[] files = this.directory.listFiles();

        for (File file : files) {
            if (file.exists()) {
                System.out.println(DEBUG + "Initial file loop = " + file.getName());
            }
        }        
    }

    /**
     * File event catcher for source video input directory.
     */
    private class SourceCatcher extends FileEventCatcher {

        final String HEADER = HDR + "SourceCatcher::";

        private File directory;

        /**
         * Public access ctor.
         */
        public SourceCatcher(File dir) {
            super(dir);
            this.directory = dir;
        }

        @Override
        public void onCreated(FileEvent event) {

            final String DEBUG = HEADER + "onCreated()::";

            File file = event.getFile();

	    System.out.println(DEBUG + file.getName());
        }

        @Override
        public void onModified(FileEvent event) { 

	    final String DEBUG = HEADER + "onModified()::";

            File file = event.getFile();

	    System.out.println(DEBUG + file.getName());
	}


        @Override
        public void onDeleted(FileEvent event) { 

	    final String DEBUG = HEADER + "onDeleted()::";

            File file = event.getFile();

	    System.out.println(DEBUG + file.getName());
	}
    }    

     /**
     * Main()
     * @param args String[] input args
     */
    public static void main(String[] args) {

	    new FileEventTest( new File("./") );
    }
} 

import java.io.File;

/**
 * Directory monitor with file change notification event integration.
 * <p>This class catches file and directory change notifications.</p>
 */
public class FileEventCatcher implements FileListener {

    private final String HDR = "FileEventCatcher::";

    private FileWatcher watcher;

    public FileEventCatcher(File directory) {

        this.watcher = new FileWatcher( directory );
        this.watcher.addListener(this).watch();       
    }

    /**
    * FileWatcher listener implementation, on file created for configured directory.
    * @param event FileEvent object input
    */
    public void onCreated(FileEvent event) {

        final String DEBUG = HDR + "onCreated()::";

	System.out.println( DEBUG + event.getFile().getName() );
    }

    /**
    * FileWatcher listener implementation, on file modififed for configured directory.
    * @param event FileEvent object input
    */
    public void onModified(FileEvent event) {

        final String DEBUG = HDR + "onModified()::";

	System.out.println( DEBUG + event.getFile().getName() );
    }

    /**
    * FileWatcher listener implementation, on file deleted for configured directory.
    * @param event FileEvent object input
    */    
    public void onDeleted(FileEvent event) {

        final String DEBUG = HDR + "onDeleted()::";

	System.out.println( DEBUG + event.getFile().getName() );
    }        
}

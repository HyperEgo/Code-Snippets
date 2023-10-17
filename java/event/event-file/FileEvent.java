import java.io.File;
import java.util.EventObject;

/**
 * Encapsulate events in to file type events.
 */
public class FileEvent extends EventObject {

    /**
    * Public access constructor.
    * @param file File object input.
    */
    public FileEvent(File file) {
        super(file);
    }

    /**
    * Get file.
    * @return File object.
    */    
    public File getFile() {
        return (File)getSource();
    }
}

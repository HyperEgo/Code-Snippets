package com.ultimate-rad-games;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;

import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Watch configured directory for File events, notify listeners.
 */
public class FileWatcher implements Runnable {

    protected List<FileListener> listeners = new ArrayList<>();
    protected final File folder;
    protected static final List<WatchService> watchServices = new ArrayList<>();

    /**
    * Public access constructor.
    * @param folder File object input, configure watch directory.
    */
    public FileWatcher(File folder) {
        this.folder = folder;
    }

    /**
    * Control thread, start watch directory utility.
    */
    public void watch() {
        if (folder.exists()) {
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

            Path path = Paths.get(folder.getAbsolutePath());
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);  // register event types
            watchServices.add(watchService);

            boolean poll = true;
            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
    * Event poll facilitator, token key manager, notify listeners for events.
    * @param watchService WatchService utility token key issuer for event types.
    * @return boolean result compare.
    */
    protected boolean pollEvents(WatchService watchService) throws InterruptedException {

        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();

        for (WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
        }

        return key.reset();
    }

    /**
    * Notify listeners for watch event type and file associated.
    * @param kind WatchEvent.Kind event type
    * @param file File object input.
    */
    protected void notifyListeners(WatchEvent.Kind<?> kind, File file) {

        FileEvent event = new FileEvent(file);

        if (kind == ENTRY_CREATE) {
            for (FileListener listener : listeners) {
                listener.onCreated(event);
            }
            if (file.isDirectory()) {
                new FileWatcher(file).setListeners(listeners).watch();
            }
        } else if (kind == ENTRY_MODIFY) {
            for (FileListener listener : listeners) {
                listener.onModified(event);
            }
        } else if (kind == ENTRY_DELETE) {
            for (FileListener listener : listeners) {
                listener.onDeleted(event);
            }
        }
    }

    /**
    * Add listener.
    * @param listener FileListener object input.
    * @return FileWatcher parent object.
    */
    public FileWatcher addListener(FileListener listener) {
        listeners.add(listener);
        return this;
    }

    /**
    * Remove listener.
    * @param listener FileListener object input.
    * @return FileWatcher parent object.
    */    
    public FileWatcher removeListener(FileListener listener) {
        listeners.remove(listener);
        return this;
    }

    /**
    * Get listener list.
    * @return List<FileListener> listener list.
    */    
    public List<FileListener> getListeners() {
        return listeners;
    }

    /**
    * Set listener list.
    * @param listeners List<FileListener> listener list.
    * @return FileWatcher parent object.
    */    
    public FileWatcher setListeners(List<FileListener> listeners) {
        this.listeners = listeners;
        return this;
    }

    /**
    * Get current WatcherService registered event types.
    * @return List<WatchService> service utility event types.
    */        
    public static List<WatchService> getWatchServices() {
        return Collections.unmodifiableList(watchServices);
    }
}

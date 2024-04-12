package com.ultimate-rad-games;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * File manipulation: hard-link, delete, verify.
 */
public class FileUtil {

    /**
    * Hard-link file source to target.
    * @param source File object source.
    * @param target File object target.
    */
    private void createHardLink(final File source, final File target) throws IOException {

        final Path SOURCE = Paths.get(source.getAbsolutePath());
        final Path TARGET = Paths.get(target.getAbsolutePath());

        if (!Files.isDirectory(TARGET)) {
            Files.deleteIfExists(TARGET);
            Files.createLink(TARGET, SOURCE);            
        }
    }

    /**
    * Append file name for String input, keep original extension.
    * @param file File object input.
    * @param append String appender.
    * @return String appended file name.
    */
    private String getAppender(final File file, final String append) { 

        final String DOT = ".";        
        final String FILE_NAME = file.getName();
        final String BASE = FilenameUtils.removeExtension(FILE_NAME);
        final String EXT = FilenameUtils.getExtension(FILE_NAME);

        return isString(EXT) ? (BASE + append + DOT + EXT) : (BASE + append);
    }

    /**
     * Test string integrity and validity.
     * @param input String input data.
     * @return boolean describes string status.
     */
    private boolean isString(final String input) {
        return (input != null && !input.trim().isEmpty());
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
    * Interface builder for FileFilter object.
    * @param regex String regular expression.
    * @return FileFilter filter object.
    */
    private FileFilter getFileFilter(final String regex) {
        
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return file.getName().contains(regex);
            }
        };
        return filter;        
    }

    /**
    * Hard-link file(s) from input to output directory using file regular expression.
    * @param inDir String input directory
    * @param outDir String output directory
    * @param regex String regular expression.
    */
    private void linkFiles(final String inDir, final String outDir, final String regex) throws IOException {

        final File inDir = new File(inDir);        
        final FileFilter filter = getFileFilter(regex);
        final File[] files = inDir.listFiles(filter);

        for (File file : files) {
            if (file.exists()) {
                createHardLink( file, new File( outDir + file.getName() ) );
            }
        }
    }

    /**
    * Delete files from directory using file regular expression.
    * @param inFile File name.
    * @param directory String directory name.
    * @param regex String regular expression.
    */
    public void deleteFiles(final File inFile, final String directory) {

        final String DEBUG = HDR + "deleteFiles()::";

        final String REGEX = FilenameUtils.getBaseName(inFile.getAbsolutePath());
        final File OPS = new File(directory);

        final FileFilter filter = getFileFilter(REGEX);
        final File[] files = OPS.listFiles(filter);

        for (File file : files) {
            if (file.exists()) {
                file.delete();
                System.out.println(DEBUG + directory + file.getName());                
            }
        }
    }
}

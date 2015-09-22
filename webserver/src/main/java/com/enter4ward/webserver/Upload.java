package com.enter4ward.webserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The Class Upload.
 */
public class Upload {

    /** The filename. */
    private String filename;

    /** The prefix. */
    private String name;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /** The suffix. */
    private String extension;

    /** The file. */
    private File file;

    /**
     * Instantiates a new upload.
     *
     * @param f
     *            the f
     * @param n
     *            the n
     */
    public Upload(final File f, final String n) {
        file = f;
        filename = n;
        int dotIndex = n.lastIndexOf('.');

        name = n.substring(0, dotIndex);
        extension = n.substring(dotIndex + 1, n.length());

    }

    /**
     * Gets the file.
     *
     * @return the file
     */
    public final File getFile() {
        return file;
    }

    /**
     * Gets the filename.
     *
     * @return the filename
     */
    public final String getFilename() {
        return filename;
    }

    /**
     * Gets the extension.
     *
     * @return the extension
     */
    public final String getExtension() {
        return extension;
    }

    /**
     * Copy to.
     *
     * @param dir
     *            the dir
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public final void copyTo(final String dir) throws IOException {
        FileOutputStream newFile = new FileOutputStream(dir);
        Files.copy(file.toPath(), newFile);
    }

}

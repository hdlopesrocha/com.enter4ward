package com.enter4ward.webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The Class Html.
 */
public final class Html {

    /**
     * Instantiates a new html.
     */
    private Html() {

    }

    /**
     * From file.
     *
     * @param url
     *            the url
     * @return the string
     */
    public static String fromFile(final String url) {
        try {
            return new String(Files.readAllBytes(Paths.get(url)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

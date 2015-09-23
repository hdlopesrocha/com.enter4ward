package com.enter4ward.webserver;

import com.enter4ward.session.Session;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;

/**
 * The Class Response.
 */
public class Response {

    /** The request. */
    private Request request;

    /** The version. */
    private String version = "1.1";

    /** The status. */
    private String status = "200 OK";

    /** The encoding. */
    private String encoding = null;

    /** The content disposition. */
    private String contentDisposition = null;

    /** The data. */
    private byte[] data = new byte[0];

    /** The content type. */
    private String contentType = "";

    /**
     * Gets the content type.
     *
     * @return the content type
     */
    public final String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param c
     *            the new content type
     */
    public final void setContentType(final String c) {
        this.contentType = c;
    }

    /**
     * Instantiates a new response.
     *
     * @param s
     *            the s
     * @param r
     *            the r
     */
    public Response(final HttpServer s, final Request r) {
        request = r;
    }

    /**
     * Gets the server time.
     *
     * @return the server time
     */
    final String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Builds the header.
     *
     * @param size
     *            the size
     * @param cookie
     *            the cookie
     * @return the string
     */
    final String buildHeader(final int size, final Session cookie) {
        String s = "";
        s += "HTTP/" + version + " " + status + "\n";
        s += "Date: " + getServerTime() + "\n";
        s += "Content-Type: " + contentType + "\n";
        s += "Content-Length: " + size + "\n";
        s += "Server: com.enter4ward/" + HttpServer.VERSION + "\n";
        s += "Connection: "+(request.keepAlive()? "keep-alive":"close")+"\n";
       
        if (encoding != null) {
            s += "Content-Encoding: gzip\n";
        }

        if (cookie != null) {
            s += "Set-Cookie: ID=" + cookie.getId() + "; Max-Age="
                    + cookie.getMaxAge() + "; Version=1;Path=/;\n";
        }

        if (contentDisposition != null) {
            s += "Content-Disposition: " + contentDisposition + "\n";
        }

        s += "\n";
        return s;
    }

    /**
     * Sets the content disposition.
     *
     * @param c the new content disposition
     */
    public final void setContentDisposition(final String c) {
        this.contentDisposition = c;
    }

    /**
     * Send data.
     *
     * @param socket
     *            the socket
     * @param session
     *            the session
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public final void send(final Socket socket, final Session session)
            throws IOException {
        if (request.acceptEncoding("gzip")) {
            encoding = "gzip";
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(data);
            gzip.close();
            data = out.toByteArray();
        }

        byte[] header = buildHeader(data.length, session).getBytes();
        byte[] packg = new byte[header.length + data.length];
        System.arraycopy(header, 0, packg, 0, header.length);
        System.arraycopy(data, 0, packg, header.length, data.length);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.write(packg);
        
    }

    /**
     * Sets the data.
     *
     * @param d
     *            the new data
     */
    public final void setData(final byte[] d) {
        data = d;
    }

}

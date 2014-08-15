package hidrogine.webserver;

import java.util.TreeMap;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * The Class Session.
 */
public class Session implements Delayed {

    /** The max age. */
    private long maxAge;

    /** The attributes. */
    private TreeMap<String, Object> attributes;

    /** The id. */
    private String id;

    /**
     * Instantiates a new session.
     *
     * @param i
     *            the i
     * @param m
     *            the m
     */
    public Session(final String i, final long m) {
        maxAge = m;
        attributes = new TreeMap<String, Object>();
        id = i;
    }

    /**
     * Read.
     *
     * @param key
     *            the key
     * @return the object
     */
    public final Object read(final String key) {
        return attributes.get(key);
    }

    /**
     * Write.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public final void write(final String key, final Object value) {
        attributes.put(key, value);

    }

    /**
     * Gets the max age.
     *
     * @return the max age
     */
    public final long getMaxAge() {
        return maxAge;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    /**
     * Compare to.
     *
     * @param arg0
     *            the arg0
     * @return the int
     */
    @Override
    public final int compareTo(final Delayed arg0) {
        Session s = (Session) arg0;

        return s.getMaxAge() == getMaxAge() ? 0
                : s.getMaxAge() > getMaxAge() ? 1 : -1;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
     */
    /**
     * Gets the delay.
     *
     * @param unit
     *            the unit
     * @return the delay
     */
    @Override
    public final long getDelay(final TimeUnit unit) {
        long diff = maxAge - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);

    }
}

package io.relayr.model;

/**
 * A reading is the information gathered by the device.
 */
public class Reading {

    /** Timestamp - when reading is received on the platform */
    public final long received;
    /** Timestamp - when reading is recorded on the device */
    public final long recorded;
    /** Meaning of the reading. Every device has a {@link Device#model} which defines readings */
    public final String meaning;
    /** If device contains multiple readings they will be identified with the path */
    public final String path;
    /** Reading value is determined by meaning e.g. {@link Device#model} */
    public final Object value;

    public Reading(long received, long recorded, String meaning, String path, Object value) {
        this.received = received;
        this.recorded = recorded;
        this.meaning = meaning;
        this.path = path;
        this.value = value;
    }

}

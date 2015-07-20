package io.relayr.model.models;

import java.io.Serializable;
import java.util.List;

import io.relayr.model.models.transport.DeviceReading;

public class ReadingMeanings implements Serializable {

    private ModelLinks _links;
    private List<ReadingMeaning> meanings;

    /**
     * Returns list of possible reading meanings. Used with {@link DeviceReading#meaning}
     */
    public List<ReadingMeaning> getMeanings() {
        return meanings;
    }

    /**
     * Returns {@link ModelLinks} object with hyperlinks to other {@link ReadingMeaning} objects
     * @return {@link ModelLinks}
     */
    public ModelLinks getLinks() {
        return _links;
    }

    @Override public String toString() {
        return "ReadingMeanings{" +
                "_links=" + _links +
                ", meanings=" + meanings.toString() +
                '}';
    }
}

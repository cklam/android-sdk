package io.relayr.model.deviceModels;

import java.io.Serializable;
import java.util.List;

public class ReadingMeanings implements Serializable {

    private ModelLinks _links;
    private List<ReadingMeaning> meanings;

    public List<ReadingMeaning> getMeanings() {
        return meanings;
    }

    public ModelLinks getLinks() {
        return _links;
    }
}

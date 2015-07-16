package io.relayr.model.deviceModels;

import java.io.Serializable;
import java.util.List;

public class DeviceModels implements Serializable {

    private ModelLinks _links;
    private List<DeviceModel> models;
    private int count;
    private int limit;
    private int offset;

    public ModelLinks getLinks() {
        return _links;
    }

    public List<DeviceModel> getModels() {
        return models;
    }

    public int getCount() {
        return count;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}

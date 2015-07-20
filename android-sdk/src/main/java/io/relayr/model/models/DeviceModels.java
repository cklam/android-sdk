package io.relayr.model.models;

import java.io.Serializable;
import java.util.List;

import io.relayr.model.models.error.DeviceModelsException;

/**
 * Object that represents all device models supported on relayr platform
 * and a way of navigation through them.
 */
public class DeviceModels implements Serializable {

    private ModelLinks _links;
    private List<DeviceModel> models;
    private int count;
    private int limit;
    private int offset;

    /**
     * Returns {@link ModelLinks} object with hyperlinks to other {@link DeviceModel} objects
     * @return {@link ModelLinks}
     */
    public ModelLinks getLinks() {
        return _links;
    }

    /**
     * Returns all device model supported on relayr platform.
     */
    public List<DeviceModel> getModels() {
        return models;
    }

    /**
     * Returns {@link DeviceModel} specified with modelId or {@link DeviceModelsException}
     * if model with specified id doesn't exist.
     * @return {@link DeviceModel} or {@link DeviceModelsException}
     */
    public DeviceModel getModel(String modelId) throws DeviceModelsException {
        for (DeviceModel model : models)
            if (model.getId().equals(modelId))
                return model;

        throw DeviceModelsException.deviceModelNotFound();
    }

    /**
     * Returns total number of models.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns number of models that can be fetched when paging.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Return offset from the first model.
     */
    public int getOffset() {
        return offset;
    }

    @Override public String toString() {
        return "DeviceModels{" +
                "_links=" + _links +
                ", models=" + models.toString() +
                ", count=" + count +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}

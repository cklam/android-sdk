package io.relayr.model.deviceModels.transport;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ValueSchema implements Serializable {

    private String type;
    private String unit;
    private float minimum;
    private float maximum;
    private Object properties;
    @SerializedName("enum") private List<String> enums;

    public String getType() {
        return type;
    }

    public float getMinimum() {
        return minimum;
    }

    public float getMaximum() {
        return maximum;
    }

    public String getUnit() {
        return unit;
    }

    public List<String> getEnums() {
        return enums;
    }
}

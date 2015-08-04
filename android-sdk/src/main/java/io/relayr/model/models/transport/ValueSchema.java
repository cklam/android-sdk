package io.relayr.model.models.transport;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ValueSchema implements Serializable {

    private String type;
    private String unit;
    private Double minimum;
    private Double maximum;
    private Object properties;
    @SerializedName("enum") private List<String> enums;

    public String getType() {
        return type;
    }

    /**
     * Returns minimum value. Actual minimum can be specified with {@link #type} as integer or float
     * but because of simplification it will be boxed to double.
     */
    public Double getMinimum() {
        return minimum;
    }

    /**
     * Returns maximum value. Actual maximum can be specified with {@link #type} as integer or float
     * but because of simplification it will be boxed to double.
     */
    public Double getMaximum() {
        return maximum;
    }

    /**
     * Returns unit of measurement as a String value.
     * Use unit to describe {@link DeviceReading} accurately.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * When {@link #type} is equal to 'string' all possibilities are described within enum field
     * @return list of possible values
     */
    public List<String> getEnums() {
        return enums;
    }

    /**
     * When {@link #type} is equal to object map {@link #properties} to appropriate object
     * depending on {@link DeviceReading#meaning}
     */
    public Object getProperties() {
        return properties;
    }

    @Override public String toString() {
        return "ValueSchema{" +
                "type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", properties=" + properties +
                ", enums=" + enums +
                '}';
    }
}

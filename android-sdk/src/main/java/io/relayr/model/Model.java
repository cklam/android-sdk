package io.relayr.model;

import java.io.Serializable;
import java.util.List;

@Deprecated
public class Model implements Serializable {

    public Model(String id) {
        this.id = id;
    }

    /**
     * Auto generated uid
     */
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * Device name
     */
    private String name;
    /**
     * Device manufacturer
     */
    private String manufacturer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}

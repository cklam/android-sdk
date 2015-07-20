package io.relayr.model.models;

import java.io.Serializable;

public class DeviceResource implements Serializable {

    private String id;
    private String type;
    private String mediaUrl;
    private String mimeType;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override public String toString() {
        return "DeviceResource{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}

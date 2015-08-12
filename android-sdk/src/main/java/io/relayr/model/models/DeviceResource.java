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

    /**
     * Returns type of the resource. May be 'icon', 'banner' and ''
     */
    public String getType() {
        return type;
    }

    /**
     * Returns media url to be used for download.
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * Returns Mime type. (Examples: image/svg+xml,image/jpg...)
     */
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

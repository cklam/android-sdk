package io.relayr.model.deviceModels;

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
}

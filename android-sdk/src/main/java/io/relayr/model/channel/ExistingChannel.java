package io.relayr.model.channel;

import java.io.Serializable;
import java.util.List;

public class ExistingChannel implements Serializable {

    private String deviceId;
    private List<ChannelInfo> channels;

    public ExistingChannel(String deviceId, List<ChannelInfo> channels) {
        this.deviceId = deviceId;
        this.channels = channels;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public List<ChannelInfo> getChannels() {
        return channels;
    }

    @Override
    public String toString() {
        return "ExistingChannel{" +
                "deviceId='" + deviceId + '\'' +
                ", channels=" + channels +
                '}';
    }

    public class ChannelInfo {
        String channelId;
        String appId;
        String transport;

        public ChannelInfo(String channelId, String appId, String transport) {
            this.channelId = channelId;
            this.appId = appId;
            this.transport = transport;
        }

        @Override
        public String toString() {
            return "ChannelInfo{" +
                    "channelId='" + channelId + '\'' +
                    ", appId='" + appId + '\'' +
                    ", transport='" + transport + '\'' +
                    '}';
        }
    }

}




package io.relayr.model.channel;

import java.io.Serializable;

public class DataChannel implements Serializable {

    private String channelId;
    private ChannelCredentials credentials;

    public DataChannel(String channelId, ChannelCredentials credentials) {
        this.channelId = channelId;
        this.credentials = credentials;
    }

    public String getChannelId() {
        return channelId;
    }

    public ChannelCredentials getCredentials() {
        return credentials;
    }

    @Override
    public String toString() {
        return "DataChannel{" +
                "channelId='" + channelId + '\'' +
                ", credentials=" + credentials +
                '}';
    }

    public static class ChannelCredentials implements Serializable {

        private String user;
        private String password;
        private String topic;
        private String clientId;

        public ChannelCredentials(String user, String password, String topic, String clientId) {
            this.user = user;
            this.password = password;
            this.topic = topic;
            this.clientId = clientId;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        public String getTopic() {
            return topic;
        }

        public String getClientId() {
            return clientId;
        }

        @Override
        public String toString() {
            return "Credentials{" +
                    "user='" + user + '\'' +
                    ", password='" + password + '\'' +
                    ", topic='" + topic + '\'' +
                    ", clientId='" + clientId + '\'' +
                    '}';
        }
    }
}




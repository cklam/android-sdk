package io.relayr.model.deviceModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.relayr.model.deviceModels.error.DeviceModelsException;
import io.relayr.model.deviceModels.transport.Transport;

public class DeviceFirmware implements Serializable {

    private String binaries;
    private Map<String, Transport> transport;
    private String repository;
    private String releaseDate;
    private String releaseNotes;
    private String documentation;

    public String getBinaries() {
        return binaries;
    }

    public String getRepository() {
        return repository;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public String getDocumentation() {
        return documentation;
    }

    public Map<String, Transport> getTransports() {
        return transport;
    }

    public List<String> getTransportTypes() {
        return new ArrayList<>(transport.keySet());
    }

    public Transport getTransport(String type) throws DeviceModelsException {
        Transport transport = this.transport.get(type);
        if (transport == null) throw DeviceModelsException.transportNotFound();
        return transport;
    }
}

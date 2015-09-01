package io.relayr.model.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.relayr.model.models.error.DeviceModelsException;
import io.relayr.model.models.error.DeviceModelsTransportException;
import io.relayr.model.models.transport.Transport;

/**
 * Defines device firmware.
 * Device details depend on firmware version which means that for the same device and same device
 * model there can be more that one firmware versions.
 * DeviceFirmware defines at least one {@link Transport} type (ble, cloud)
 */
public class DeviceFirmware implements Serializable {

    private ModelLinks _links;
    private String binaries;
    private Map<String, Transport> transport = new HashMap<>();
    private String repository;
    private String releaseDate;
    private String releaseNotes;
    private String documentation;

    /**
     * Returns {@link ModelLinks} object with hyperlinks to other firmware versions
     * @return {@link ModelLinks}
     */
    public ModelLinks getLinks() {
        return _links;
    }

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

    /**
     * Returns {@link Map} of {@link Transport} objects defined by transport type
     * @return {@link Map} of {@link Transport}
     */
    public Map<String, Transport> getTransports() {
        return transport;
    }

    /**
     * Returns all available {@link Transport} types
     * @return {@link List} of transport types
     */
    public List<String> getTransportTypes() {
        return new ArrayList<>(transport.keySet());
    }

    /**
     * Return a {@link Transport} object specified by type
     * @param type of transport
     * @return {@link Transport} object
     * @throws DeviceModelsException if transport method specified by type doesn't exist
     */
    public Transport getTransport(String type) throws DeviceModelsException {
        Transport transport = this.transport.get(type);
        if (transport == null) throw DeviceModelsException.transportNotFound();
        return transport;
    }

    /**
     * Return a default {@link Transport} object
     * @return {@link Transport} object
     * @throws DeviceModelsException if transport doesn't exist
     */
    public Transport getDefaultTransport() throws DeviceModelsException {
        if (transport.isEmpty()) throw DeviceModelsException.transportNotFound();
        if (transport.size() == 1) return transport.get(getTransportTypes().get(0));
        else return transport.get("cloud");
    }

    @Override public String toString() {
        return "DeviceFirmware{" +
                "links=" + _links +
                ", binaries='" + binaries + '\'' +
                ", transport=" + transport +
                ", repository='" + repository + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", releaseNotes='" + releaseNotes + '\'' +
                ", documentation='" + documentation + '\'' +
                '}';
    }
}

package org.sendim.sdnsim.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Base abstract class for simulated network elements (Switches/Hosts).
 */
public abstract class NetworkElement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String type;
    private List<String> links;

    public NetworkElement(String id, String type) {
        this.id = id;
        this.type = type;
        this.links = new ArrayList<>();
    }

    public void addLink(String neighborId) {
        this.links.add(neighborId);
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public List<String> getLinks() { return links; }

    @Override
    public String toString() {
        return "NetworkElement{id='" + id + "', type='" + type + "', links=" + links + "}";
    }
}

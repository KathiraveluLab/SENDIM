package org.sendim.sdnsim.model;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 * SimulationState: A serializable container for the entire network simulation state.
 */
public class SimulationState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String snapshotId;
    private long timestamp;
    private Map<String, NetworkElement> elements;

    public SimulationState(String snapshotId, Map<String, NetworkElement> elements) {
        this.snapshotId = snapshotId;
        this.timestamp = System.currentTimeMillis();
        this.elements = new HashMap<>(elements);
    }

    public String getSnapshotId() { return snapshotId; }
    public long getTimestamp() { return timestamp; }
    public Map<String, NetworkElement> getElements() { return elements; }

    @Override
    public String toString() {
        return "SimulationState{snapshotId='" + snapshotId + "', timestamp=" + timestamp + ", elements=" + elements.size() + "}";
    }
}

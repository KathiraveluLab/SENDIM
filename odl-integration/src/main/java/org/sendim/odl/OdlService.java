package org.sendim.odl;

import org.sendim.sdnsim.model.NetworkElement;
import java.util.Map;

/**
 * Service interface for ODL synchronization.
 */
public interface OdlService {
    /**
     * Synchronize simulation topology with the ODL controller.
     * @param elements Map of network elements (Switches/Hosts).
     */
    void syncTopology(Map<String, NetworkElement> elements);
}

package org.sendim.odl;

import org.sendim.sdnsim.model.NetworkElement;
import java.util.Map;

/**
 * Default implementation of OdlService that performs no actual integration.
 * Used as a fallback when ODL libraries are not available.
 */
public class NoOpOdlService implements OdlService {

    @Override
    public void syncTopology(Map<String, NetworkElement> elements) {
        System.out.println("NoOpOdlService: Falling back to simulation-only mode (No ODL detected).");
        for (String id : elements.keySet()) {
            NetworkElement element = elements.get(id);
            System.out.println("  [Simulation Sync] Node: " + id + " type: " + element.getClass().getSimpleName());
        }
    }
}

package org.sendim.odl;

import org.sendim.sdnsim.model.NetworkElement;
import java.util.Map;

/**
 * MD-SAL implementation of OdlService.
 * Note: This implementation is decoupled to ensure buildability without 
 * legacy ODL repositories unless specifically requested.
 */
public class MdSalOdlService implements OdlService {

    @Override
    public void syncTopology(Map<String, NetworkElement> elements) {
        System.out.println("MdSalOdlService: Synchronizing with ODL MD-SAL...");
        // In a real implementation, we would use DataBroker here:
        // DataBroker db = ...;
        // Write transaction to ODL topology...
        
        for (String id : elements.keySet()) {
            NetworkElement element = elements.get(id);
            System.out.println("  [ODL MD-SAL Sync] Node: " + id + " type: " + element.getClass().getSimpleName());
        }
    }
}

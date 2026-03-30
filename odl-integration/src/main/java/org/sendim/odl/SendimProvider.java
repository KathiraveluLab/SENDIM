package org.sendim.odl;

import org.sendim.sdnsim.model.NetworkElement;
import java.util.Map;

/**
 * SendimProvider: Logic for synchronizing simulation state with an SDN controller.
 * Note: Actual MD-SAL integration is currently decoupled to ensure buildability 
 * without legacy ODL repositories.
 */
public class SendimProvider {

    private final OdlService odlService;

    public SendimProvider() {
        this.odlService = OdlServiceFactory.getOdlService();
    }

    public void updateTopology(Map<String, NetworkElement> elements) {
        System.out.println("SendimProvider: Forwarding topology synchronization to OdlService...");
        odlService.syncTopology(elements);
    }
}


package org.sendim.core;

import pt.inesc_id.gsd.ravana.core.XSDNEngine;
import pt.inesc_id.gsd.ravana.constants.XSDNConstants;
import org.sendim.sdnsim.model.NetworkElement;
import org.sendim.sdnsim.model.SimulationState;
import org.sendim.sdnsim.parser.DSLParser;

import org.sendim.odl.SendimProvider;
import org.sendim.converters.MininetConverter;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main Orchestrator for SENDIM Simulation.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("SENDIM: Starting Simulation Orchestration...");
        
        String topologyPath = "sdnsim/src/test/resources/sample-topology.xml";
        String mininetOutputPath = "sendim_topology.py";
        
        if (args.length > 0) {
            topologyPath = args[0];
        }

        try {
            // 1. Prepare xSDN environment
            File experimentsDir = new File(XSDNConstants.CONF_FOLDER);
            if (!experimentsDir.exists()) {
                experimentsDir.mkdirs();
            }

            // Copy sample topology to the name expected by xSDN
            String networkFile = XSDNConstants.CONF_FOLDER + File.separator + XSDNConstants.NETWORK_XML;
            System.out.println("Provisioning xSDN topology at: " + networkFile);
            
            Files.copy(Paths.get(topologyPath), Paths.get(networkFile), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            provisionSkeleton(XSDNConstants.CONF_FOLDER + File.separator + "dflows2.xml", "<flows></flows>");
            provisionSkeleton(XSDNConstants.CONF_FOLDER + File.separator + XSDNConstants.POLICY_XML, "<policies></policies>");

            // 2. Initialize xSDN Engine
            System.out.println("Initializing xSDN Engine...");
            XSDNEngine.initializeFlowNetwork();
            System.out.println("xSDN Engine initialized (Simulated).");

            // 3. Parse and Sync (Bridging)
            DSLParser parser = new DSLParser();
            Map<String, NetworkElement> elements = parser.parseSystemDescriptor(topologyPath);
            
            // --- Advanced State Persistency ---
            String topologyId = "default-v1"; // In a real app, this would be the descriptor ID/version
            System.out.println("Checking for existing state in Infinispan...");
            SimulationState savedState = StateRegistry.loadSnapshot(topologyId);
            if (savedState != null) {
                System.out.println("Restored previous simulation state from timestamp: " + savedState.getTimestamp());
                elements = savedState.getElements();
            } else {
                System.out.println("No existing state found. Saving initial snapshot...");
                StateRegistry.saveSnapshot(topologyId, elements);
            }
            // ----------------------------------

            System.out.println("Syncing with Controller Layer...");
            SendimProvider provider = new SendimProvider();
            provider.updateTopology(elements);

            // 4. Conversion for Emulation (Mininet)
            System.out.println("Generating Mininet script at: " + mininetOutputPath);
            MininetConverter converter = new MininetConverter();
            converter.generateMininetScript(elements, mininetOutputPath);


            System.out.println("SENDIM: Simulation Orchestration Complete!");
        } catch (Exception e) {
            System.err.println("Error during orchestration: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void provisionSkeleton(String path, String content) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(content);
            }
        }
    }
}


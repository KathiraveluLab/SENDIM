package org.sendim.core;

import org.infinispan.Cache;
import pt.inesc_id.gsd.ravana.infinispan.InfCore;
import org.sendim.sdnsim.model.SimulationState;
import org.sendim.sdnsim.model.NetworkElement;
import java.util.Map;

/**
 * StateRegistry: Manages SENDIM simulation state using Infinispan.
 */
public class StateRegistry {
    private static Cache<String, Object> cache;

    static {
        try {
            // Reusing xSDN's InfCore but casting to Object for broader state support
            cache = (Cache<String, Object>) (Object) InfCore.getInfiniCore().getDefaultCache();
        } catch (Exception e) {
            System.err.println("Failed to initialize Infinispan StateRegistry: " + e.getMessage());
        }
    }

    public static void saveSnapshot(String id, Map<String, NetworkElement> elements) {
        SimulationState state = new SimulationState(id, elements);
        if (cache != null) {
            try {
                cache.put("sendim.state." + id, state);
                System.out.println("StateRegistry: Saved snapshot [" + id + "] to Infinispan.");
                return;
            } catch (Exception e) {
                System.err.println("StateRegistry: Infinispan save failed, falling back to file: " + e.getMessage());
            }
        }
        saveToFile(id, state);
    }

    public static SimulationState loadSnapshot(String id) {
        if (cache != null) {
            try {
                Object obj = cache.get("sendim.state." + id);
                if (obj instanceof SimulationState) {
                    System.out.println("StateRegistry: Restored snapshot [" + id + "] from Infinispan.");
                    return (SimulationState) obj;
                }
            } catch (Exception e) {
                System.err.println("StateRegistry: Infinispan load failed, falling back to file: " + e.getMessage());
            }
        }
        return loadFromFile(id);
    }

    private static void saveToFile(String id, SimulationState state) {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream("experiments/state_" + id + ".ser"))) {
            oos.writeObject(state);
            System.out.println("StateRegistry: Saved snapshot [" + id + "] to local file.");
        } catch (Exception e) {
            System.err.println("StateRegistry: File save failed: " + e.getMessage());
        }
    }

    private static SimulationState loadFromFile(String id) {
        java.io.File file = new java.io.File("experiments/state_" + id + ".ser");
        if (!file.exists()) return null;
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(file))) {
            System.out.println("StateRegistry: Restored snapshot [" + id + "] from local file.");
            return (SimulationState) ois.readObject();
        } catch (Exception e) {
            System.err.println("StateRegistry: File load failed: " + e.getMessage());
            return null;
        }
    }
}


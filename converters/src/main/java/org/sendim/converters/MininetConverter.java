package org.sendim.converters;

import org.sendim.sdnsim.model.NetworkElement;
import org.sendim.sdnsim.model.Switch;
import org.sendim.sdnsim.model.Host;
import java.io.PrintWriter;
import java.util.Map;
import java.util.List;

/**
 * MininetConverter: Translates SENDIM NetworkElements into a Mininet Python script.
 */
public class MininetConverter {

    public void generateMininetScript(Map<String, NetworkElement> elements, String outputPath) throws Exception {
        try (PrintWriter writer = new PrintWriter(outputPath)) {
            writer.println("#!/usr/bin/python");
            writer.println("from mininet.net import Mininet");
            writer.println("from mininet.node import Controller, RemoteController, OVSKernelSwitch");
            writer.println("from mininet.cli import CLI");
            writer.println("from mininet.log import setLogLevel, info");
            writer.println("");
            writer.println("def sendimNetwork():");
            writer.println("    net = Mininet(controller=RemoteController, switch=OVSKernelSwitch)");
            writer.println("    info('*** Adding controller\\n')");
            writer.println("    net.addController('c0')");
            writer.println("");
            
            writer.println("    info('*** Adding hosts and switches\\n')");
            for (String id : elements.keySet()) {
                NetworkElement element = elements.get(id);
                if (element instanceof Switch) {
                    writer.println("    " + id + " = net.addSwitch('" + id + "')");
                } else if (element instanceof Host) {
                    writer.println("    " + id + " = net.addHost('" + id + "')");
                }
            }
            writer.println("");

            writer.println("    info('*** Creating links\\n')");
            for (String id : elements.keySet()) {
                NetworkElement element = elements.get(id);
                List<String> neighbors = element.getLinks();
                for (String neighborId : neighbors) {

                    // Logic to avoid duplicate links (bi-directional in Mininet by default)
                    if (id.compareTo(neighborId) < 0) {
                        writer.println("    net.addLink(" + id + ", " + neighborId + ")");
                    }
                }
            }
            writer.println("");

            writer.println("    info('*** Starting network\\n')");
            writer.println("    net.build()");
            writer.println("    net.start()");
            writer.println("    CLI(net)");
            writer.println("    net.stop()");
            writer.println("");
            writer.println("if __name__ == '__main__':");
            writer.println("    setLogLevel('info')");
            writer.println("    sendimNetwork()");
        }
    }
}

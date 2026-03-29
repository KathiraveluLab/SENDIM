package org.sendim.sdnsim.parser;

import org.sendim.sdnsim.model.Host;
import org.sendim.sdnsim.model.NetworkElement;
import org.sendim.sdnsim.model.Switch;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses SENDIM XML System Descriptors into NetworkElement objects.
 */
public class DSLParser {
    public Map<String, NetworkElement> parseSystemDescriptor(String filePath) throws Exception {
        Map<String, NetworkElement> elements = new HashMap<>();
        
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("node");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String type = element.getAttribute("type");

                NetworkElement netElement;
                if ("switch".equalsIgnoreCase(type)) {
                    netElement = new Switch(id);
                } else {
                    netElement = new Host(id);
                }

                NodeList linkList = element.getElementsByTagName("link");
                for (int j = 0; j < linkList.getLength(); j++) {
                    Node linkNode = linkList.item(j);
                    if (linkNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element linkElement = (Element) linkNode;
                        netElement.addLink(linkElement.getAttribute("id"));
                    }
                }
                elements.put(id, netElement);
            }
        }
        return elements;
    }
}

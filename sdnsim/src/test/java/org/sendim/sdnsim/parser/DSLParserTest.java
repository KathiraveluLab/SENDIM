package org.sendim.sdnsim.parser;

import org.junit.Test;
import org.sendim.sdnsim.model.NetworkElement;
import java.util.Map;
import static org.junit.Assert.*;

public class DSLParserTest {
    @Test
    public void testParseSystemDescriptor() throws Exception {
        DSLParser parser = new DSLParser();
        String path = "src/test/resources/sample-topology.xml";
        Map<String, NetworkElement> elements = parser.parseSystemDescriptor(path);

        assertNotNull(elements);
        assertEquals(4, elements.size());
        
        NetworkElement s1 = elements.get("s1");
        assertEquals("switch", s1.getType());
        assertTrue(s1.getLinks().contains("s2"));
        assertTrue(s1.getLinks().contains("h1"));

        NetworkElement h1 = elements.get("h1");
        assertEquals("host", h1.getType());
        assertTrue(h1.getLinks().contains("s1"));
    }
}

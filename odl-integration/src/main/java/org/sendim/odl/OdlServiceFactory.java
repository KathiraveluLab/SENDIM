package org.sendim.odl;

/**
 * Factory for creating OdlService instances based on runtime availability.
 */
public class OdlServiceFactory {

    private static final String ODL_DATABROKER_CLASS = "org.opendaylight.controller.md.sal.binding.api.DataBroker";

    public static OdlService getOdlService() {
        try {
            Class.forName(ODL_DATABROKER_CLASS);
            System.out.println("OdlServiceFactory: ODL MD-SAL detected. Loading MD-SAL implementation...");
            return new MdSalOdlService();
        } catch (ClassNotFoundException e) {
            System.out.println("OdlServiceFactory: ODL MD-SAL not detected. Falling back to NoOp implementation.");
            return new NoOpOdlService();
        }
    }
}

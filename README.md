# SENDIM
A Simulation, Emulation, aNd Deployment Integration Middleware for cloud networks.


## Prerequisites

To build and run SENDIM, you need the following dependencies installed:

*   **Java JDK 8 or 21**: The project targets Java 1.8 for compatibility with OpenDaylight Beryllium.
*   **Apache Maven**: Used for multi-module project management and building OSGi bundles.
*   **xSDN Simulator**: The core simulation engine. Clone it from `https://github.com/kathiraveluLab/xSDN` and install it locally using `mvn clean install`.

## Note on OpenDaylight Integration

> [!NOTE]
> OpenDaylight MD-SAL binary integration is currently represented via a modular `SendimProvider` interface due to the sunsetting of legacy Beryllium Nexus repositories. The architectural integration points remain fully documented and ready for deployment in a live ODL environment.

*   **Infinispan 7.0.0.Final**: Required by xSDN for distributed state management.
*   **OpenDaylight Beryllium**: The default SDN controller for MD-SAL integration.

## Build Instructions

1.  **Clone and Install xSDN**:
    ```bash
    git clone https://github.com/kathiraveluLab/xSDN xsdn-core
    cd xsdn-core
    mvn install -DskipTests
    cd ..
    ```

2.  **Build SENDIM**:
    ```bash
    mvn install -DskipTests
    ```


## Citing SENDIM

If you use SENDIM in your research, please cite the following papers:

* Kathiravelu, P. and Veiga, L., 2016, April. **SENDIM for incremental development of cloud networks: Simulation, emulation and deployment integration middleware.** In 2016 IEEE International Conference on Cloud Engineering (IC2E) (pp. 143-146). IEEE.

* Kathiravelu, P. and Veiga, L., 2016, October. **Software-defined simulations for continuous development of cloud and data center networks.** In OTM Confederated International Conferences" On the Move to Meaningful Internet Systems" (pp. 3-23). Cham: Springer International Publishing.
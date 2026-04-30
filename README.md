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

## Usage

### 1. Running the SENDIM Middleware
SENDIM acts as an orchestrator that takes a topology descriptor and synchronizes it across simulation, emulation, and deployment layers.

To run the core orchestration logic:

```bash
mvn exec:java -pl core -Dexec.mainClass="org.sendim.core.Main"
```

By default, it uses the sample topology located at `sdnsim/src/test/resources/sample-topology.xml`. You can specify a custom topology file as an argument:

```bash
mvn exec:java -pl core -Dexec.mainClass="org.sendim.core.Main" -Dexec.args="/path/to/your-topology.xml"
```

### 2. Emulation with Mininet
One of the primary outputs of SENDIM is a Mininet topology script (`sendim_topology.py`). This allows you to transition from simulation to emulation seamlessly.

To run the generated emulation:

```bash
sudo python sendim_topology.py
```

### 3. Simulation State & Persistency
SENDIM uses **Infinispan** for distributed state management. When you run the middleware:
*   The xSDN engine is initialized.
*   The topology is parsed and stored in the `experiments/` directory.
*   Snapshots are saved as `.ser` files (e.g., `experiments/state_default-v1.ser`), allowing for state recovery across runs.

### 4. OpenDaylight Integration
The middleware automatically detects if an OpenDaylight (ODL) environment is present.
*   **Standalone Mode**: If ODL jars are not in the classpath, SENDIM runs in a "No-Op" mode for the controller layer, while still performing simulation and generating emulation scripts.
*   **Integrated Mode**: When deployed as an OSGi bundle within ODL Beryllium, the `SendimProvider` synchronizes the topology with the ODL MD-SAL Inventory.


## Citing SENDIM

If you use SENDIM in your research, please cite the following papers:

* Kathiravelu, P. and Veiga, L., 2016, April. **SENDIM for incremental development of cloud networks: Simulation, emulation and deployment integration middleware.** In 2016 IEEE International Conference on Cloud Engineering (IC2E) (pp. 143-146). IEEE.

* Kathiravelu, P. and Veiga, L., 2016, October. **Software-defined simulations for continuous development of cloud and data center networks.** In OTM Confederated International Conferences" On the Move to Meaningful Internet Systems" (pp. 3-23). Cham: Springer International Publishing.
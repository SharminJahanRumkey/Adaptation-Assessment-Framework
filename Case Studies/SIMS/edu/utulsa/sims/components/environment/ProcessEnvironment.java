package edu.utulsa.sims.components.environment;

import edu.utulsa.components.ComponentEnvironment;
import edu.utulsa.sims.components.state.ProcessState;

/**
 * Simulates the component-local environment for the Measure component. This class is responsible for updating the
 * sensor pressure value.
 */
public class ProcessEnvironment implements ComponentEnvironment<ProcessState> {

    @Override
    public void update(ProcessState state) {
        // TODO: This method should update the pressure value, either randomly or based on a scenario input file.
    }

}

package edu.utulsa.sims.MAPELoop;

import edu.utulsa.components.*;
import edu.utulsa.sims.components.state.ProcessState;

public class ProcessMAPELoop implements ComponentMAPELoop<ProcessState> {

	private Double processThresholdLowerLimit=0.0;
	private Double processThresholdUpperLimit=0.0;
	private ProcessState currentProcess= new ProcessState(20.0,50);
	private int adaptationStatus=0;

	@Override
	public ComponentCore<ProcessState> update(ProcessState state, ComponentCore<ProcessState> componentCore, ComponentMessageQueue componentInQueue) {
		// TODO Auto-generated method stub
		monitor(state);
		analyze();
		plan();
		return execute(componentCore);
	}

	private void monitor(Object state)
	{
		//logger.GenerateLog("Monitor", caseID);
		ProcessState currentProcess=(ProcessState) state;
		processThresholdLowerLimit= currentProcess.getThresholdLowerLimit();
		processThresholdUpperLimit=currentProcess.getThresholdUpperLimit();

	}

	private void analyze()
	{
		if(processThresholdLowerLimit != null && processThresholdUpperLimit!=null)
		{
			adaptationStatus=0;
		}
		else
		{
			adaptationStatus=1;
		}
	}

	private void plan()
	{
		//logger.GenerateLog("Plan", caseID);


	}

	private ComponentCore<ProcessState> execute(ComponentCore<ProcessState> componentCore)
	{
		//logger.GenerateLog("Execute", caseID);

		return componentCore;
	}

}

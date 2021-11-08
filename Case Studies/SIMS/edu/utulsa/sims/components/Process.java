package edu.utulsa.sims.components;

import java.util.Calendar;

import edu.utulsa.components.ComponentCore;
import edu.utulsa.components.ComponentMessage;
import edu.utulsa.components.LogGeneration;
import edu.utulsa.components.LogValidity;
import edu.utulsa.sims.AuditRecord;
import edu.utulsa.sims.components.state.ProcessState;
import edu.utulsa.sims.messages.AuditRecordMessage;
import edu.utulsa.sims.messages.SensorReportMessage;
import edu.utulsa.sims.messages.ThresholdUpdateMessage;

/**
 * Simulated SIMS Process component.
 * Output ports used:
 * <ul>
 *   <li>"measure": For sending threshold updates to the Measure component.</li>
 *   <li>"audit": For sending audit logs to the Audit Trail component.</li>
 * </ul>
 * Input ports used:
 * <ul>
 *   <li>"measurement": For receiving measurement updates from the Measure component.</li>
	
 * </ul>
 */
public class Process extends ComponentCore<ProcessState> {
    /**
     * @param logger The logging utility object.
     * @throws NullPointerException If logger is null.
     */
    private double thresholdLowerLimit = 0.0;
    private double thresholdUpperLimit = 0.0;

    public Process(LogGeneration logger, LogValidity logValidity) {
        super(logger,logValidity);
    }




    @Override
    protected boolean update(ProcessState state) {
        // TODO Auto-generated method stub

        thresholdUpdate(state);
        sendThresholdToMeasure(state);
        try {
            Thread.sleep(25);
        }
        catch (InterruptedException e) {}
        return true;
    }

    @Override
    protected void handleMessage(ComponentMessage message, ProcessState state) {
        // TODO Auto-generated method stub
        if (message.getInPort().equals("measurement") && message instanceof SensorReportMessage) {
            SensorReportMessage theMessage = (SensorReportMessage) message;
            double pressure = theMessage.getPressure();
            int signal = theMessage.getSignal();
            state.setPressure(pressure);
            state.setSignal(signal);
            logger.GenerateLog(String.format("Process: Received measurement. Pressure=%s Signal=%s", pressure, signal));
        }
    }


    private void thresholdUpdate(ProcessState state)
    {
        boolean isThresholdUpdate= state.isThresholdUpdate();

        if(isThresholdUpdate) {
            this.thresholdLowerLimit = state.getThresholdLowerLimit();
            this.thresholdUpperLimit = state.getThresholdUpperLimit();
            logger.GenerateLog(String.format("Process: Threshold updated. Lower Limit=%s Upper limit=%s", this.thresholdLowerLimit, this.thresholdUpperLimit));
            AuditRecord record = generateAuditRecord(state);
            sendAuditRecord(record, state);
        }
    }

    private void sendThresholdToMeasure(ProcessState state) {
        // Send a report to the Process component.
        boolean isThresholdUpdate= state.isThresholdUpdate();
        if(isThresholdUpdate) {
            outputMessage(new ThresholdUpdateMessage("Measure", "Process", state.getThresholdLowerLimit(), state.getThresholdUpperLimit()));
        }
    }

    private AuditRecord generateAuditRecord(ProcessState state) {
        String recordOutcome = String.format("Lower Limit=%s, Upper Limit=%s", thresholdLowerLimit, thresholdUpperLimit);
        AuditRecord result = new AuditRecord("Process Value", Calendar.getInstance().getTimeInMillis(), "Process",
                "Process", ""+recordOutcome, "");
        return result;


    }

    private void sendAuditRecord(AuditRecord record, ProcessState state)
    {
        boolean isThresholdUpdate= state.isThresholdUpdate();
        if(isThresholdUpdate) {
            if (record != null) {
                AuditRecordMessage message = new AuditRecordMessage("audit", "event", record);
                outputMessage(message);
            }
        }

    }

}

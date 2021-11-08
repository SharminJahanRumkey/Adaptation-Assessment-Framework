package edu.utulsa.sims.components;

import java.util.Calendar;

import edu.utulsa.components.ComponentCore;
import edu.utulsa.components.ComponentMessage;
import edu.utulsa.components.LogGeneration;
import edu.utulsa.components.LogValidity;
import edu.utulsa.sims.AuditRecord;
import edu.utulsa.sims.components.state.MeasureState;
import edu.utulsa.sims.messages.AuditRecordMessage;
import edu.utulsa.sims.messages.SensorReportMessage;
import edu.utulsa.sims.messages.ThresholdUpdateMessage;

/**
 * Simulated SIMS Measure component.<br>
 * Output ports used:
 * <ul>
 *   <li>"process": For sending measurements to the Process component.</li>
 *   <li>"audit": For sending audit logs to the Audit Trail component.</li>
 * </ul>
 * Input ports used:
 * <ul>
 *   <li>"threshold": For receiving threshold updates from the Process component.</li>
 * </ul>
 */
public class Measure extends ComponentCore<MeasureState> {
    
    /**
     * @param logger The logging utility object.
     * @throws NullPointerException If logger is null.
     */
    public Measure(LogGeneration logger, LogValidity logValidity) {
        super(logger, logValidity);
    }

    @Override
    protected boolean update(MeasureState state){
        updateSignal(state);
        
        sendStatusToProcess(state);

        // Sleep for a short time so the audit log doesn't fill up too quickly.
        try {
            Thread.sleep(25);
        }
        catch (InterruptedException e) {}
        
        return true;
    }
    
    @Override
    protected void handleMessage(ComponentMessage message, MeasureState state) {
        // Handle threshold updates.

        String inPort = message.getInPort();
        if (inPort.equals("threshold") && message instanceof ThresholdUpdateMessage) {
            ThresholdUpdateMessage theMessage = (ThresholdUpdateMessage) message;
            double thresholdLowerLimit = theMessage.getThresholdLowerLimit();
            double thresholdUpperLimit= theMessage.getThresholdUpperLimit();
            state.setThreshold(thresholdLowerLimit, thresholdUpperLimit);
            logger.GenerateLog(String.format("Measure: Threshold received. Lower limit=%s Upper limit", thresholdLowerLimit,thresholdUpperLimit));
        }
    }
    
    
    private void sendStatusToProcess(MeasureState state) {
        // Send a report to the Process component.
        double pressure = state.getPressure();
        int signal = state.getSignal();
        SensorReportMessage message = new SensorReportMessage("process", "measurement", pressure, signal);
        outputMessage(message);
    }
    
    private void signalUpdateDetected(MeasureState state) {
        double pressure = state.getPressure();
        int signal = state.getSignal();
        AuditRecord record = generateAuditRecord(pressure, signal);
        sendAuditRecord(record);
    }
    
    private AuditRecord generateAuditRecord(double pressure, int signal) {
        long time = Calendar.getInstance().getTimeInMillis();
        String recordOutcome = String.format("P=%s, S=%s", pressure, signal);
        AuditRecord result = new AuditRecord("Measure Update", time, "Measure", "Measure", recordOutcome, "");
        return result;
    }

    private void updateSignal(MeasureState state) {
        doUpdateSignal(state);
        signalUpdateDetected(state);
    }
    
    private void doUpdateSignal(MeasureState state) {
        double pressure = state.getPressure();
        double thresholdLowerLimit = state.getThresholdLowerLimit();
        double thresholdUpperLimit= state.getThresholdUpperLimit();
        int newSignal = 0;
        if(thresholdLowerLimit<= pressure && pressure<=thresholdUpperLimit) {
            newSignal = 1;
        }
        state.setSignal(newSignal);

        logger.GenerateLog(String.format("Measure: Signal updated Pressure=%s Threshold Lower limit=%s Threshold Upper limit Signal=%s", pressure, thresholdLowerLimit,thresholdUpperLimit, newSignal));
    }
    
    private void sendAuditRecord(AuditRecord record) {
        if (record != null) {
            AuditRecordMessage message = new AuditRecordMessage("audit", "event", record);
            outputMessage(message);
        }
    }
}

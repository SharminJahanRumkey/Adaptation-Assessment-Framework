package edu.utulsa.sims.components;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.utulsa.components.ComponentCore;
import edu.utulsa.components.ComponentMessage;
import edu.utulsa.components.LogGeneration;
import edu.utulsa.components.LogValidity;
import edu.utulsa.sims.AuditRecord;
import edu.utulsa.sims.TimeToleranceAlert;
import edu.utulsa.sims.components.state.AuditTrailState;
import edu.utulsa.sims.components.state.ExternalBufferState;
import edu.utulsa.sims.messages.AuditRecordMessage;

/**
 * Simulated SIMS Measure component.<br>
 * Input ports used:
 * <ul>
 *   <li>"event": For receiving records of auditable events.</li>
 * </ul>
 */
public class AuditTrail extends ComponentCore<AuditTrailState> {
    
    /**
     * The amount of time (in milliseconds) that can occur between consecutive audit records without raising a time
     * tolerance alert.
     */
    public static long TIME_TOLERANCE = 10000;
    public static long capacity = 40;
    public static double capRatioLimit = 0.75;
    public boolean capAlert = false;
    private int count = 0;
    private int insertionPoint=-2;
    public int numberOfRecordsToBeStored = 50;
    
    /**
     * @param logger The logging utility object.
     * @throws NullPointerException If logger is null.
     */
    public AuditTrail(LogGeneration logger, LogValidity logValidity) {
        super(logger,logValidity);
        logValidity.GenerateLog(this.count+";"+this.numberOfRecordsToBeStored+" records are attempted to be stored");
    }

    @Override
    protected void handleMessage(ComponentMessage message, AuditTrailState state) {


        while(this.count<this.numberOfRecordsToBeStored) {
            // Handle received records of auditable events.
            String inPort = message.getInPort();
            if (inPort.equals("event") && message instanceof AuditRecordMessage) {

                AuditRecordMessage theMessage = (AuditRecordMessage) message;
                AuditRecord record = theMessage.getRecord();
                if (record != null) {
                    logValidity.GenerateLog(this.count+";record contains the next audit record to be inserted");
                }
                checkCapacity(state);
                if (hasSpace(state)) {
                    String type = record.getType();
                    String outcome = record.getOutcome();
                    logger.GenerateLog(String.format("Audit Trail: Received record. Type=\'%s\' Outcome=\'%s\'", type, outcome));
                    // Store the event record, maintaining time-based ordering.
                    if(hasSpace1(state)) {
                        this.insertionPoint = findInsertionPoint(state, record);
                        storeEventRecord(state, record);
                        logValidity.GenerateLog(this.count+";Maintain safty property...");
                    }
                    this.count++;
                }

          else {
                triggerAdaptation(state,record);

                    this.count++;

                }
            }

        }
        }

    private void storeEventRecord(AuditTrailState state, AuditRecord record) {
        int preSize=0;
        int postSize=0;
        assert (state != null);
        assert (record != null);



        List<AuditRecord> eventLog = state.getEventRecords();
        preSize = eventLog.size();
        eventLog.add(this.insertionPoint, record);
        postSize = eventLog.size();
        if(postSize - preSize == 1)
        {
            logValidity.GenerateLog(this.count+";auditTrail performs audit record storage");
        }
    }
    
    /**
     * Determines the index in the event log at which a new auditable event record should be inserted, to maintain a
     * sorted order by timestamp.
     */
    private int findInsertionPoint(AuditTrailState state, AuditRecord record) {
        assert (state != null);
        assert (record != null);

        List<AuditRecord> eventLog = state.getEventRecords();
        this.insertionPoint = Collections.binarySearch(eventLog, record,
            (a, b) -> (int) Math.signum(a.getTime() - b.getTime()));
        // binarySearch can return a negative index to indicate that no event record with the same timestamp already
        // exists in the event log. We don't need that information here, so convert it to a nonnegative index.
        if (this.insertionPoint < 0) {
            this.insertionPoint = -1 - insertionPoint;

        }
        logValidity.GenerateLog(this.count+";insertionPoint maintains the index at which to insert next record");
        return this.insertionPoint;
    }
    

    private void checkCapacity(AuditTrailState state)
    {
        if(this.capRatioLimit<=1.0 && this.capRatioLimit >=0.0)
        {
            logValidity.GenerateLog(this.count+";capRatioLimit maintains the storage capacity percentage");
            if(this.capacity >=0)
            {
                logValidity.GenerateLog(this.count+";capacity maintains the storage capacity percentage");
                if(state.getEventRecords().size()>= this.capRatioLimit * this.capacity)
                {
                    this.capAlert = true;
                    logValidity.GenerateLog(this.count+";capAlert is true when # of audit records exceeds percentage of capacity");
                }
                else {
                    this.capAlert = false;
                    logValidity.GenerateLog(this.count+";capAlert is true when # of audit records exceeds percentage of capacity");
                }
            }
            else
            {
                logValidity.GenerateLog(this.count+";capacity does not maintain the storage capacity percentage");
            }
        }
        else
        {
            logValidity.GenerateLog(this.count+";capRatioLimit does not maintain the storage capacity percentage");
        }

    }

    private boolean hasSpace(AuditTrailState state)
    {
        boolean hasSpace = true;
        if(this.capRatioLimit<=1.0 && this.capRatioLimit >=0.0)
        {
            hasSpace= true;
            if(this.capacity >=0)
            {
                hasSpace= true;
                if(state.getEventRecords().size()>= this.capRatioLimit * this.capacity)
                {
                    hasSpace = false;
                }
                else {
                    hasSpace = true;
                }
            }
            else
            {
                hasSpace = false;
            }
        }
        else
        {
            hasSpace= false;
        }



        return hasSpace;
    }

    private boolean hasSpace1(AuditTrailState state) {
        if(state.getEventRecords().size()<this.capacity)
            return true;
        else
            return false;
    }

    public void alert()
    {
        logValidity.GenerateLog(this.count+";WARNING!!!");
    }
    public void triggerAdaptation(AuditTrailState state,AuditRecord record)
    {
        boolean triggerAdaptation = false;
        String adaptationPlanID = "";
        String adaptationPlan = "";
        String adaptationValue = "";


        String csvFile = "./Adaptation.txt";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] plans = line.split(cvsSplitBy);
                if(plans.length > 0) {
                    triggerAdaptation = true;
                    adaptationPlanID = plans[0];
                    adaptationPlan = plans[1];
                    adaptationValue = plans[2];
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        alert();
        if(triggerAdaptation)
        {

            if(adaptationPlanID.equals("A1"))
            {
                changeCapRatioLimit(Double.parseDouble(adaptationValue));
                this.capAlert = false;
                String type = record.getType();
                String outcome = record.getOutcome();
                logger.GenerateLog(String.format("Audit Trail: Received record. Type=\'%s\' Outcome=\'%s\'", type, outcome));

                // Store the event record, maintaining time-based ordering.
                if(hasSpace1(state)) {
                    this.insertionPoint = findInsertionPoint(state, record);
                    storeEventRecord(state, record);
                    logValidity.GenerateLog(this.count+";Maintain safty property...");
                }

            }
            if(adaptationPlanID.equals("A2"))
            {
                ExternalBufferState buffer = new ExternalBufferState();
                addBuffer(state,buffer);
                this.capAlert = false;
                String type = record.getType();
                String outcome = record.getOutcome();
                logger.GenerateLog(String.format("Audit Trail: Received record. Type=\'%s\' Outcome=\'%s\'", type, outcome));

                // Store the event record, maintaining time-based ordering.
                if(hasSpace1(state)) {
                    this.insertionPoint = findInsertionPoint(state, record);
                    storeEventRecord(state, record);
                    logValidity.GenerateLog(this.count+";Maintain safty property...");
                }
            }
            if(adaptationPlanID.equals("A3"))
            {

                this.capAlert = false;
                String type = record.getType();
                String outcome = record.getOutcome();
                logger.GenerateLog(String.format("Audit Trail: Received record. Type=\'%s\' Outcome=\'%s\'", type, outcome));

                // Store the event record, maintaining time-based ordering.
                if(hasSpace1(state)) {
                    this.insertionPoint = overWriteRecord(state, record);
                    logValidity.GenerateLog(this.count+";Maintain safty property...");
                }
            }


        }
        else
        {
            String type = record.getType();
            String outcome = record.getOutcome();
            logger.GenerateLog(String.format("Audit Trail: Received record. Type=\'%s\' Outcome=\'%s\'", type, outcome));
            // Store the event record, maintaining time-based ordering.
            if(hasSpace1(state)) {
                this.insertionPoint = findInsertionPoint(state, record);
                storeEventRecord(state, record);
                logValidity.GenerateLog(this.count+";Maintain safty property...");
            }
        }


    }

       private void changeCapRatioLimit(double changeCapRatioLimit)
    {
        this.capRatioLimit = this.capRatioLimit + changeCapRatioLimit;
        if(changeCapRatioLimit>0) {
            logValidity.GenerateLog(this.count+";capRatioLimit increases to maintain the storage capacity percentage");
        }
        else {
            logValidity.GenerateLog(this.count+";capRatioLimit decreases to maintain the storage capacity percentage");
        }
    }

    private void addBuffer(AuditTrailState state, ExternalBufferState buffer)
    {
        while (state.getEventRecords().size()>0)
        {
            buffer.getEventRecords().addAll(state.getEventRecords());
            state.getEventRecords().clear();
        }
        logValidity.GenerateLog(this.count+";Buffer stores older record of auditTrail");
    }

    private int overWriteRecord(AuditTrailState state,AuditRecord record)
    {
        List<AuditRecord> eventLog = state.getEventRecords();
             this.insertionPoint = Collections.binarySearch(eventLog, record,
                     (a, b) -> (int) Math.signum(a.getTime() - b.getTime()));
            this.insertionPoint = eventLog.size()- this.insertionPoint;
            // binarySearch can return a negative index to indicate that no event record with the same timestamp already
            // exists in the event log. We don't need that information here, so convert it to a nonnegative index.
            if (this.insertionPoint < 0) {
                this.insertionPoint = -1 - this.insertionPoint;
        }
        logValidity.GenerateLog(this.count+";insertionPoint maintains the index at which to insert next record");


        state.getEventRecords().remove(this.insertionPoint);
        state.getEventRecords().add(this.insertionPoint,record);
        logValidity.GenerateLog(this.count+";Overwrites older record of auditTrail");
        logValidity.GenerateLog(this.count+";auditTrail performs audit record storage");
            return this.insertionPoint;
    }



}

package edu.utulsa.sims.components.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.utulsa.sims.AuditRecord;
import edu.utulsa.sims.TimeToleranceAlert;

/**
 * The state type for the Audit Trail component.
 */
public class AuditTrailState {
    // The audit trail's event log. In a real system, it might make more sense to store this data in a file instead of
    // in memory. The reason an in-memory list is used instead is to make the program state easier to model in KIV.
    private final List<AuditRecord> eventRecords = new ArrayList<>();
    
    // Set of time tolerance alerts the audit trail has raised.
    private final Set<TimeToleranceAlert> timeToleranceAlerts = new HashSet<>();
    
    /**
     * @return The audit trail's event log. The log can be modified by modifying the returned list.
     */
    public List<AuditRecord> getEventRecords() {
        return eventRecords;
    }
    
    /**
     * @return The set of time tolerance alerts the audit trail has raised. The set can be modified to raise new alerts.
     */
    public Set<TimeToleranceAlert> getTimeToleranceAlerts() {
        return timeToleranceAlerts;
    }
}

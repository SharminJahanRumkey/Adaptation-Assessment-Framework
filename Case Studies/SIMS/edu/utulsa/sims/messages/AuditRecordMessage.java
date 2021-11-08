package edu.utulsa.sims.messages;

import edu.utulsa.components.ComponentMessage;
import edu.utulsa.sims.AuditRecord;

/** Message type for audit records sent to the Audit Trail component. */
public class AuditRecordMessage extends ComponentMessage {
    // The auditable event record to be sent to the audit trail.
    private AuditRecord record;
    
    /**
     * @param outPort - Output port on which the message should be sent. This determines the recipient component.
     * @param inPort - Input port on the recipient side to which the message should be sent.
     * @param record - The auditable event record to be sent to the audit trail.
     * @throws NullPointerException If outPort, inPort, or event is null.
     */
    public AuditRecordMessage(String outPort, String inPort, AuditRecord record) {
        super(outPort, inPort);
        if (record == null) {
            throw new NullPointerException("Null audit record");
        }
        this.record = record;
    }
    
    /**
     * @return The auditable event record to be sent to the audit trail.
     */
    public AuditRecord getRecord() {
        return record;
    }
}

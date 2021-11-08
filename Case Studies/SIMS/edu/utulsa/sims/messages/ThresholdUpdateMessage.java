package edu.utulsa.sims.messages;

import edu.utulsa.components.ComponentMessage;

/** Message type for threshold updates sent to the Measure component. */
public class ThresholdUpdateMessage extends ComponentMessage {
    // The new threshold value.
    private final double thresholdLowerLimit;
    private final double thresholdUpperLimit;

    /**
     * @param thresholdLowerLimit The new Lower limit of threshold value.
     * @param thresholdUpperLimit The new Upper limit of threshold value.
     */
    public ThresholdUpdateMessage(String outPort, String inPort, double thresholdLowerLimit, double thresholdUpperLimit) {
        super(outPort, inPort);
        this.thresholdLowerLimit = thresholdLowerLimit;
        this.thresholdUpperLimit = thresholdUpperLimit;
    }

    /**
     * @return The new threshold value.
     */
    public double getThresholdLowerLimit()
    {
        return thresholdLowerLimit;
    }

    public double getThresholdUpperLimit()
    {
        return thresholdUpperLimit;
    }
}

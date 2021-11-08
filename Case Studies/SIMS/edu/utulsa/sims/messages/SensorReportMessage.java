package edu.utulsa.sims.messages;

import edu.utulsa.components.ComponentMessage;

/** Message type for sensor reports sent to the Process component. */
public class SensorReportMessage extends ComponentMessage {

    // The reported pressure.
    private final double pressure;


    // The reported signal.
    private final int signal;

    /**
     * @param outPort - Output port on which the message should be sent. This determines the recipient component.
     * @param inPort - Input port on the recipient side to which the message should be sent.
     * @param pressure - The reported pressure.
     * @param signal - The reported signal.
     * @throws NullPointerException If outPort or inPort is null.
     */
    public SensorReportMessage(String outPort, String inPort, double pressure, int signal) {
        super(outPort, inPort);
        this.pressure = pressure;
        this.signal = signal;
    }

    /**
     * @return The reported pressure.
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @return The reported threshold.
     */
    public int getSignal() {
        return signal;
    }
}

package edu.utulsa.sims.components.state;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * The state type for the Measure component.
 */
public class MeasureState {
    private double pressure = 0;
    private double thresholdLowerLimit = 0;
    private double thresholdUpperLimit = 0;
    private int signal = 0;

    public MeasureState(double pressure, double thresholdLowerLimit,double thresholdUpperLimit, int signal) {
        this.pressure = pressure;
        this.thresholdLowerLimit = thresholdLowerLimit;
        this.thresholdUpperLimit=thresholdUpperLimit;
        this.signal = signal;
    }

    public void setPressure(double pressure)
    {
        this.pressure=pressure;
    }

    public void setThreshold(double thresholdLowerLimit, double thresholdUpperLimit) {
        this.thresholdLowerLimit = thresholdLowerLimit;
        this.thresholdUpperLimit = thresholdUpperLimit;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public double getPressure() {
        return pressure;
    }

    public double getThresholdLowerLimit() {
        return thresholdLowerLimit;
    }
    public double getThresholdUpperLimit() {
        return thresholdUpperLimit;
    }

    public int getSignal() {
        return signal;
    }
}
package edu.utulsa.sims.components.state;
import java.lang.Math;

/**
 * The state type for the Process component.
 */

public class ProcessState {
    // TODO

    private double thresholdLowerLimit = 0;
    private double thresholdUpperLimit = 0;
    private double pressure=0;
    private double prevPressure=0;
    private Integer signal=1;
    private Integer prevSignal=1;
    private int pressureChangeCount=1;
    private int InventoryAddCount=1;
    private double avgChangeOfPressure=0.0;
    private double avgChangeOfAddInventory=0.0;
    private boolean isUpdate=false;

    public ProcessState(double thresholdLowerLimit, double thresholdUpperLimit)
    {
        this.thresholdLowerLimit = thresholdLowerLimit;
        this.thresholdUpperLimit = thresholdUpperLimit;
    }

    public double getThresholdLowerLimit() {
        return thresholdLowerLimit;
    }

    public double getThresholdUpperLimit() {
        return thresholdUpperLimit;
    }

    public void setPressure(double pressure)
    {
        this.prevPressure=this.pressure;
        this.pressure=pressure;
    }

    public void setSignal(int signal)
    {
        this.prevSignal=this.signal;
        this.signal=signal;
    }

    public boolean CalculateThresholdLowerLimit()
    {
        double changePressure = 0;
        boolean updated=false;

        if(this.signal!=2) {
            changePressure =  this.prevPressure-this.pressure;

            if (changePressure != 0) {

                if (pressureChangeCount == 1) {
                    this.avgChangeOfPressure = Math.abs(changePressure);
                } else {
                    this.avgChangeOfPressure = Math.abs((this.avgChangeOfPressure * (pressureChangeCount - 1) + changePressure) / pressureChangeCount);
                }
                pressureChangeCount++;

            }


            if (this.prevSignal == 1 && this.signal == 0 && changePressure>0) {
                this.thresholdLowerLimit = this.avgChangeOfPressure;
                updated = true;
            } else {
                updated = false;
            }
        }

        return updated;
    }

    public boolean CalculateThresholdUpperLimit()
    {
        double addInventory = 0;
        boolean updated=false;
        if(this.signal!=2) {
            addInventory = this.pressure -this.prevPressure  ;
            if (this.prevSignal == 0 && this.signal == 1 && addInventory>0) {




                if (addInventory != 0) {

                    if (InventoryAddCount == 1) {
                        this.avgChangeOfAddInventory = Math.abs(addInventory);
                    } else {
                        this.avgChangeOfAddInventory = Math.abs((this.avgChangeOfAddInventory * (InventoryAddCount - 1) + addInventory) / InventoryAddCount);
                    }
                    InventoryAddCount++;
                    this.thresholdUpperLimit = this.avgChangeOfAddInventory+this.pressure;
                    updated = true;

                } else {
                    updated = false;

                }
            } else {
                updated = false;
            }
        }
        return updated;
    }

    public boolean isThresholdUpdate()
    {
        boolean isLowerLimitUpdate=false;
        boolean isUpperLimitUpdate=false;
        this.isUpdate=false;

        isLowerLimitUpdate= CalculateThresholdLowerLimit();
        isUpperLimitUpdate= CalculateThresholdUpperLimit();

        if(isLowerLimitUpdate==true || isUpperLimitUpdate==true)
        {
            this.isUpdate=true;
        }

        return this.isUpdate;
    }

}
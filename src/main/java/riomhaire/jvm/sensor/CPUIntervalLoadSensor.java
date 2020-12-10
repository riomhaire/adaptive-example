package riomhaire.jvm.sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CPUIntervalLoadSensor implements Runnable {
    public static enum WaterMark {NORMAL,WARNING,DANGEROUS}

    protected int intervalPeriod=10; // Default to 10 units
    protected List<Double> measurements = new ArrayList<>();
    protected CPUSensor cpuSensor=new CPUSensor();
    protected double warningWaterMark=-1.0;
    protected double dangerousWaterMark=-1.0;

    public CPUIntervalLoadSensor() {
        
    }

    public CPUIntervalLoadSensor(int intervalPeriod) {
        this.intervalPeriod = intervalPeriod;
    }

    public void watermarks(double warning, double dangerous) {
     if( warning >0.0 && warning <= 100.0 && warning <=dangerous) {
         // if we have some sane values otherwise ignore
         this.warningWaterMark = warning;
         this.dangerousWaterMark=dangerous;
     }
    }

    // Takes a measurement and adds it to the measurements list and pops the older ones
    // if too many values
    protected void measure() {
      synchronized (measurements) {
          measurements.add(0,cpuSensor.getLoad());
          if( measurements.size() > intervalPeriod) {
              measurements.remove(measurements.size()-1);
          }
      }
    }

    // Returns the average load over the interval
    public double value() {
      double value =0.0;
      synchronized (measurements) {
          if( measurements.size() == intervalPeriod) { // only when we have enough values
              double sum = measurements.
                      stream()
                      .collect(Collectors.summingDouble(Double::doubleValue));
              value = sum/measurements.size();
             value = Math.round(value * 100.0) / 100.0; // Round to 2 decimal places
          }
      }
      return value;
    }

    // Returns current watermark value
    public WaterMark watermark() {
        WaterMark waterMark = WaterMark.NORMAL;
        double value = value();

        // See if warning or dangerous
        if( warningWaterMark >= 0.0 && value >= warningWaterMark && value <dangerousWaterMark )
           waterMark=WaterMark.WARNING;
        else if( dangerousWaterMark >= 0.0 && value >= dangerousWaterMark )
            waterMark=WaterMark.DANGEROUS;

        return waterMark;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
       measure();
    }
}

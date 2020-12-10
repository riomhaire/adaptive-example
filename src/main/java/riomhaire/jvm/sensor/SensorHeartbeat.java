package riomhaire.jvm.sensor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorHeartbeat {
    protected  int period = 10;
    protected  TimeUnit timeUnit = TimeUnit.SECONDS; // Default to seconds
    protected  boolean active=false;
    protected  Runnable heartbeatTask;
    protected ScheduledExecutorService executor;


    public SensorHeartbeat(int period, TimeUnit timeUnit, Runnable heartbeatTask) {
      this.period=period;
      this.timeUnit=timeUnit;
      this.heartbeatTask = heartbeatTask;
    }

    public void start( ) {
      // sanity check
      if( this.active) return;  // already running
      this.active=true;
      executor = Executors.newSingleThreadScheduledExecutor();
      executor.scheduleAtFixedRate( heartbeatTask,0, period, timeUnit);
    }

    public void stop() {
        if( this.active == false) return;
        executor.shutdown();
        active = false;
    }
}

package riomhaire.jvm.sensor.app;

import java.util.concurrent.atomic.AtomicInteger;

// Counts things
public class Tallyman {

    protected String[] labels;
    protected AtomicInteger[] tallies;

    public Tallyman(String[] labels) {
        this.labels = labels;
        tallies = new AtomicInteger[labels.length];
         for( int i=0;i<labels.length;i++){
             tallies[i]=new AtomicInteger();
         }
    }
    public void clear() {
        for( int i=0;i<labels.length;i++){
            tallies[i].set(0);
        }
    }

    public void incrementTally( int i ) {
      if( i>=0 && i<labels.length) {
          tallies[i].incrementAndGet();
      }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for( int i=0; i<labels.length;i++) {
            if( s.length() >0 ) s.append(',');
            s.append(labels[i]);
            s.append(',');
            s.append(tallies[i].get());
        }
        return s.toString();
    }
}

package riomhaire.jvm.sensor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import riomhaire.jvm.sensor.CPUIntervalLoadSensor;
import static riomhaire.jvm.sensor.CPUIntervalLoadSensor.WaterMark;

@Component
public class GreetingHandler {
    @Autowired
    CPUIntervalLoadSensor cpuIntervalLoadSensor;

    @Autowired
    Tallyman tallyman;

    public Mono<ServerResponse> helloBasic(ServerRequest request) {
        tallyWatermarkLevel(cpuIntervalLoadSensor.watermark());

        // step 1 - log info something
        tallyman.incrementTally(0);
        System.out.println("INFO. /hello called");

        // step 2 - important operation for 200ms
        tallyman.incrementTally(1);
        dosomething(200);

        // step 3 - log debug something
        tallyman.incrementTally(2);
        System.out.println("DEBUG. calling event service");

        // step 4 - unimportant operation for 50ms
        tallyman.incrementTally(3);
        dosomething(50);

        // step 5 - return
        tallyman.incrementTally(4);
        System.out.println("INFO. /hello done");
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(tallyman.toString()));
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        // step 1 - log info something
        CPUIntervalLoadSensor.WaterMark cpuLevel = cpuIntervalLoadSensor.watermark();
        tallyWatermarkLevel(cpuLevel);

        if (cpuLevel != WaterMark.DANGEROUS) {
            tallyman.incrementTally(0);
            System.out.println("INFO. /hello called");
        }
        // step 2 - important operation for 200ms
        tallyman.incrementTally(1);
        dosomething(200);

        // step 3 - log debug something
        if (cpuLevel != WaterMark.DANGEROUS) {
            if (cpuLevel == WaterMark.NORMAL) {
                tallyman.incrementTally(2);
                System.out.println("DEBUG. calling event service");
            }
            // step 4 - unimportant operation for 50ms
            tallyman.incrementTally(3);
            dosomething(50);
            // step 5 - return
        }
        if (cpuLevel == WaterMark.NORMAL) {
            tallyman.incrementTally(4);
            System.out.println("INFO. /hello done");
        }
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(tallyman.toString()));
    }

    private void dosomething(int i) {
        // A busy wait - yep I know this code stinks
        long end = System.currentTimeMillis()+i;
        while( System.currentTimeMillis() < end);
    }

    private void tallyWatermarkLevel(CPUIntervalLoadSensor.WaterMark cpuLevel) {
      switch (cpuLevel) {
          case NORMAL: tallyman.incrementTally(5);
              break;
          case WARNING: tallyman.incrementTally(6);
              break;
          case DANGEROUS: tallyman.incrementTally(7);
                break;
      }
    }

}
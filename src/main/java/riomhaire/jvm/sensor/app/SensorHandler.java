package riomhaire.jvm.sensor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import riomhaire.jvm.sensor.CPUIntervalLoadSensor;

@Component
public class SensorHandler {
    @Autowired
    CPUIntervalLoadSensor cpuIntervalLoadSensor;

    @Autowired
    Tallyman tallyman;

    @Scheduled(fixedDelay = 1000)
    public void displayCPU()  {
        System.out.println("CPU Load [ "+ cpuIntervalLoadSensor.value() +" ] %, WaterMark Level [ "+cpuIntervalLoadSensor.watermark()+" ]  "+tallyman.toString());
    }

    public Mono<ServerResponse> status(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(String.format("%.0f,%s",cpuIntervalLoadSensor.value(),cpuIntervalLoadSensor.watermark())));
    }

    public Mono<ServerResponse> clear(ServerRequest request) {
        String last = tallyman.toString();
        tallyman.clear();
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(last));
    }

}

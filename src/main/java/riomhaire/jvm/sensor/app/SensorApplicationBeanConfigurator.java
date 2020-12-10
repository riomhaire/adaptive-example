package riomhaire.jvm.sensor.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import riomhaire.jvm.sensor.CPUIntervalLoadSensor;
import riomhaire.jvm.sensor.SensorHeartbeat;

import java.util.concurrent.TimeUnit;

@Configuration
public class SensorApplicationBeanConfigurator {
    protected CPUIntervalLoadSensor cpuIntervalLoadSensor = new CPUIntervalLoadSensor(10);// Were interested in over 10 seconds
    protected SensorHeartbeat cpuSensorHeartbeat;

    @Bean
    public RouterFunction<ServerResponse> helloBasicRoute(GreetingHandler greetingHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/helloBasic").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::helloBasic);
    }

    @Bean
    public RouterFunction<ServerResponse> helloRoute(GreetingHandler greetingHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello);
    }

    @Bean
    public RouterFunction<ServerResponse> sensorRoute(SensorHandler sensorHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/sensor").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), sensorHandler::status);
    }

    @Bean
    public RouterFunction<ServerResponse> sensorClearRoute(SensorHandler sensorHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/sensor/clear").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), sensorHandler::clear);
    }

    @Bean
    public CPUIntervalLoadSensor cpuIntervalLoadSensor() {
        SensorHeartbeat cpuSensorHeartbeat;
        // Set watermarks
        cpuIntervalLoadSensor.watermarks(50.0,80.0);
        // start sensor
        cpuSensorHeartbeat = new SensorHeartbeat(1, TimeUnit.SECONDS,cpuIntervalLoadSensor);
        cpuSensorHeartbeat.start();

        return cpuIntervalLoadSensor;
    }

    @Bean
    public Tallyman tallyman() {
        String[] labels = {"entry","work","debug","nth-work","exit","normal","warning","dangerous"};
        return new Tallyman(labels);
    }
}

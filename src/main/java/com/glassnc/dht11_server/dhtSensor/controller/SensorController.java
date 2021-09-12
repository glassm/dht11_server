package com.glassnc.dht11_server.dhtSensor.controller;

import com.glassnc.dht11_server.dhtSensor.control.DHT11;
import com.glassnc.dht11_server.dhtSensor.control.DHT11Result;
import com.glassnc.dht11_server.dhtSensor.dto.DhtSensorDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("sensors")
public class SensorController {

    @GetMapping("dht11")
    public DhtSensorDto getSensorData() throws InterruptedException {
        int counter = 100;
        boolean completed = false;

        DhtSensorDto dto = new DhtSensorDto();
        DHT11 dht11 = new DHT11(0);

        //The DHT11 often throws CRC errors. Cycle through invocations until a response is received
        while(counter > 0 && !completed) {
            DHT11Result result = dht11.read();

            System.out.println("dht 11 read result: " + result);

            if (result.isValid()) {
                dto.setTimestamp(LocalDateTime.now());
                dto.setRelativeHumidity(Double.valueOf(result.getHumidity()).intValue());
                dto.setTempC(Double.valueOf(result.getTemperature()).intValue());
                dto.setTempF(Double.valueOf(result.getTemperature()).intValue() * 9/5 + 32);
                completed = true;
            }

            TimeUnit.SECONDS.sleep(2);
            counter--;

        }

        return dto;
    }

}

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
                double relativeHumidity = result.getHumidity();
                double tempC = result.getTemperature();
                double tempF = tempC * 9/5 + 32;
                double dewpointC = (tempC - (14.55 + 0.114 * tempC) * (1 - (0.01 * relativeHumidity)) - Math.pow(((2.5 + 0.007 * tempC) * (1 - (0.01 * relativeHumidity))),3) - (15.9 + 0.117 * tempC) * Math.pow((1 - (0.01 * relativeHumidity)), 14));
                double dewpointF = dewpointC * 9/5 + 32;
                dto.setTimestamp(LocalDateTime.now());
                dto.setRelativeHumidity(Double.valueOf(relativeHumidity).intValue());
                dto.setTempC(Double.valueOf(tempC).intValue());
                dto.setTempF(Double.valueOf(tempF).intValue());
                dto.setDewpoint(Double.valueOf(dewpointF).intValue());
                completed = true;
            }

            TimeUnit.SECONDS.sleep(2);
            counter--;

        }

        return dto;
    }

}

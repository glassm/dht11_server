package com.glassnc.dht11_server.dhtSensor.controller;

import com.glassnc.dht11_server.dhtSensor.control.DHT11;
import com.glassnc.dht11_server.dhtSensor.control.DHT11Result;
import com.glassnc.dht11_server.dhtSensor.dto.DhtSensorDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("sensors")
public class SensorController {

//
//    @GetMapping("/dht11")
//    public DhtSensorDto getDhtData() {
//        DhtSensorDto dto = new DhtSensorDto();
//        StringBuilder sb = new StringBuilder();
//
//        try {
//            Process p = Runtime.getRuntime().exec(myApp);
//            System.out.println("HERE");
//            try(InputStream reader = p.getInputStream()) {
//                int data = reader.read();
//                while(data != -1) {
//                    sb.append((char) data);
//                    data = reader.read();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //TODO Real data here
//        dto.setRelativeHumidity(357);
//        dto.setTempC(21);
//        dto.setTestNonsense(sb.toString());
//        return dto;
//    }

    @GetMapping("dht11")
    public DhtSensorDto getSensorData() throws InterruptedException {
        int counter = 100;
        boolean completed = false;

        System.out.println("version ===>6<===");
        DhtSensorDto dto = new DhtSensorDto();
        DHT11 dht11 = new DHT11(0);

        while(counter > 0 && !completed) {
            DHT11Result result = dht11.read();

            System.out.println("dht 11 read result: " + result);

            if (result.isValid()) {
                dto.setTimestamp(LocalDateTime.now());
                dto.setRelativeHumidity(Double.valueOf(result.getHumidity()).intValue());
                dto.setTempC(Double.valueOf(result.getTemperature()).intValue());
                dto.setTempF(Double.valueOf(result.getTemperature()).intValue() * 9/5 + 32);
                completed = true;
//            System.out.println("Last valid input: " + new Date());
//            System.out.printf("Temperature: %.1f C\n" , result.getTemperature());
//            System.out.printf("Humidity:    %.1f %%\n", result.getHumidity());
            }

            TimeUnit.SECONDS.sleep(2);
            counter--;

        }

        return dto;
    }

}

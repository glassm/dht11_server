package com.glassnc.dht11_server.dhtSensor.control;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DHT11Demo {
    public static void main(String[] args) throws InterruptedException {
        //Pi4J implements the same pin number scheme as the Wiring Pi project.
        //https://pi4j.com/1.2/usage.html
        DHT11 dht11 = new DHT11(0); // use GPIO pin 17 according to wiringplan

        while (true) {
            DHT11Result result = dht11.read();

            if (result.isValid()) {
                System.out.println("Last valid input: " + new Date());
                System.out.printf("Temperature: %.1f C\n" , result.getTemperature());
                System.out.printf("Humidity:    %.1f %%\n", result.getHumidity());
            }

            TimeUnit.SECONDS.sleep(2);
        }
    }
}

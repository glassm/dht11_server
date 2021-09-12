package com.glassnc.dht11_server.dhtSensor.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DhtSensorDto {
    private int tempC;
    private int tempF;
    private int relativeHumidity;
    private LocalDateTime timestamp;

    public int getTempC() {
        return tempC;
    }

    public void setTempC(int tempC) {
        this.tempC = tempC;
    }

    public int getTempF() {
        return tempF;
    }

    public void setTempF(int tempF) {
        this.tempF = tempF;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DhtSensorDto{" +
                "tempC=" + tempC +
                ", tempF=" + tempF +
                ", relativeHumidity=" + relativeHumidity +
                ", timestamp=" + timestamp +
                '}';
    }
}

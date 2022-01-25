package at.fhburgenland.models;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public class EntryDTO {
    private LocalDateTime timestamp;
    private Double temperature;
    private Double humidity;

    public EntryDTO() {}
    public EntryDTO(LocalDateTime time, Double temperature, Double humidity) {
        this.timestamp = time;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }


}

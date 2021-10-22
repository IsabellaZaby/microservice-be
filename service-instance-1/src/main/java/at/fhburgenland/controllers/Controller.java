package at.fhburgenland.controllers;

import at.fhburgenland.models.Sensor;
import at.fhburgenland.services.DBService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/readAll")
    public List<Sensor> readAll() {
        DBService dbService = DBService.getInstance();
        return dbService.readAll();
    }

    @RequestMapping("/readSensor/{sensorId}")
    public String readSensor(@PathVariable String sensorId) {
        DBService dbService = DBService.getInstance();
        return dbService.readSensorEntries(sensorId).toString();
    }

    @PutMapping("/update")
    public String updateSensor(@RequestParam(value = "id") String id,
                               @RequestParam(value = "temperature") String temperature,
                               @RequestParam(value = "humidity") String humidity,
                               @RequestParam(value = "timestamp") String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);
        DBService dbService = DBService.getInstance();
        dbService.updateSensor(Integer.valueOf(id), dateTime, Double.parseDouble(temperature), Double.parseDouble(humidity));
        return "Update successfully!";
    }

    @DeleteMapping("/delete")
    public List<Sensor> deleteSensor(@RequestBody Map<String, Integer> body) {
        DBService dbService = DBService.getInstance();
        Integer id = body.get("id");
        Sensor sensor = dbService.readSensor(id);
        dbService.deleteSensor(sensor);
        return dbService.readAll();
    }

    @PostMapping("/add")
    public String addSensor(@RequestBody Map<String, String> body) {
        LocalDateTime dateTime = LocalDateTime.parse(body.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME);
        DBService dbService = DBService.getInstance();
        Sensor sensor = new Sensor();
        sensor.setSensor_id(body.get("sensorId"));
        sensor.setTimestamp(dateTime);
        sensor.setTemperature(Double.parseDouble(body.get("temperature")));
        sensor.setHumidity(Double.parseDouble(body.get("humidity")));
        dbService.addSensor(sensor);
        return "Sensor added";
    }
}
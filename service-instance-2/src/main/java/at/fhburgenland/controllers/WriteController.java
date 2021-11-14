package at.fhburgenland.controllers;

import at.fhburgenland.models.DBError;
import at.fhburgenland.models.Sensor;
import at.fhburgenland.services.DBService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
public class WriteController {

    @PutMapping("/update")
    public String updateSensor(@RequestBody Map<String, String> body) throws DBError {
        LocalDateTime dateTime = LocalDateTime.parse(body.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME);
        DBService dbService = DBService.getInstance();
        try {
            dbService.updateSensor(Integer.valueOf(body.get("id")), dateTime, Double.parseDouble(body.get("temperature")),
                Double.parseDouble(body.get("humidity")));
        } catch (NumberFormatException e) {
            throw new DBError("Not a number!");
        }
        return "Update successfully!";
    }

    @DeleteMapping("/delete")
    public List<Sensor> deleteSensor(@RequestBody Map<String, Integer> body) throws DBError {
        DBService dbService = DBService.getInstance();
        Integer id = body.get("id");
        dbService.deleteSensor(id);
        return dbService.readAll();
    }

    @PostMapping("/add")
    public String addSensor(@RequestBody Map<String, String> body) throws DBError {
        LocalDateTime dateTime = LocalDateTime.parse(body.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME);
        DBService dbService = DBService.getInstance();
        Sensor sensor = new Sensor();
        sensor.setSensor_id(body.get("sensorId"));
        sensor.setTimestamp(dateTime);
        try {
            sensor.setTemperature(Double.parseDouble(body.get("temperature")));
            sensor.setHumidity(Double.parseDouble(body.get("humidity")));
            dbService.addSensor(sensor);
        } catch (NumberFormatException e) {
            throw new DBError("Not a number!");
        }
        return "Sensor added";
    }
}

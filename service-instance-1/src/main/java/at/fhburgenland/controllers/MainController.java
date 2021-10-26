package at.fhburgenland.controllers;

import at.fhburgenland.models.Sensor;
import at.fhburgenland.services.DBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/readAll")
    public List<Sensor> readAll() {
        DBService dbService = DBService.getInstance();
        return dbService.readAll();
    }

    @GetMapping(value = "/readSensor")
    public Sensor readSensor(@RequestParam("id") String id) {
        DBService dbService = DBService.getInstance();
        return dbService.readSensor(id);
    }
}
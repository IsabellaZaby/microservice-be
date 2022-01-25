package at.fhburgenland.controllers;

import at.fhburgenland.facades.DataFacade;
import at.fhburgenland.models.ChartDTO;
import at.fhburgenland.models.Sensor;
import at.fhburgenland.services.DBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReadController {

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

    @GetMapping(value = "/readChartData")
    public List<ChartDTO> readChartData() {
        DataFacade dataFacade = new DataFacade();
        return dataFacade.readChartData();
    }

    @GetMapping(value = "/readMobileChartData")
    public List<ChartDTO> readMobileChartData(@RequestParam("type") String type) {
        DataFacade dataFacade = new DataFacade();
        return dataFacade.readMobileChartData(type);
    }
}

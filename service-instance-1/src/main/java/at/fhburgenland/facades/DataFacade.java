package at.fhburgenland.facades;

import at.fhburgenland.models.ChartDTO;
import at.fhburgenland.models.EntryDTO;
import at.fhburgenland.models.Sensor;
import at.fhburgenland.services.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataFacade {

    public List<ChartDTO> readChartData() {
        DBService dbService = DBService.getInstance();
        List<String> sensorIds = dbService.readAllDistinct();

        List<ChartDTO> chartDTOList = new ArrayList<>();
        if (!sensorIds.isEmpty()) {
            chartDTOList = sensorIds.stream().map(id -> {
                ChartDTO chartDTO = new ChartDTO();
                chartDTO.setSensor_id(id);
                List<Sensor> sensorEntries = dbService.readSensorEntries(id);
                List<EntryDTO> entryDTOList = sensorEntries.stream().map(sensor -> {
                    EntryDTO entryDTO = new EntryDTO();
                    entryDTO.setTemperature(sensor.getTemperature());
                    entryDTO.setHumidity(sensor.getHumidity());
                    entryDTO.setTimestamp(sensor.getTimestamp());
                    return entryDTO;
                }).collect(Collectors.toList());
                chartDTO.setEntries(entryDTOList);
                return chartDTO;
            }).collect(Collectors.toList());
        }
        return chartDTOList;
    }
}

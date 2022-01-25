package at.fhburgenland.facades;

import at.fhburgenland.models.ChartDTO;
import at.fhburgenland.models.EntryDTO;
import at.fhburgenland.models.Sensor;
import at.fhburgenland.services.DBService;
import com.thoughtworks.xstream.converters.time.LocalDateTimeConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    public List<ChartDTO> readMobileChartData(String type) {
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
                List<EntryDTO> result = null;
                LocalDateTime today = LocalDateTime.now();
                switch (type) {
                    case "24h":
                        LocalDateTime min = today.minusDays(1);
                        List<EntryDTO> filteredDate = entryDTOList.stream().filter(entryDTO ->
                            entryDTO.getTimestamp().isAfter(min) && entryDTO.getTimestamp().isBefore(today)).collect(Collectors.toList());
                        List<EntryDTO> test = new ArrayList<>();
                        for (int i = 1; i < 25; i++) {
                            List<EntryDTO> test2 = new ArrayList<>();
                            for (EntryDTO entryDTO : filteredDate) {
                                if (entryDTO.getTimestamp().getHour() == i) {
                                    test2.add(entryDTO);
                                }
                            }
                            test2.stream().reduce((entryA, entryB) -> {
                                Double temperature = (entryA.getTemperature() + entryB.getTemperature()) / 2;
                                Double humidity = (entryA.getHumidity() + entryB.getHumidity()) / 2;
                                return new EntryDTO(entryA.getTimestamp(), temperature, humidity);
                            }).ifPresent(test::add);
                        }
                        result = test;
                        break;
                    case "7d":
                        LocalDateTime week = today.minusDays(7);
                        List<EntryDTO> filteredWeek = entryDTOList.stream().filter(entryDTO ->
                            entryDTO.getTimestamp().isAfter(week) && entryDTO.getTimestamp().isBefore(today)).collect(Collectors.toList());
                        List<EntryDTO> weekList = new ArrayList<>();
                        for (DayOfWeek day : DayOfWeek.values()) {
                            List<EntryDTO> test2 = new ArrayList<>();
                            for (EntryDTO entryDTO : filteredWeek) {
                                if (entryDTO.getTimestamp().getDayOfWeek() == day) {
                                    test2.add(entryDTO);
                                }
                            }
                            test2.stream().reduce((entryA, entryB) -> {
                                Double temperature = (entryA.getTemperature() + entryB.getTemperature()) / 2;
                                Double humidity = (entryA.getHumidity() + entryB.getHumidity()) / 2;
                                return new EntryDTO(entryA.getTimestamp(), temperature, humidity);
                            }).ifPresent(weekList::add);
                        }
                        result = weekList;
                        break;
                    case "30d":
                        LocalDateTime month = today.minusDays(31);
                        List<EntryDTO> filteredMonth = entryDTOList.stream().filter(entryDTO ->
                            entryDTO.getTimestamp().isAfter(month) && entryDTO.getTimestamp().isBefore(today)).collect(Collectors.toList());
                        List<EntryDTO> monthList = new ArrayList<>();
                        for (int i = 1; i < 32; i++) {
                            List<EntryDTO> test2 = new ArrayList<>();
                            for (EntryDTO entryDTO : filteredMonth) {
                                if (entryDTO.getTimestamp().getDayOfMonth() == i) {
                                    test2.add(entryDTO);
                                }
                            }
                            test2.stream().reduce((entryA, entryB) -> {
                                Double temperature = (entryA.getTemperature() + entryB.getTemperature()) / 2;
                                Double humidity = (entryA.getHumidity() + entryB.getHumidity()) / 2;
                                return new EntryDTO(entryA.getTimestamp(), temperature, humidity);
                            }).ifPresent(monthList::add);
                        }
                        result = monthList;
                        break;
                    default:
                        result = new ArrayList<>();
                }
                chartDTO.setEntries(result);
                return chartDTO;
            }).collect(Collectors.toList());
        }
        return chartDTOList;
    }
}

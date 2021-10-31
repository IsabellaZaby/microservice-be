package at.fhburgenland.models;

import java.util.List;
import java.util.Objects;

public class ChartDTO {

    private String sensor_id;
    private List<EntryDTO> entries;

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public List<EntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryDTO> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChartDTO chartDTO = (ChartDTO) o;
        return sensor_id.equals(chartDTO.sensor_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor_id);
    }

}

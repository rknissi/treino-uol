package uol.location.location.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private Long id;

    @JsonProperty("min_temp")
    private Long minTemp;

    @JsonProperty("max_temp")
    private Long maxTemp;

    public Long getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Long minTemp) {
        this.minTemp = minTemp;
    }

    public Long getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Long maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

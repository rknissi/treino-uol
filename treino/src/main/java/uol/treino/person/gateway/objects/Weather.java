package uol.treino.person.gateway.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

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

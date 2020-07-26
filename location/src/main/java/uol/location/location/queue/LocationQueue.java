package uol.location.location.queue;

import uol.location.location.repository.objects.LocationRepositoryEntity;

import java.io.Serializable;

public class LocationQueue implements Serializable {

    private String ip;
    private Long id;

    public LocationQueue(){
    }

    public LocationQueue(String ip, Long id) {
        this.ip = ip;
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

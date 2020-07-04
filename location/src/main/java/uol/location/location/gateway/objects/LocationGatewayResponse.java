package uol.location.location.gateway.objects;

import uol.location.location.objects.Location;

public class LocationGatewayResponse {
    private String status;
    private Location data;

    public Location getData() {
        return data;
    }

    public void setData(Location data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

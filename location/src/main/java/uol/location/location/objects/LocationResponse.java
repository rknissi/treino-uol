package uol.location.location.objects;

public class LocationResponse {
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

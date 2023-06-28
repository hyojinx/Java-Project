package projectzzz;

import java.util.HashMap;
import java.util.Map;

public class Bus {
    private String route; 
    private String[] times;
    private Map<String, String> seats; 

    public static Bus[] buses = {
            new Bus("서울-광주", new String[]{"10:00", "14:00", "19:00"}),
            new Bus("서울-목포", new String[]{"11:00", "15:00", "20:00"}),
            new Bus("서울-부산", new String[]{"09:00", "13:00", "21:00"})
    };
    
    public Bus(String route, String[] times) {
        this.route = route;
        this.times = times;
        this.seats = new HashMap<>();
    }

    public String getRoute() {
        return route;
    }

    public String[] getTimes() {
        return times;
    }

    public Map<String, String> getSeats() {
        return seats;
    }
}

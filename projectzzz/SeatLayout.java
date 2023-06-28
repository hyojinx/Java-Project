package projectzzz;

import java.util.Map;

public class SeatLayout {
    private Map<String, String> seats;

    public SeatLayout(Map<String, String> seats) {
        this.seats = seats;
    }

    public void printSeatStatus() {
        System.out.println("\n======= 버스 좌석 현황 =======");
        System.out.println("\t" + "1A" + seats.get("1A") + "\t" + "1B" + seats.get("1B") + "\t  \t \t" + "1C" + seats.get("1C") + "\t" + "1D" + seats.get("1D"));
        System.out.println("\t" + "2A" + seats.get("2A") + "\t" + "2B" + seats.get("2B") + "\t  \t \t" + "2C" + seats.get("2C") + "\t" + "2D" + seats.get("2D"));
        System.out.println("\t" + "3A" + seats.get("3A") + "\t" + "3B" + seats.get("3B") + "\t  \t \t" + "3C" + seats.get("3C") + "\t" + "3D" + seats.get("3D"));
        System.out.println("\t" + "4A" + seats.get("4A") + "\t" + "4B" + seats.get("4B") + "\t  \t \t" + "4C" + seats.get("4C") + "\t" + "4D" + seats.get("4D"));
        System.out.println("\t" + "5A" + seats.get("5A") + "\t" + "5B" + seats.get("5B") + "\t  \t \t" + "5C" + seats.get("5C") + "\t" + "5D" + seats.get("5D"));
        System.out.println("\t" + "6A" + seats.get("6A") + "\t" + "6B" + seats.get("6B") + "\t  \t \t" + "6C" + seats.get("6C") + "\t" + "6D" + seats.get("6D"));
        System.out.println("\t" + "7A" + seats.get("7A") + "\t" + "7B" + seats.get("7B") + "\t  \t \t" + "7C" + seats.get("7C") + "\t" + "7D" + seats.get("7D"));
        System.out.println("\t" + "8A" + seats.get("8A") + "\t" + "8B" + seats.get("8B") + "\t  \t \t" + "8C" + seats.get("8C") + "\t" + "8D" + seats.get("8D"));
        System.out.println("\t" + "9A" + seats.get("9A") + "\t" + "9B" + seats.get("9B") + "\t  \t \t" + "9C" + seats.get("9C") + "\t" + "9D" + seats.get("9D"));
       }
}

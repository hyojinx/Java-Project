package projectzzz;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class BusReservation {
    public static Map<String, String> seats = new HashMap<>();
    public static void main(String[] args) {
        
        System.out.println("◈◈◈◈◈ 고 속 버 스   좌 석 예 매   프 로 그 램 ◈◈◈◈◈");
        Scanner scanner = new Scanner(System.in);

        if (!Login.login())       return;  
        
        for (int row = 1; row < 10; row++) {
            for (char seat = 'A'; seat <= 'D'; seat++) {
                seats.put(row + "" + seat, " ");
            }
        }
        
        String loggedInUserID = Login.getLoggedInUserID();
       
        while (true) {
        	System.out.print("\n1. 좌석조회");
            System.out.print("\t2. 버스예약");
            System.out.print("\t3. 예약조회");
            System.out.print("\t4. 예약취소");
            System.out.println("\t5. 종료");
            System.out.print("번호를 입력하세요: ");
            int menu = scanner.nextInt();

            switch (menu) {
            	case 1:		checkSeat(scanner);
            		break;
                case 2:     busReservation(scanner, loggedInUserID);
                    break;
                case 3:  	checkReservation(scanner, loggedInUserID);
                    break;
                case 4:     cancelReservation(scanner, loggedInUserID);
                    break;
                case 5:     System.out.println("\n프로그램을 종료합니다");
                    scanner.close();
                    return;
                default:     System.out.println("잘못 입력하셨습니다.");
                    break;
            }
        }
    }
    
    public static  void checkSeat(Scanner scanner) {
    	for (int i = 0; i < Bus.buses.length; i++) {
            System.out.print((i+1) + ". " + Bus.buses[i].getRoute()+"  ");
        }
        System.out.print("\n노선을 선택하세요: ");
        int routeNum = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println();
        String[] times = Bus.buses[routeNum-1].getTimes();
        for (int i = 0; i < times.length; i++) {
            System.out.print((i+1) + ". " + times[i]+"  ");
        }
        System.out.print("\n시간대를 선택하세요:");
        int timeNum = scanner.nextInt();
	    SeatLayout seatLayout = new SeatLayout(seats);
	    seatLayout.printSeatStatus();
    }

	public static void busReservation(Scanner scanner, String loggedInUserID) {
        System.out.println();
        for (int i = 0; i < Bus.buses.length; i++) {
            System.out.print((i+1) + ". " + Bus.buses[i].getRoute()+"  ");
        }
        System.out.print("\n노선을 선택하세요: ");
        int routeNum = scanner.nextInt();
        scanner.nextLine();  

        System.out.println();
        String[] times = Bus.buses[routeNum-1].getTimes();
        for (int i = 0; i < times.length; i++) {
            System.out.print((i+1) + ". " + times[i]+"  ");
        }
        System.out.print("\n시간대를 선택하세요:");
        int timeNum = scanner.nextInt();

        System.out.print("\n좌석을 선택해주세요: ");
        scanner.nextLine();
        String seatNumber = scanner.nextLine().toUpperCase();

        if (!seats.containsKey(seatNumber)) {
            System.out.println("잘못된 좌석 번호입니다.");
        } else if (seats.get(seatNumber).equals("x") && seats.containsKey(seatNumber + "-" + routeNum + "-" + timeNum)) {
            System.out.println("이미 예약된 좌석입니다.");
        } else {
            String fileName = "projectzzz/client";
            try {
                File file = new File(fileName);
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                
                Map<String, String> reservations = new HashMap<>();
                for (String line : lines) {
                    String[] tokens = line.trim().split("\\s+");
                    String seatNumber1 = tokens[tokens.length - 1];
                    String routeTime = tokens[tokens.length - 3] + "-" + tokens[tokens.length - 2];
                    String key = seatNumber1 + "-" + routeTime;
                    reservations.put(key, "x");
                }
     
                if (reservations.containsKey(seatNumber + "-" + Bus.buses[routeNum-1].getRoute() + "-" + times[timeNum-1])) {
                    System.out.println("이미 예약된 좌석입니다.");
                } else {
                    boolean foundUser = false;
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);
                        if (line.startsWith(loggedInUserID + " ")) {
                            line = line.trim();
                            if (!line.endsWith(" ")) {
                                line += " ";
                            }
                            line += Bus.buses[routeNum-1].getRoute() + "  " + times[timeNum-1] + "  " + seatNumber;
                            lines.set(i, line);
                            foundUser = true;
                            break;
                        }
                    }
                    Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
                    System.out.println("선택한 좌석 " + seatNumber + "가 예약되었습니다");
                    seats.replace(seatNumber, "x");
                    seats.put(seatNumber + "-" + routeNum + "-" + timeNum, "x");
                }
            } catch (IOException e) {
                System.out.println("파일을 읽거나 쓰는 도중에 오류가 발생했습니다.");
            }
        }
    }

    public static void checkReservation(Scanner scanner, String loggedInUserID) {
        String fileName = "projectzzz/client";
        try {
            File file = new File(fileName);
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            boolean found = false;
            for (String line : lines) {
                String[] tokens = line.trim().split("\\s+");
                if (line.contains(loggedInUserID)) {
                    found = true;
                    if (tokens.length < 4) { 
                        System.out.println("예약된 좌석이 없습니다.");
                        return;
                    }
                    System.out.println("  " + tokens[2] + "님의 예약 정보입니다. ");
                    int count = 0;
                    for (int i = 3; i < tokens.length; i++) {
                        count++;
                        System.out.print("  " + tokens[i] + " ");
                        if (count % 3 == 0) {
                            System.out.println();
                        }
                    }
                    return;
                }
            }
            if (!found) {
                System.out.println("예약된 좌석이 없습니다.");
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }
    }

    public static void cancelReservation(Scanner scanner, String loggedInUserID) {
        String fileName = "projectzzz/client";
        try {
            File file = new File(fileName);
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(loggedInUserID)) {
                    String[] tokens = line.trim().split("\\s+");
                    System.out.println("  취소 가능한 예약입니다.");
                    for (int j = 3; j < tokens.length; j += 3) {
                        if (tokens[j] != null) {
                            System.out.printf("  %s   %s   %s\n", tokens[j], tokens[j + 1], tokens[j + 2]);
                        }
                    }
                    System.out.print("취소할 예약을 선택해주세요: ");
                    int selection = scanner.nextInt();
                    if (selection < 1 || selection > (tokens.length - 3) / 3) {
                        System.out.println("  잘못된 선택입니다.");
                        return;
                    }
                    int index = (selection - 1) * 3 + 3;
                    String reservationInfo = tokens[index] + " " + tokens[index + 1] + " " + tokens[index + 2];
                    System.out.printf("선택한 예약 (%s) 이 취소되었습니다.\n", reservationInfo);
                    String seatNumber = tokens[index + 2];
                    seats.replace(seatNumber, " ");
                    List<String> newTokens = new ArrayList<>();
                    for (int j = 3; j < tokens.length; j += 3) {
                        if (j == index) {
                            continue;
                        }
                        if (tokens[j] != null && !tokens[j].isEmpty()) {
                            newTokens.add(tokens[j] + " " + tokens[j + 1] + " " + tokens[j + 2]);
                        }
                    }
                    if (newTokens.isEmpty()) {
                        lines.remove(i);
                    } else {
                        String newLine = tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + String.join(" ", newTokens);
                        lines.set(i, newLine);
                    }
                    Files.write(file.toPath(), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                    return;
                }
            }
            System.out.println("  예약 정보를 찾을 수 없습니다.");
        } catch (IOException e) {
            System.out.println("  파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}


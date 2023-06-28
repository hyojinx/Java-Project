package projectzzz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Login {
    private static String loggedInUserID;

    public static boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("아이디를 입력하세요: ");
        String username = scanner.next();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.next();

        try (BufferedReader br = new BufferedReader(new FileReader("projectzzz/client"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens[0].equals(username) && tokens[1].equals(password)) {
                    System.out.println(tokens[2] + "님 안녕하세요");
                    loggedInUserID = username;
                    return true;
                }
            }

            System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
            System.out.println("회원가입을 하시겠습니까? (Y/N)");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("Y")) {
                register();
                System.out.println("회원가입이 완료되었습니다. 다시 로그인 해주세요."); }
            else       System.out.println("다시 로그인 해주세요.");
            return false;
        } catch (IOException e) {
            System.out.println("파일을 읽어오는 도중 오류가 발생했습니다.");
            return false;
        }
    }

    public static void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("아이디를 입력하세요: ");
        String username = scanner.next();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.next();
        System.out.print("이름을 입력하세요: ");
        String name = scanner.next();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("projectzzz/client", true))) {
            bw.newLine();
            bw.write(username + " " + password + " " + name);
        } catch (IOException e) {
            System.out.println("파일을 쓰는 도중 오류가 발생했습니다.");
        }
    }

    public static void checkReservation(Scanner scanner) {
        String loggedInUserID = Login.getLoggedInUserID();
        String fileName = "C:\\Users\\82109\\eclipse-workspace\\finalproject\\src\\finalproject\\reservation";
        try {
            List<String> lines = Files.readAllLines(new File(fileName).toPath(), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] tokens = line.split(" ");
                if (tokens[0].equals(loggedInUserID)) {
                    System.out.println("예약 정보: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }
    }

	public static String getLoggedInUserID() {
		return loggedInUserID;
	}

	public static void setLoggedInUserID(String loggedInUserID) {
		Login.loggedInUserID = loggedInUserID;
	}
}

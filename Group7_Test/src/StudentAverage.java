import java.util.Scanner;

public class StudentAverage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập tên sinh viên: ");
        String name = scanner.nextLine();

        System.out.print("Nhập điểm Toán: ");
        double math = scanner.nextDouble();

        System.out.print("Nhập điểm Lý: ");
        double physics = scanner.nextDouble();

        System.out.print("Nhập điểm Hóa: ");
        double chemistry = scanner.nextDouble();

        double average = (math + physics + chemistry) / 3;

        System.out.printf("Sinh viên: %s%n", name);
        System.out.printf("Điểm trung bình: %.2f%n", average);
    }
}

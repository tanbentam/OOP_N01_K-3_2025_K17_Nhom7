import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String filename = "data.dat";

    // Load dữ liệu từ file (nếu có)
    ClassManager manager = FileManager.loadData(filename);

    // Nếu file chưa có dữ liệu, cho phép nhập mới
    if (manager.getClassrooms().isEmpty()) {
        System.out.println("⚠ Dữ liệu trống, vui lòng nhập dữ liệu ban đầu.");
        manager = initializeClassManager(scanner);
        FileManager.saveData(manager, filename); // lưu lại ngay sau khi nhập
    }

    // Vòng lặp menu chính
    while (true) {
        displayMenu();
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 0) break;

        System.out.print("Nhập tên lớp: ");
        String className = scanner.nextLine();
        Classroom cls = manager.getClassroom(className);
        if (cls == null) {
            System.out.println("Không tìm thấy lớp.");
            continue;
        }

        handleChoice(choice, className, scanner, manager, cls);
    }

    // Sau khi kết thúc, lưu lại dữ liệu
    scanner.close();
    FileManager.saveData(manager, filename);
    }

    private static ClassManager initializeClassManager(Scanner scanner) {
        ClassManager manager = new ClassManager();
        //ClassManager manager = FileManager.loadData("data.dat");
        System.out.print("Nhập số lượng lớp học: ");
        int numClasses = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numClasses; i++) {
            System.out.println("\nNhập thông tin lớp học thứ " + (i + 1) + ":");
            System.out.print("Tên lớp: ");
            String className = scanner.nextLine();

            // Nhập thông tin giáo viên cho lớp
            System.out.print("Mã giáo viên: ");
            String teacherId = scanner.nextLine();
            System.out.print("Tên giáo viên: ");
            String teacherName = scanner.nextLine();
            System.out.print("Bộ môn: ");
            String department = scanner.nextLine();

            Teacher teacher = new Teacher(teacherId, teacherName, department);
            Classroom classroom = new Classroom(className, teacher);

            // Nhập danh sách học sinh cho lớp
            System.out.print("Số lượng học sinh trong lớp: ");
            int numStudents = Integer.parseInt(scanner.nextLine());

            for (int j = 0; j < numStudents; j++) {
                System.out.println("Nhập thông tin học sinh thứ " + (j + 1) + ":");
                System.out.print("Mã học sinh: ");
                String studentId = scanner.nextLine();
                System.out.print("Tên học sinh: ");
                String studentName = scanner.nextLine();
                System.out.print("Tuổi: ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.print("Môn Học: ");
                String subject = scanner.nextLine();
                System.out.print("Điểm: ");
                double grade = Double.parseDouble(scanner.nextLine());

                Student student = new Student(studentId, studentName, age, subject, grade);
                classroom.addStudent(student);
                System.out.println("-----------------------------------------");
            }

            manager.addClassroom(classroom);
        System.out.println("===========================================");
        }

        return manager;
    }

    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Điểm danh học sinh");
        System.out.println("2. Hiển thị học sinh có mặt");
        System.out.println("3. Hiển thị học sinh nghỉ");
        System.out.println("4. Chuyển học sinh sang lớp khác");
        System.out.println("5. Hiển thị danh sách các lớp hiện có");
        System.out.println("6. Reset");
        System.out.println("0. Thoát");
        System.out.print("Chọn: ");
    }

    private static void handleChoice(int choice,String classname, Scanner scanner, ClassManager manager, Classroom cls) {
        switch (choice) {
            case 1:
                markAttendance(scanner, cls);
                break;
            case 2:
                displayPresentStudents(scanner, manager, cls.getClassName());
                break;
            case 3:
                displayAbsentStudents(scanner, manager, cls.getClassName());
                break;
            case 4:
                transferStudent(scanner, manager, cls.getClassName());
                break;
            case 5:
                String name = classname;
                manager.printAllClasses(name);
                break;
            case 6:
                deleteDataFile("data.dat");
                manager = new ClassManager(); // tạo lại dữ liệu trống
                System.out.println("✅ Đã xóa toàn bộ dữ liệu.");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    private static void markAttendance(Scanner scanner, Classroom cls) {
        System.out.print("Nhập ngày (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        for (Student student : cls.getStudentList()) {
            System.out.println("Học sinh: " + student.getName());
            System.out.print("Có mặt? (y/n): ");
            boolean present = scanner.nextLine().equalsIgnoreCase("y");
            boolean permission = false;

            if (!present) {
                System.out.print("Có phép? (y/n): ");
                permission = scanner.nextLine().equalsIgnoreCase("y");
            }
            student.markAttendance(date, present, permission);
        }
    }

    private static void displayPresentStudents(Scanner scanner, ClassManager manager, String className) {
        System.out.print("Nhập ngày (yyyy-mm-dd): ");
        LocalDate presentDate = LocalDate.parse(scanner.nextLine());
        manager.displayPresentStudents(className, presentDate);
    }

    private static void displayAbsentStudents(Scanner scanner, ClassManager manager, String className) {
        System.out.print("Nhập ngày (yyyy-mm-dd): ");
        LocalDate absentDate = LocalDate.parse(scanner.nextLine());
        manager.displayAbsentStudents(className, absentDate);
    }

    private static void transferStudent(Scanner scanner, ClassManager manager, String className) {

        System.out.print("Nhập mã học sinh cần chuyển: ");
        String studentId = scanner.nextLine();
        System.out.print("Nhập tên lớp mới: ");
        String newClass = scanner.nextLine();
        manager.transferStudent(studentId, className, newClass);
    }

    public static void deleteDataFile(String filename) {
    File file = new File(filename);
    if (file.exists()) {
        if (file.delete()) {
            System.out.println("✅ File đã được xóa thành công.");
        } else {
            System.out.println("⚠ Không thể xóa file.");
        }
    } else {
        System.out.println("⚠ File không tồn tại.");
    }
}

}


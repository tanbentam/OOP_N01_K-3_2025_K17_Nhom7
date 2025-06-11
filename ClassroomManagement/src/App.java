import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Nhập dữ liệu ban đầu

        // Bắt đầu chương trình: đọc file dữ liệu hoặc tạo mới
        ClassManager manager = FileManager.loadData("data.dat");

        // (phần nhập dữ liệu như ở trên bạn làm)
        manager = initializeClassManager(scanner);

        // Sau khi xử lý, lưu lại dữ liệu:
        FileManager.saveData(manager, "data.dat");

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

            handleChoice(choice, scanner, manager, cls);
        }
        FileManager.saveData(manager, "data.dat");
        scanner.close();
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
        System.out.println("0. Thoát");
        System.out.print("Chọn: ");
    }

    private static void handleChoice(int choice, Scanner scanner, ClassManager manager, Classroom cls) {
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
                manager.printAllClasses("K17_CNTT1");
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
}


import java.util.Scanner;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        // Tạo giáo viên
        Teacher teacher1 = new Teacher("T01", "Thầy Tráng", "CNTT");
        Teacher teacher2 = new Teacher("T02", "Cô Thu", "OOP");

        // Tạo học sinh
        Student student1 = new Student("S01", "Tân", 20, "Toan", 10);
        Student student2 = new Student("S02", "Hải", 19, "Li", 9);
        Student student3 = new Student("S03", "Lâm", 20, "Ngu Van", 8);

        // Tạo lớp học
        Classroom classA = new Classroom("K17_CNTT1", teacher1);
        classA.addStudent(student1);
        classA.addStudent(student2);

        Classroom classB = new Classroom("K17_CNTT2", teacher2);
        classB.addStudent(student3);

        // Quản lý lớp
        ClassManager manager = new ClassManager();
        manager.addClassroom(classA);
        manager.addClassroom(classB);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Điểm danh học sinh");
            System.out.println("2. Hiển thị học sinh có mặt");
            System.out.println("3. Hiển thị học sinh nghỉ");
            System.out.println("4. Chuyển học sinh sang lớp khác");
            System.out.println("5. Hiển thị danh sách các lớp hiện có");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) break;

            System.out.print("Nhập tên lớp: ");
            String className = scanner.nextLine();
            Classroom cls = manager.getClassroom(className);
            if (cls == null) {
                System.out.println("Không tìm thấy lớp.");
                continue;
            }

            switch (choice) {
                case 1:
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
                    break;

                case 2:
                    System.out.print("Nhập ngày (yyyy-mm-dd): ");
                    LocalDate presentDate = LocalDate.parse(scanner.nextLine());
                    manager.displayPresentStudents(className, presentDate);
                    break;

                case 3:
                    System.out.print("Nhập ngày (yyyy-mm-dd): ");
                    LocalDate absentDate = LocalDate.parse(scanner.nextLine());
                    manager.displayAbsentStudents(className, absentDate);
                    break;

                case 4:
                    System.out.print("Nhập mã học sinh cần chuyển: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Nhập tên lớp mới: ");
                    String newClass = scanner.nextLine();
                    manager.transferStudent(studentId, className, newClass);
                    break;

                case 5: 
                    System.out.println("danh sach lop:");
                    String inputclassName = scanner.nextLine();
                    manager.printAllClasses(inputclassName);
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }

        scanner.close();
    }
}


import java.util.ArrayList;
import java.util.Scanner;
import ClassroomManagement.Student;
import ClassroomManagement.StudentList;
public class TestStudent2 {
    public void testEditDelete() {
        StudentList stuList = new StudentList();
        Student s1 = new Student("12345", "Nguyen Thi Lan Anh", 20);
        Student s2 = new Student("2", "Tran Van Minh", 19);
        Student s3 = new Student("101010", "Nguyen An", 18);
        stuList.addStudent(s1);
        stuList.addStudent(s2);
        stuList.addStudent(s3);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter student ID to edit:");
        String studentIdToEdit = scanner.nextLine();
        System.out.println("Enter new fullname:");
        String newName = scanner.nextLine();
        stuList.getEditStudent(newName, studentIdToEdit);
        stuList.printStudentList();

        System.out.println("Enter student ID to delete:");
        String studentIdToDelete = scanner.nextLine();
        stuList.getDeleteStudent(studentIdToDelete);
        System.out.println("Danh sách sau khi xóa:");
        stuList.printStudentList();
    }

    public static void main(String[] args) {
        TestStudent2 test = new TestStudent2();
        test.testEditDelete();
    }
}
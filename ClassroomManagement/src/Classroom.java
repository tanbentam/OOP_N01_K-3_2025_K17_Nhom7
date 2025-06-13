import java.util.ArrayList;
import java.io.Serializable;

public class Classroom implements Serializable {
    private String className;
    private Teacher teacher; // Lưu đối tượng Teacher
    private ArrayList<Student> studentList; // Lưu danh sách đối tượng Student

    public Classroom(String className, Teacher teacher) {
        this.className = className;
        this.teacher = teacher;
        this.studentList = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public Teacher getTeacher() { // Trả về đối tượng Teacher
        return teacher;
    }

    public void addStudent(Student student) { // Nhận đối tượng Student
        studentList.add(student);
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    public ArrayList<Student> getStudentList() {
        return new ArrayList<>(studentList); // Trả về bản sao để bảo vệ dữ liệu
    }

    public void printClassInfo() {
        System.out.println("Class Name: " + className);
        System.out.println("Teacher: ");
        teacher.printInfo();
        System.out.println("Students:");
        for (Student student : studentList) {
            student.printInfo();
            System.out.println("---");
        }
    }
}
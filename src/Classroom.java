import java.util.ArrayList;

public class Classroom {
    private String className;
    private ArrayList<Student> studentList;

    // Constructor
    public Classroom(String className) {
        this.className = className;
        this.studentList = new ArrayList<>();
    }

    // Thêm học sinh vào lớp
    public void addStudent(Student student) {
        studentList.add(student);
    }

    // Phương thức tìm kiếm học sinh theo mã học sinh
    public void findStudentById(String studentId) {
        for (Student student : studentList) {
            if (student.getId().equals(studentId)) {
                System.out.println("Thông tin học sinh:");
                student.printInfo();
                return;
            }
        }
        System.out.println("Không tìm thấy học sinh với mã " + studentId + " trong lớp " + className);
    }
}

class Student {
    private String id;
    private String name;
    private int age;

    // Constructor
    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getter cho id
    public String getId() {
        return id;
    }

    // Hiển thị thông tin học sinh
    public void printInfo() {
        System.out.println("Mã học sinh: " + id);
        System.out.println("Tên: " + name);
        System.out.println("Tuổi: " + age);
    }
}

class App {
    public static void main(String[] args) {
        // Tạo lớp học
        Classroom classA = new Classroom("10A1");

        // Thêm học sinh vào lớp
        classA.addStudent(new Student("S01", "Nam", 16));
        classA.addStudent(new Student("S02", "Lan", 15));
        classA.addStudent(new Student("S03", "Hùng", 16));

        // Tìm kiếm học sinh
        System.out.println("Tìm kiếm học sinh với mã S01 trong lớp 10A1:");
        classA.findStudentById("S01");

        System.out.println("\nTìm kiếm học sinh với mã S04 trong lớp 10A1:");
        classA.findStudentById("S04");
    }
}
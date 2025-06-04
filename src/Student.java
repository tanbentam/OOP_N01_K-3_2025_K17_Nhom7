public class Student {

    public String StudentId;
    public String StudentName;
    public int StudentAge;

    public Student(String id, String name, int age) {
        this.StudentId = id;
        this.StudentName = name;
        this.StudentAge = age;
    }

    // Getter methods
    public String getId() {
        return StudentId;
    }

    public String getName() {
        return StudentName;
    }

    public int getAge() {
        return StudentAge;
    }

    // Setter methods
    public void setName(String name) {
        this.StudentName = name;
    }

    public void setAge(int age) {
        this.StudentAge = age;
    }

    // Display info
    public void printInfo() {
        System.out.println("Student ID: " + StudentId);
        System.out.println("Name: " + StudentName);
        System.out.println("Age: " + StudentAge);
    }
}

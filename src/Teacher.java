public class Teacher {
    private String TeacherId;
    private String TeacherName;
    private String TeacherSubject;

    public Teacher(String id, String name, String subject) {
        this.TeacherId = id;
        this.TeacherName = name;
        this.TeacherSubject = subject;
    }

    // Getter methods
    public String getId() {
        return TeacherId;
    }

    public String getName() {
        return TeacherName;
    }

    public String getSubject() {
        return TeacherName;
    }

    // Setter methods
    public void setName(String name) {
        this.TeacherName = name;
    }

    public void setSubject(String subject) {
        this.TeacherName = subject;
    }

    // Display info
    public void printInfo() {
        System.out.println("Teacher ID: " + TeacherId);
        System.out.println("Name: " + TeacherName);
        System.out.println("Subject: " + TeacherName);
    }
}

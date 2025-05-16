public class Student {
    private String id;
    private String name;
    private int age;
    private Score score; 

    public Student(String id, String name, int age, String subject, double scoreValue) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = new Score(id, subject, scoreValue); 
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public Score getscore() {
        return score; 
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setscore(String subject, double scoreValue) {
        this.score.setsubject(subject);
        this.score.setscoreValue(scoreValue);
    }

    // Display info
    public void printInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Score: " + score);
    }
}

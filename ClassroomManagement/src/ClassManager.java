import java.util.ArrayList;

public class ClassManager {
    private ArrayList<Classroom> classrooms;

    public ClassManager() {
        classrooms = new ArrayList<>();
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public boolean removeClassroom(String className) {
        for (Classroom c : classrooms) {
            if (c.getClassName().equalsIgnoreCase(className)) {
                classrooms.remove(c);
                return true;
            }
        }
        return false;
    }

    public Classroom getClassroom(String className) {
        for (Classroom c : classrooms) {
            if (c.getClassName().equalsIgnoreCase(className)) {
                return c;
            }
        }
        return null;
    }

    public void printAllClasses() {
        if (classrooms.isEmpty()) {
            System.out.println("No classes available.");
        } else {
            for (Classroom c : classrooms) {
                c.printClassInfo();
                System.out.println("-----------------------");
            }
        }
    }
}

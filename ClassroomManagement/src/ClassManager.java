import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ClassManager implements Serializable {
    private ArrayList<Classroom> classrooms;

    public ClassManager() {
        classrooms = new ArrayList<>();
    }

    public void displayPresentStudents(String className, LocalDate date) {
        Classroom cls = getClassroom(className);
        if (cls == null) {
            System.out.println("Không tìm thấy lớp.");
            return;
        }

        System.out.println("Danh sách học sinh có mặt ngày " + date + ":");
        for (Student s : cls.getStudentList()) {
            if (s.isPresentOnDate(date)) {
                System.out.println("- " + s.getName());
            }
        }
    }

     public void displayAbsentStudents(String className, LocalDate date) {
        Classroom cls = getClassroom(className);
        if (cls == null) {
            System.out.println("Không tìm thấy lớp.");
            return;
        }

        System.out.println("Danh sách học sinh vắng ngày " + date + ":");
        for (Student s : cls.getStudentList()) {
            if (!s.isPresentOnDate(date)) {
                String status = s.hasPermissionOnDate(date) ? " (Có phép)" : " (Không phép)";
                System.out.println("- " + s.getName() + status);
            }
        }
    }


    public void transferStudent(String studentId, String fromClassName, String toClassName) {
    Classroom fromClass = getClassroom(fromClassName);
    Classroom toClass = getClassroom(toClassName);

    if (fromClass == null || toClass == null) {
        System.out.println("Một trong hai lớp không tồn tại.");
        return;
    }

    Student foundStudent = null;
    for (Student s : fromClass.getStudentList()) {
        if (s.getId().equalsIgnoreCase(studentId)) {
            foundStudent = s;
            break;
        }
    }

    if (foundStudent == null) {
        System.out.println("Không tìm thấy học sinh trong lớp " + fromClassName);
        return;
    }

    fromClass.removeStudent(foundStudent);
    toClass.addStudent(foundStudent);
    System.out.println("Đã chuyển học sinh " + foundStudent.getName() + " sang lớp " + toClassName);
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

      public List<Classroom> getClassrooms() {
        return classrooms;
    }


    
    public void printAllClasses(String classroom) {
        if (classrooms.isEmpty()) {
            System.out.println("No classes available.");
        } else {
            for (Classroom c : classrooms) {
                if (c.getClassName().equalsIgnoreCase(classroom)) {
                    c.printClassInfo();
                     System.out.println
                     ("-------------------------------------------------");
                    return;
                    }   
                }
        }
    }
}

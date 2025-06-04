import org.junit.Test;
import static org.junit.Assert.*;

public class TestClassroom {
    @Test
    public void testClassroomCreation() {
        Teacher teacher = new Teacher("T01", "Thầy An", "Toán");
        Classroom classroom = new Classroom("10A1", teacher);
        assertEquals("10A1", classroom.getClassName());
        assertEquals(teacher, classroom.getTeacher());
        assertTrue(classroom.getStudentList().isEmpty());
    }

    @Test
    public void testAddStudent() {
        Teacher teacher = new Teacher("T01", "Thầy An", "Toán");
        Classroom classroom = new Classroom("10A1", teacher);
        Student student = new Student("S01", "Nam", 16);
        classroom.addStudent(student);
        assertEquals(1, classroom.getStudentList().size());
        assertEquals(student, classroom.getStudentList().get(0));
    }

    @Test
    public void testRemoveStudent() {
        Teacher teacher = new Teacher("T01", "Thầy An", "Toán");
        Classroom classroom = new Classroom("10A1", teacher);
        Student student = new Student("S01", "Nam", 16);
        classroom.addStudent(student);
        classroom.removeStudent("S01");
        assertEquals(0, classroom.getStudentList().size());
    }
}
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStudent {
    @Test
    public void testStudentCreation() {
        Student student = new Student("S01", "Nam", 16);
        assertEquals("S01", student.getId());
        assertEquals("Nam", student.getName());
        assertEquals(16, student.getAge());
    }

    @Test
    public void testSetName() {
        Student student = new Student("S01", "Nam", 16);
        student.setName("Lan");
        assertEquals("Lan", student.getName());
    }

    @Test
    public void testSetAge() {
        Student student = new Student("S01", "Nam", 16);
        student.setAge(17);
        assertEquals(17, student.getAge());
    }
}
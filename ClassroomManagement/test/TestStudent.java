import org.junit.Test;
import static org.junit.Assert.*;

public class TestStudent {
    @Test
    public void testStudentCreation() {
        Student student = new Student("S01", "Tân", 20);
        assertEquals("S01", student.getId());
        assertEquals("Tân", student.getName());
        assertEquals(20, student.getAge());
    }

    @Test
    public void testSetName() {
        Student student = new Student("S01", "Tân", 20);
        student.setName("Lan");
        assertEquals("Lan", student.getName());
    }

    @Test
    public void testSetAge() {
        Student student = new Student("S01", "Tân", 20);
        student.setAge(17);
        assertEquals(17, student.getAge());
    }
}

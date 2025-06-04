import org.junit.Test;
import static org.junit.Assert.*;

public class TestTeacher {
    @Test
    public void testTeacherCreation() {
        Teacher teacher = new Teacher("T01", "Thầy An", "Toán");
        assertEquals("T01", teacher.getId());
        assertEquals("Thầy An", teacher.getName());
        assertEquals("Toán", teacher.getSubject());
    }

    @Test
    public void testSetName() {
        Teacher teacher = new Teacher("T01", "Thầy An", "Toán");
        teacher.setName("Cô Lan");
        assertEquals("Cô Lan", teacher.getName());
    }

    @Test
    public void testSetSubject() {
        Teacher teacher = new Teacher("T01", "Thầy An", "Toán");
        teacher.setSubject("Văn");
        assertEquals("Văn", teacher.getSubject());
    }
}
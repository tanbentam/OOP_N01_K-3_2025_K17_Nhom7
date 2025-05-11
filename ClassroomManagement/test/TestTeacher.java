import org.junit.Test;
import static org.junit.Assert.*;

public class TestTeacher {
    @Test
    public void testTeacherCreation() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        assertEquals("T01", teacher.getId());
        assertEquals("Thầy Tráng", teacher.getName());
        assertEquals("CNTT", teacher.getSubject());
    }

    @Test
    public void testSetName() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        teacher.setName("Cô Lan");
        assertEquals("Cô Lan", teacher.getName());
    }

    @Test
    public void testSetSubject() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        teacher.setSubject("Kinh tế");
        assertEquals("Kinh tế", teacher.getSubject());
    }
}

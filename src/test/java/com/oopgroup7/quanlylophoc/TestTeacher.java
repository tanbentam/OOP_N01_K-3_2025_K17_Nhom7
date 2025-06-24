package com.oopgroup7.quanlylophoc;
import org.junit.Test;
import static org.junit.Assert.*;
import com.oopgroup7.quanlylophoc.Model.Teacher;

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
        teacher.setName("Cô Thu");
        assertEquals("Cô Thu", teacher.getName());
    }

    @Test
    public void testSetSubject() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        teacher.setSubject("OOP");
        assertEquals("OOP", teacher.getSubject());
    }
}

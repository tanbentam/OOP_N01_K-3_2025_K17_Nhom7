package com.oopgroup7.quanlylophoc;
import org.junit.Test;
import com.oopgroup7.quanlylophoc.Model.Student;

import static org.junit.Assert.*;

public class TestStudent {
    @Test
    public void testStudentCreation() {
        // Using the constructor that exists in Student.java (name, age, subject, scoreValue)
        Student student = new Student("Tân", 20, "Math", 8.5);
        
        // Update the assertions to match the new implementation
        assertNotNull(student.getId());
        assertEquals("Tân", student.getName());
        assertEquals(20, student.getAge());
        assertEquals("Math", student.getScore().getsubject());
        assertEquals(8.5, student.getScore().getscoreValue(), 0.001);
    }

    @Test
    public void testSetName() {
        Student student = new Student("Tân", 20, "Math", 8.5);
        student.setName("Lan");
        assertEquals("Lan", student.getName());
    }

    @Test
    public void testSetAge() {
        Student student = new Student("Tân", 20, "Math", 8.5);
        student.setAge(17);
        assertEquals(17, student.getAge());
    }
    
    @Test
    public void testSetScore() {
        Student student = new Student("Tân", 20, "Math", 8.5);
        student.setscore("Physics", 9.0);
        assertEquals("Physics", student.getScore().getsubject());
        assertEquals(9.0, student.getScore().getscoreValue(), 0.001);
    }
}
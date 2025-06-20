package com.oopgroup7.quanlylophoc;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Teacher;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestClassroom {
    @Test
    public void testClassroomCreation() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        Classroom classroom = new Classroom("K17_CNTT1", teacher);
        assertEquals("K17_CNTT1", classroom.getClassName());
        assertEquals(teacher, classroom.getTeacher());
        assertTrue(classroom.getStudentList().isEmpty());
    }

    @Test
    public void testAddStudent() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        Classroom classroom = new Classroom("K17_CNTT1", teacher);
        Student student = new Student("Tân", 20, "Math", 8.5);
        classroom.addStudent(student);
        assertEquals(1, classroom.getStudentList().size());
        assertEquals(student, classroom.getStudentList().get(0));
    }

    @Test
    public void testRemoveStudent() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        Classroom classroom = new Classroom("K17_CNTT1", teacher);
        Student student = new Student("Tân", 20, "Math", 8.5);
        classroom.addStudent(student);
        classroom.removeStudent(student);
        assertEquals(0, classroom.getStudentList().size());
    }
}

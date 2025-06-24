package com.oopgroup7.quanlylophoc;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Teacher;
import org.junit.Test;
import static org.junit.Assert.*;

import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudentId;
import java.util.stream.Collectors;
import java.util.List;


public class TestClassroom {

    @Test
    public void testClassroomCreation() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        Classroom classroom = new Classroom("K17_CNTT1", teacher);
        assertEquals("K17_CNTT1", classroom.getClassName());
        assertEquals(teacher, classroom.getTeacher());
        List<Student> students = classroom.getClassroomStudents()
            .stream()
            .map(cs -> cs.getStudent())
            .collect(Collectors.toList());
        assertTrue(students.isEmpty());
    }

    @Test
    public void testAddStudent() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        Classroom classroom = new Classroom("K17_CNTT1", teacher);
        Student student = new Student("Tân", 20, "Math", 8.5);

        // Tạo ClassroomStudent và thêm vào classroom
        ClassroomStudent cs = new ClassroomStudent();
        cs.setClassroom(classroom);
        cs.setStudent(student);
        ClassroomStudentId id = new ClassroomStudentId();
        id.setClassroomId(classroom.getId());
        id.setStudentId(student.getId());
        cs.setId(id);
        classroom.getClassroomStudents().add(cs);

        List<Student> students = classroom.getClassroomStudents()
            .stream()
            .map(c -> c.getStudent())
            .collect(Collectors.toList());
        assertEquals(1, students.size());
        assertEquals(student, students.get(0));
    }

    @Test
    public void testRemoveStudent() {
        Teacher teacher = new Teacher("T01", "Thầy Tráng", "CNTT");
        Classroom classroom = new Classroom("K17_CNTT1", teacher);
        Student student = new Student("Tân", 20, "Math", 8.5);

        ClassroomStudent cs = new ClassroomStudent();
        cs.setClassroom(classroom);
        cs.setStudent(student);
        ClassroomStudentId id = new ClassroomStudentId();
        id.setClassroomId(classroom.getId());
        id.setStudentId(student.getId());
        cs.setId(id);
        classroom.getClassroomStudents().add(cs);

        // Xóa ClassroomStudent khỏi classroom
        classroom.getClassroomStudents().remove(cs);

        List<Student> students = classroom.getClassroomStudents()
            .stream()
            .map(c -> c.getStudent())
            .collect(Collectors.toList());
        assertEquals(0, students.size());
    }
}
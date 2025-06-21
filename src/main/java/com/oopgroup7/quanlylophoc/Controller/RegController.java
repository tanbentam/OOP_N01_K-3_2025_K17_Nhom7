package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Teacher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

/**
 * Register Controller (ResController) manages the registration of students and teachers
 * and provides functionality to add, remove, find, and manage registrations.
 */
public class RegController implements Serializable {
    
    private Map<String, Student> registeredStudents;
    private Map<String, Teacher> registeredTeachers;
    
    public RegController() {
        this.registeredStudents = new HashMap<>();
        this.registeredTeachers = new HashMap<>();
    }
    
    /**
     * Register a new student
     * @param student The student to register
     * @return true if registration successful, false if student ID already exists
     */
    public boolean registerStudent(Student student) {
        if (registeredStudents.containsKey(student.getId().toString())) {
            return false; // Student with this ID already exists
        }
        registeredStudents.put(student.getId().toString(), student);
        return true;
    }
    
    /**
     * Register a new teacher
     * @param teacher The teacher to register
     * @return true if registration successful, false if teacher ID already exists
     */
    public boolean registerTeacher(Teacher teacher) {
        if (registeredTeachers.containsKey(teacher.getId().toString())) {
            return false; // Teacher with this ID already exists
        }
        registeredTeachers.put(teacher.getId().toString(), teacher);
        return true;
    }
    
    /**
     * Find a student by ID
     * @param id Student ID
     * @return The Student object if found, null otherwise
     */
    public Student findStudentById(String id) {
        return registeredStudents.get(id);
    }
    
    /**
     * Find a teacher by ID
     * @param id Teacher ID
     * @return The Teacher object if found, null otherwise
     */
    public Teacher findTeacherById(String id) {
        return registeredTeachers.get(id);
    }
    
    /**
     * Get all registered students
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(registeredStudents.values());
    }
    
    /**
     * Get all registered teachers
     * @return List of all teachers
     */
    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(registeredTeachers.values());
    }
    
    /**
     * Remove a student registration by ID
     * @param id Student ID
     * @return true if student was removed, false if not found
     */
    public boolean removeStudent(String id) {
        if (registeredStudents.containsKey(id)) {
            registeredStudents.remove(id);
            return true;
        }
        return false;
    }
    
    /**
     * Remove a teacher registration by ID
     * @param id Teacher ID
     * @return true if teacher was removed, false if not found
     */
    public boolean removeTeacher(String id) {
        if (registeredTeachers.containsKey(id)) {
            registeredTeachers.remove(id);
            return true;
        }
        return false;
    }
    
    /**
     * Get the count of registered students
     * @return Number of registered students
     */
    public int getStudentCount() {
        return registeredStudents.size();
    }
    
    /**
     * Get the count of registered teachers
     * @return Number of registered teachers
     */
    public int getTeacherCount() {
        return registeredTeachers.size();
    }
    
    /**
     * Find students by name (partial match)
     * @param name Name to search for
     * @return List of students whose names contain the search term
     */
    public List<Student> findStudentsByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : registeredStudents.values()) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }
    
    /**
     * Find teachers by name (partial match)
     * @param name Name to search for
     * @return List of teachers whose names contain the search term
     */
    public List<Teacher> findTeachersByName(String name) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : registeredTeachers.values()) {
            if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(teacher);
            }
        }
        return result;
    }
    
    /**
     * Find teachers by subject
     * @param subject Subject to search for
     * @return List of teachers who teach the specified subject
     */
    public List<Teacher> findTeachersBySubject(String subject) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : registeredTeachers.values()) {
            if (teacher.getSubject().equalsIgnoreCase(subject)) {
                result.add(teacher);
            }
        }
        return result;
    }
}
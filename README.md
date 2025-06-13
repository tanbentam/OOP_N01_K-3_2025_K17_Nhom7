Câu 1 (1 điểm). Tiêu đề của bài tập lớn cuối kỳ (nhóm sinh viên) lựa chọn là gì ? 

Tiêu đề: Hệ thống quản lý lớp học (Classroom Management System)

Câu 2: Phân tích cơ sở ít nhất 03 đối tượng cần thiết  
1. Student: Đại diện cho học sinh, lưu trữ thông tin gồm id, name, age.  
2. Teacher: Đại diện cho giáo viên, lưu trữ thông tin gồm id, name, subject.  
3. Classroom: Đại diện cho lớp học, chứa className, một Teacher, và danh sách các Student.

Câu 3: Cấu trúc thư mục đã làm

Câu 4: Có những Class : + ClassManager.java
                        + Classroom.java
                        + Student.java
                        + Teacher.java
                        + App.java
                        
Câu 5: Có những class kiểm định: + TestClassroom.java
                                 + TestStudent.java
                                 + TestTeacher.java

Link Git repo (Câu 3, 4, 5): https://github.com/NgHaiii/OOP_N01_K-3_2025_K17_Nhom7/

Link ReadMe (Câu 6): https://github.com/NgHaiii/OOP_N01_K-3_2025_K17_Nhom7/blob/main/README.md
---------------------------------------------------------------


miêu tả phương thức điểm danh 
@startuml
class ClassManager {
    - classrooms: List<Classroom>
    + addClassroom(c: Classroom)
    + getClassroom(name: String): Classroom
    + loadStudentsFromFile(path: String)
    + printAllClasses()
    + printClassInfoByName(name: String)
}

class Classroom {
    - className: String
    - teacher: Teacher
    - students: List<Student>
    + addStudent(s: Student)
    + removeStudent(id: String)
    + getStudents(): List<Student>
    + printClassInfo()
}

class Student {
    - id: String
    - name: String
    - attendance: Map<LocalDate, AttendanceRecord>
    + markAttendance(date: LocalDate, present: boolean, permission: boolean)
    + getId(): String
    + getName(): String
    + getAttendance(): Map<LocalDate, AttendanceRecord>
}

class AttendanceRecord {
    - present: boolean
    - permission: boolean
    + isPresent(): boolean
    + hasPermission(): boolean
}

class Teacher {
    - name: String
    + getName(): String
}

ClassManager "1" --> "*" Classroom
Classroom "1" --> "1" Teacher
Classroom "1" --> "*" Student
Student "1" --> "*" AttendanceRecord

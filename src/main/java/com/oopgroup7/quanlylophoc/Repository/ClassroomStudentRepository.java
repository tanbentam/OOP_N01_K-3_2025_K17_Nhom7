package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomStudentRepository extends JpaRepository<ClassroomStudent, ClassroomStudentId> {
}
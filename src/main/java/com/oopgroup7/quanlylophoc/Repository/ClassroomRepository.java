package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    Optional<Classroom> findByClassNameIgnoreCase(String className);
}
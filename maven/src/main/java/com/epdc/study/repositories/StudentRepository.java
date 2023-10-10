package com.epdc.study.repositories;

import com.epdc.study.table.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

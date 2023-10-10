package com.example.springdatajpa.repositories;

import com.example.springdatajpa.table.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

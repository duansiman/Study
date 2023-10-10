package com.example.springdatajpa.controller;

import com.example.springdatajpa.repositories.StudentRepository;
import com.example.springdatajpa.table.Student;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Setter(onMethod_ = @Autowired)
public class StudyController {

    private StudentRepository studentRepository;

    @GetMapping("/student")
    public List<Student> hello(){
        return studentRepository.findAll();
    }
}

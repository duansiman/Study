package com.epdc.study.controller;

import com.epdc.study.entity.Student;
import com.epdc.study.repository.StudentRepository;
import com.epdc.study.service.HelloService;
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

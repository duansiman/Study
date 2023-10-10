package com.epdc.study.repository;

import com.epdc.study.entity.Student;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2023-02-16.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	@Test
	public void test() {
//		Optional<Student> optional = studentRepository.findById(1L);
//		System.out.println(optional.orElse(new Student()));
	}

}
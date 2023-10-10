package com.epdc.study.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Data
@NoArgsConstructor
@ToString
//@Entity(name = "t_student")
public class Student {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public String name;
	public int age;

}

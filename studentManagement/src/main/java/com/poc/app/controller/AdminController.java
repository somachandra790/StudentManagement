package com.poc.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.app.entities.Student;
import com.poc.app.entities.Teacher;
import com.poc.app.entities.User;
import com.poc.app.repository.StudentRepository;
import com.poc.app.repository.TeacherRepository;
import com.poc.app.repository.UserRepository;

@RestController
@org.springframework.transaction.annotation.Transactional
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	StudentRepository studentRepository;

	@PostMapping("/addstudent")
	public Student saveStudent(@RequestBody Student student) {
		User user = student.getUser();
		String email = user.getEmail();
		if (userRepository.existsByEmail(email)) {
			System.out.println("Email Already exists");
		} else {
			// user.setActive(true);
			userRepository.save(user);
			return studentRepository.save(student);
		}
		return null;
	}

	@GetMapping("/getStudent")
	public List<Student> getStudent() {
		return studentRepository.findAll();
	}

	@DeleteMapping("/deleteuser/{user_id}")
	public void deleteStudent(@PathVariable int user_id) {
		User user = userRepository.findById(user_id).get();
		user.setActive(false);
		userRepository.save(user);
	}

	@PutMapping("/updateStudent/{student_id}")
	public Student updateStudent(@RequestBody Student student, @PathVariable int student_id) {
		if (studentRepository.existsById(student_id)) {
			
			Student updatedStudent =  studentRepository.findById(student_id).get();
			int user_id =  updatedStudent.getUser().getUser_id();
			
			student.setStudent_id(student_id);
			student.getUser().setUser_id(user_id);
			
			return studentRepository.save(student);
			
		} else {
			System.out.println("Data Not Found");
		}
		return null;
	}

	@PostMapping("/addteacher")
	public Teacher saveTeacher(@RequestBody Teacher teacher) {
		User user = teacher.getUser();
		String email = user.getEmail();
		if (userRepository.existsByEmail(email)) {
			System.out.println("Email Already exists");
		} else {

			userRepository.save(user);
			return teacherRepository.save(teacher);
		}
		return null;
	}

	@GetMapping("/getTeacher")
	public List<Teacher> getTeacher() {
		return teacherRepository.findAll();
	}

	@PutMapping("/updateTeacher/{teacher_id}")
	public Teacher updateTeacher(@RequestBody Teacher teacher, @PathVariable int teacher_id) {
		if (teacherRepository.existsById(teacher_id)) {
			
			Teacher updatedTeacher =  teacherRepository.findById(teacher_id).get();
			int user_id =  updatedTeacher.getUser().getUser_id();
			
			teacher.setTeacher_id(teacher_id);
			teacher.getUser().setUser_id(user_id);
			System.out.println(teacher);
			return teacherRepository.save(teacher);
			
		} else {
			System.out.println("Data Not Found");
		}
		return null;
	}
}

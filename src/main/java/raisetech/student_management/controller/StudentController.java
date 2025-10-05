package raisetech.student_management.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.service.StudentService;

@RestController
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/students")
  public List<Student> getStudents() {
    return  studentService.searchStudents();
  }

  @GetMapping("/student-courses")
  public List<StudentCourse> getStudentCourses() {
    return  studentService.searchStudentCourses();
  }

}

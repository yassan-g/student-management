package raisetech.student_management.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.service.StudentService;

@RestController
public class StudentController {

  private final StudentService studentService;
  private final StudentConverter studentConverter;

  public StudentController(StudentService studentService, StudentConverter studentConverter) {
    this.studentService = studentService;
    this.studentConverter = studentConverter;
  }

  @GetMapping("/students")
  public List<StudentDetail> getStudents() {
    List<Student> students = studentService.searchStudents();
    List<StudentCourse> studentCourses = studentService.searchStudentCourses();

    return studentConverter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/student-courses")
  public List<StudentCourse> getStudentCourses() {
    return  studentService.searchStudentCourses();
  }

}

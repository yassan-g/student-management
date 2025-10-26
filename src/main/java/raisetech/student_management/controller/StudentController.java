package raisetech.student_management.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.service.StudentService;

@Controller
public class StudentController {

  private final StudentService studentService;
  private final StudentConverter studentConverter;

  public StudentController(StudentService studentService, StudentConverter studentConverter) {
    this.studentService = studentService;
    this.studentConverter = studentConverter;
  }

  @GetMapping("/students")
  public String getStudents(Model model) {
    List<Student> students = studentService.searchStudents();
    List<StudentCourse> studentCourses = studentService.searchStudentCourses();

    model.addAttribute("students", studentConverter.convertStudentDetails(students, studentCourses));

    return "students.html";
  }

  @GetMapping("/student-courses")
  public List<StudentCourse> getStudentCourses() {
    return  studentService.searchStudentCourses();
  }

  @GetMapping("/new-student")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourses(Arrays.asList(new StudentCourse()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent.html";
  }

  @PostMapping("/register-student")
  public String resisterStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent.html";
    }
    studentService.registerStudent(studentDetail);
    return "redirect:/students";
  }

}

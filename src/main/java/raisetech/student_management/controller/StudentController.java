package raisetech.student_management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

  // 受講生情報一覧画面表示
  @GetMapping("/students")
  public String getStudents(Model model) {
    List<Student> students = studentService.searchStudents();
    List<StudentCourse> studentCourses = studentService.searchStudentCourses();
    model.addAttribute("students", studentConverter.convertStudentDetails(students, studentCourses));
    return "students.html";
  }

  // 受講生情報登録画面表示
  @GetMapping("/new-student")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    // 受講コース情報5件分の初期表示
    List<StudentCourse> initialCourses = new ArrayList<>();
    for (int i =0; i < 5; i++) {
      initialCourses.add(new StudentCourse());
    }
    studentDetail.setStudentCourses(initialCourses);
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent.html";
  }

  // 受講生情報登録処理
  @PostMapping("/register-student")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      return "registerStudent.html";
    }
    try {
      studentService.registerStudent(studentDetail);
      redirectAttributes.addFlashAttribute("message", "新規登録が完了しました。");
      return "redirect:/students";
    } catch (IllegalArgumentException e) {
      // 画面に戻すためにデータを渡す必要がある
      model.addAttribute("studentDetail", studentDetail);
      model.addAttribute("errorMessage", e.getMessage());
      return "registerStudent.html";
    }
  }

  // 受講生詳細画面表示
  @GetMapping("/student/{id}")
  public String getStudent(@PathVariable Integer id, Model model) {
    StudentDetail studentDetail = studentService.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent.html";
  }

  //  受講生詳細更新処理
  @PostMapping("/update-student")
  public String updateStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return "updateStudent.html";
    }
    studentService.updateStudent(studentDetail);
    redirectAttributes.addFlashAttribute("message", "更新が完了しました。");
    return "redirect:/students";
  }

  // 受講生情報削除処理
  @PostMapping("/students/{id}/delete")
  public String deleteStudent(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
      studentService.deleteStudent(id);
      redirectAttributes.addFlashAttribute("message", "削除が完了しました。");
    } catch (IllegalArgumentException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/students";
  }

  // 受講生情報復元処理
  @PostMapping("/students/{id}/restore")
  public String restoreStudent(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
      studentService.restoreStudent(id);
      redirectAttributes.addFlashAttribute("message", "復元が完了しました。");
    } catch (IllegalArgumentException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/students";
  }

}

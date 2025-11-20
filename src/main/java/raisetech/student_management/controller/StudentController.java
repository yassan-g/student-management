package raisetech.student_management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるController
 */
@RestController
@Validated
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * 受講生情報一覧検索
   *
   * @return 受講生情報一覧(全件)
   */
  @GetMapping("/students")
  public List<StudentDetail> getStudents() {
    return studentService.searchStudents();
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

  /**
   * 受講生詳細登録
   *
   * @param studentDetail 受講生詳細
   * @return 受講生詳細
   */
  @PostMapping("/student")
  public ResponseEntity<StudentDetail> registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
      StudentDetail saved = studentService.registerStudent(studentDetail);
      return ResponseEntity.ok(saved);
  }

  /**
   * 受講生詳細検索
   * 受講生と紐づく受講コース情報を取得
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Min(1) Integer id) {
    return studentService.searchStudent(id);
  }

  /**
   * 受講生詳細更新
   *
   * @param studentDetail 受講生詳細
   * @return 受講生詳細
   */
  @PutMapping("/student/{id}")
  public ResponseEntity<StudentDetail> updateStudent(@PathVariable @Min(1) Integer id, @Valid @RequestBody StudentDetail studentDetail) {
    studentDetail.getStudent().setId(id);
    StudentDetail updated = studentService.updateStudent(studentDetail);
    return ResponseEntity.ok(updated);
  }

  /**
   * 受講生情報削除
   *
   * @param id 受講生ID
   * @return Student
   */
  @PatchMapping("/students/{id}/delete")
  public ResponseEntity<Student> deleteStudent(@PathVariable @Min(1) Integer id) {
      Student deleted = studentService.deleteStudent(id);
    return ResponseEntity.ok(deleted);
  }

  /**
   * 受講生情報復元
   *
   * @param id 受講生ID
   * @return Student
   */
  @PatchMapping("/students/{id}/restore")
  public ResponseEntity<Student> restoreStudent(@PathVariable @Min(1) Integer id) {
      Student restored = studentService.restoreStudent(id);
    return ResponseEntity.ok(restored);
  }

}

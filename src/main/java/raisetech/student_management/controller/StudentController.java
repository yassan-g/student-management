package raisetech.student_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.data.Student;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるController
 */
@RestController
@Validated
@Tag(name = "Student API", description = "受講生の検索や登録、更新、削除、復元を行うAPI")
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
  @Operation(
      summary = "受講生情報一覧検索",
      description = "受講生情報を全件取得します",
      responses = {
          @ApiResponse(responseCode = "200", description = "正常に取得できました")
      })
  @GetMapping("/students")
  public List<StudentDetail> getStudents() {
    return studentService.searchStudents();
  }

  /**
   * 受講生詳細登録
   *
   * @param studentDetail 受講生詳細
   * @return 受講生詳細
   */
  @Operation(
      summary = "受講生詳細登録",
      description = "受講生詳細を新規登録します",
      responses = {
          @ApiResponse(responseCode = "200", description = "登録成功"),
          @ApiResponse(responseCode = "400", ref = "BadRequest")
      })
  @PostMapping("/student")
  public ResponseEntity<StudentDetail> registerStudent(
      @Valid @RequestBody StudentDetail studentDetail) {
    StudentDetail saved = studentService.registerStudent(studentDetail);
    return ResponseEntity.ok(saved);
  }

  /**
   * 受講生詳細検索 受講生と紐づく受講コース情報を取得
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @Operation(
      summary = "受講生詳細検索",
      description = "受講生と紐づく受講コース情報を取得します",
      responses = {
          @ApiResponse(responseCode = "200", description = "正常に取得できました"),
          @ApiResponse(responseCode = "404", ref = "NotFound")
      })
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
      @Parameter(description = "受講生ID", required = true) @PathVariable @Min(1) Integer id) {
    return studentService.searchStudent(id);
  }

  /**
   * 受講生詳細更新
   *
   * @param studentDetail 受講生詳細
   * @return 受講生詳細
   */
  @Operation(
      summary = "受講生詳細更新",
      description = "受講生詳細を更新します",
      responses = {
          @ApiResponse(responseCode = "200", description = "更新成功"),
          @ApiResponse(responseCode = "400", ref = "BadRequest")
      })
  @PutMapping("/student/{id}")
  public ResponseEntity<StudentDetail> updateStudent(
      @Parameter(description = "受講生ID", required = true) @PathVariable @Min(1) Integer id,
      @Valid @RequestBody StudentDetail studentDetail) {
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
  @Operation(
      summary = "受講生情報削除",
      description = "受講生情報を削除します（論理削除）",
      responses = {
          @ApiResponse(responseCode = "200", description = "削除成功"),
          @ApiResponse(responseCode = "404", ref = "NotFound")
      })
  @PatchMapping("/students/{id}/delete")
  public ResponseEntity<Student> deleteStudent(
      @Parameter(description = "受講生ID", required = true) @PathVariable @Min(1) Integer id) {
    Student deleted = studentService.deleteStudent(id);
    return ResponseEntity.ok(deleted);
  }

  /**
   * 受講生情報復元
   *
   * @param id 受講生ID
   * @return Student
   */
  @Operation(
      summary = "受講者情報復元",
      description = "削除済みの受講生情報を復元します",
      responses = {
          @ApiResponse(responseCode = "200", description = "復元成功"),
          @ApiResponse(responseCode = "404", ref = "NotFound")
      })
  @PatchMapping("/students/{id}/restore")
  public ResponseEntity<Student> restoreStudent(
      @Parameter(description = "受講生ID", required = true) @PathVariable @Min(1) Integer id) {
    Student restored = studentService.restoreStudent(id);
    return ResponseEntity.ok(restored);
  }

}

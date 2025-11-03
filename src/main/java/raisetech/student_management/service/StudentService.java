package raisetech.student_management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  // 受講生情報全件取得処理
  public List<Student> searchStudents() {
    return studentRepository.findAllStudents();

  }

  // 受講生情報と紐づく受講コース情報を取得処理
  public StudentDetail searchStudent(Integer id) {
    Student student = studentRepository.findStudentById(id);
    List<StudentCourse> studentCourses  = studentRepository.findCoursesByStudentId(id);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);
    return studentDetail;
  }

  // 受講コース情報全件取得処理
  public List<StudentCourse> searchStudentCourses() {
    return  studentRepository.findAllStudentCourse();
  }

  // 受講生詳細登録処理
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    studentRepository.registerStudent(studentDetail.getStudent());
    List<StudentCourse> studentCourses = studentDetail.getStudentCourses();
    boolean hasValidCourse = studentCourses.stream()
        .anyMatch(c -> c.getCourseName() != null && !c.getCourseName().isBlank());
    if (!hasValidCourse) {
      throw new IllegalArgumentException("最低1件の受講コース情報（コース名）が必要です。");
    }
    // 受講コース情報登録を行う
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      // 空欄チェック（必要最低限の項目が入力されているか）
      if (studentCourse.getCourseName() != null && !studentCourse.getCourseName().isBlank()) {
        studentCourse.setStudentId(studentDetail.getStudent().getId());
        studentCourse.setStartDate(LocalDate.now());
        studentCourse.setExpectedEndDate(calculateEndDate(studentCourse.getCourseName(),studentCourse.getStartDate()));
        studentRepository.registerStudentCourse(studentCourse);
      }
    }
  }

  // 受講生詳細更新処理
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    studentRepository.updateStudent(studentDetail.getStudent());
    // 受講コース情報更新を行う
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      // 終了予定日を再計算
      LocalDate newExpectedEndDate = calculateEndDate(studentCourse.getCourseName(),studentCourse.getStartDate());
      studentCourse.setExpectedEndDate(newExpectedEndDate);
      studentRepository.updateStudentCourse(studentCourse);
    }
  }

  // 受講コース情報に応じて終了予定日を再計算する処理
  public LocalDate calculateEndDate(String courseName, LocalDate startDate) {
    switch (courseName) {
      case "HTML/CSS/JavaScriptコース":
      case "SQLコース":
        return startDate.plusMonths(2);
      case "AWSコース":
        return startDate.plusMonths(3);
      case "Pythonコース":
        return startDate.plusMonths(4);
      case "Javaコース":
        return startDate.plusMonths(6);
      default:
        return startDate;
    }
  }

  // 受講生情報削除処理
  @Transactional
  public void deleteStudent(Integer id) {
    int deleted = studentRepository.logicalDeleteStudent(id);
    if (deleted == 0) {
      throw new IllegalArgumentException("指定されたIDの受講生が存在しません：" + id);
    }
  }

  // 受講生情報復元処理
  @Transactional
  public void restoreStudent(Integer id) {
    int restored = studentRepository.restoreStudent(id);
    if (restored == 0) {
      throw new IllegalArgumentException("指定されたIDの受講生が存在しません：" + id);
    }
  }

}

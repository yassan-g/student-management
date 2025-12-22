package raisetech.student_management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.exception.StudentNotFoundException;
import raisetech.student_management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うService
 * 受講生情報の検索や登録、更新処理などを行う
 */
@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final StudentConverter studentConverter;

  public StudentService(StudentRepository studentRepository, StudentConverter studentConverter) {
    this.studentRepository = studentRepository;
    this.studentConverter = studentConverter;
  }

  /**
   * 受講生情報一覧検索
   *
   * @return 受講生情報一覧(全件)
   */
  public List<StudentDetail> searchStudents() {
    List<Student> students = studentRepository.findAllStudents();
    List<StudentCourse> studentCourses = studentRepository.findAllStudentCourse();
    return studentConverter.convertStudentDetails(students, studentCourses);
  }

  /**
   * 受講生詳細検索
   * 受講生と紐づく受講コース情報を取得
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(Integer id) {
    Student student = studentRepository.findStudentById(id);
    if (student == null) {
      throw new StudentNotFoundException("指定されたIDの受講生が存在しません：id=" + id);
    }
    List<StudentCourse> studentCourses  = studentRepository.findCoursesByStudentId(id);
    return new StudentDetail(student,studentCourses);
  }

  /**
   * 受講生詳細登録
   *
   * @param studentDetail 受講生詳細
   * @return 受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    studentRepository.registerStudent(studentDetail.getStudent());
    // 受講コース情報登録を行う
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
        studentCourse.setStudentId(studentDetail.getStudent().getId());
        studentCourse.setStartDate(LocalDate.now());
        studentCourse.setExpectedEndDate(calculateEndDate(studentCourse.getCourseName(),studentCourse.getStartDate()));
        studentRepository.registerStudentCourse(studentCourse);
    }
    return studentDetail;
  }

  /**
   * 受講生詳細更新
   *
   * @param studentDetail 受講生詳細
   * @return 受講生詳細
   */
  @Transactional
  public StudentDetail updateStudent(StudentDetail studentDetail) {
    studentRepository.updateStudent(studentDetail.getStudent());
    // 受講コース情報更新を行う
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      // 終了予定日を再計算
      LocalDate newExpectedEndDate = calculateEndDate(studentCourse.getCourseName(),studentCourse.getStartDate());
      studentCourse.setExpectedEndDate(newExpectedEndDate);
      studentRepository.updateStudentCourse(studentCourse);
    }
    return studentDetail;
  }

  /**
   * 受講コース情報に応じて終了予定日を再計算する処理
   *
   * @param courseName コース名
   * @param startDate 開始日
   * @return 終了予定日
   */
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

  /**
   * 受講生情報削除
   *
   * @param id 受講生ID
   * @return Student
   */
  @Transactional
  public Student deleteStudent(Integer id) {
    int deletedCount = studentRepository.logicalDeleteStudent(id);
    if (deletedCount == 0) {
      throw new StudentNotFoundException("指定されたIDの受講生が存在しません：id=" + id);
    }
    return studentRepository.findStudentById(id);
  }

  /**
   * 受講生情報復元
   *
   * @param id 受講生ID
   * @return Student
   */
  @Transactional
  public Student restoreStudent(Integer id) {
    int restoredCount = studentRepository.restoreStudent(id);
    if (restoredCount == 0) {
      throw new StudentNotFoundException("指定されたIDの受講生が存在しません：id=" + id);
    }
    return  studentRepository.findStudentById(id);
  }

}

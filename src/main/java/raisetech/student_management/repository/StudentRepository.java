package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;

/**
 * 受講生テーブルと受講コース情報テーブルと紐づくRepository
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生情報を全件検索する
   *
   * @return Studentのリスト
   */
  List<Student> findAllStudents();

  /**
   * 受講生情報を検索する
   *
   * @param id 受講生ID
   * @return Student
   */
  Student findStudentById(Integer id);

  /**
   * 受講コース情報を全件検索する
   *
   * @return StudentCourseのリスト
   */
  List<StudentCourse> findAllStudentCourse();

  /**
   * 受講生IDに紐づく受講コース情報を検索する
   *
   * @param studentId 受講生ID
   * @return StudentCourseのリスト
   */
  List<StudentCourse> findCoursesByStudentId(Integer studentId);

  /**
   * 受講生情報を登録する
   *
   * @param student 受講生情報
   */
  void registerStudent(Student student);

  /**
   * 受講コース情報を登録する
   *
   * @param studentCourse 受講コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報を更新する
   *
   * @param student 受講生情報
   */
  void updateStudent(Student student);

  /**
   * 受講コース情報を更新する
   *
   * @param studentCourse 受講コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報を論理削除する
   *
   * @param id 受講生ID
   * @return 削除された件数（通常は0または1）
   */
  int logicalDeleteStudent(Integer id);

  /**
   * 受講生情報を復元する
   *
   * @param id 受講生ID
   * @return 復元された件数（通常は0または1）
   */
  int restoreStudent(Integer id);

}

package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  @Select("SELECT * FROM students")
  List<Student> findAllStudents();

  /**
   * 受講生情報を検索する
   *
   * @param id 受講生ID
   * @return Student
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(Integer id);

  /**
   * 受講コース情報を全件検索する
   *
   * @return StudentCourseのリスト
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> findAllStudentCourse();

  /**
   *受講生IDに紐づく受講コース情報を検索する
   *
   * @param studentId 受講生ID
   * @return StudentCourseのリスト
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> findCoursesByStudentId(Integer studentId);

  /**
   *  受講生情報を登録する
   *
   * @param student 受講生情報
   */
  @Insert("INSERT INTO students(name, name_kana, nickname, email, area, birth_date, gender, remark, deleted)"
      + "VALUES(#{name}, #{nameKana}, #{nickname}, #{email}, #{area}, #{birthDate}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * 受講コース情報を登録する
   *
   * @param studentCourse 受講コース情報
   */
  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, expected_end_date)"
      + "VALUES(#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報を更新する
   *
   * @param student 受講生情報
   */
  @Update("UPDATE students SET name = #{name}, name_kana = #{nameKana}, nickname = #{nickname}, email = #{email},"
      + "area = #{area}, birth_date = #{birthDate}, gender = #{gender}, remark = #{remark}, deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);

  /**
   * 受講コース情報を更新する
   *
   * @param studentCourse 受講コース情報
   */
  @Update("UPDATE students_courses SET course_name = #{courseName}, expected_end_date = #{expectedEndDate} WHERE id = #{id} AND student_id = #{studentId}")
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報を論理削除する
   *
   * @param id 受講生ID
   * @return 削除された件数（通常は0または1）
   */
  @Update("UPDATE students SET deleted = true WHERE id = #{id}")
  int logicalDeleteStudent(Integer id);

  /**
   * 受講生情報を復元する
   *
   * @param id 受講生ID
   * @return 復元された件数（通常は0または1）
   */
  @Update("UPDATE students SET deleted = false WHERE id = #{id}")
  int restoreStudent(Integer id);

}

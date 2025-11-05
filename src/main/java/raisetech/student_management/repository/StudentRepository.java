package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;

@Mapper
public interface StudentRepository {

  /**
   * 受講生情報を全件取得する
   *
   * @return Studentのリスト
   */
  @Select("SELECT * FROM students")
  List<Student> findAllStudents();

  // 受講生情報を1件取得する
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(Integer id);

  /**
   * 受講コース情報を全件取得する
   *
   * @return StudentCourseのリスト
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> findAllStudentCourse();

  // 受講コース情報を複数件取得する
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> findCoursesByStudentId(Integer studentId);

  // 受講生情報を登録する
  @Insert("INSERT INTO students(name, name_kana, nickname, email, area, birth_date, gender, remark, deleted)"
      + "VALUES(#{name}, #{nameKana}, #{nickname}, #{email}, #{area}, #{birthDate}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  // 受講コース情報を登録する
  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, expected_end_date)"
      + "VALUES(#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  // 受講生情報を更新する
  @Update("UPDATE students SET name = #{name}, name_kana = #{nameKana}, nickname = #{nickname}, email = #{email},"
      + "area = #{area}, birth_date = #{birthDate}, gender = #{gender}, remark = #{remark}, deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);

  // 受講コース情報を更新する
  @Update("UPDATE students_courses SET course_name = #{courseName}, expected_end_date = #{expectedEndDate} WHERE id = #{id} AND student_id = #{studentId}")
  void updateStudentCourse(StudentCourse studentCourse);

  // 受講生情報を削除する
  @Update("UPDATE students SET is_deleted = TRUE WHERE id = #{id}")
  int logicalDeleteStudent(Integer id);

  // 受講生情報を復元する
  @Update("UPDATE students SET is_deleted = FALSE WHERE id = #{id}")
  int restoreStudent(Integer id);

}

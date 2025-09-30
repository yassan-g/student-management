package raisetech.student_management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  /**
   * 受講生情報を全件取得する
   *
   * @return Studentのリスト
   */
  @Select("SELECT * FROM students")
  List<Student> findAllStudents();

  /**
   * 受講生コース情報を全件取得する
   *
   * @return StudentCourseのリスト
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> findAllStudentCourses();

}

package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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

  /**
   * 受講生コース情報を全件取得する
   *
   * @return StudentCourseのリスト
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> findAllStudentCourses();

  @Insert("INSERT INTO students(name, name_kana, nickname, email, area, birth_date, gender, remark, is_deleted)"
      + "VALUES(#{name}, #{nameKana}, #{nickname}, #{email}, #{area}, #{birthDate}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, expected_end_date)"
      + "VALUES(#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentCourse studentCourse);

}

package raisetech.student_management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;

/**
 * 受講生詳細を受講生情報や受講コース情報、もしくはその逆の変換を行うクラス
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講コース情報をマッピングする
   * 受講コース情報は受講生に対して複数存在するのでループを回して受講生詳細を組み立てる
   *
   * @param students 受講生情報一覧
   * @param studentCourses 受講コース情報のリスト
   * @return 受講生詳細のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentCourse> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourses = studentCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}

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

  public List<Student> searchStudents() {
    return studentRepository.findAllStudents();
  }

  public List<StudentCourse> searchStudentCourses() {
    return  studentRepository.findAllStudentCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    studentRepository.registerStudent(studentDetail.getStudent());
    // コース情報登録を行う
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setStartDate(LocalDate.now());
      switch (studentCourse.getCourseName()) {
        case "HTML/CSS/JavaScriptコース":
        case "SQLコース":
          studentCourse.setExpectedEndDate(LocalDate.now().plusMonths(2));
          break;
        case "AWSコース":
          studentCourse.setExpectedEndDate(LocalDate.now().plusMonths(3));
          break;
        case "Pythonコース":
          studentCourse.setExpectedEndDate(LocalDate.now().plusMonths(4));
          break;
        case "Javaコース":
          studentCourse.setExpectedEndDate(LocalDate.now().plusMonths(6));
          break;
      }

      studentRepository.registerStudentCourses(studentCourse);
    }
  }

}

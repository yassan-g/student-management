package raisetech.student_management.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> searchStudents() {
    // 年齢が30代の人のみを抽出する
    return studentRepository.findAllStudents().stream()
        .filter(s -> {
              int age = Period.between(s.getBirthDate(), LocalDate.now()).getYears();
              return age >= 30 && age < 40;
        })
        .collect(Collectors.toList());
  }

  public List<StudentCourse> searchStudentCourses() {
    //「Javaコース」のコース情報のみを抽出する
    return  studentRepository.findAllStudentCourses().stream()
        .filter(c -> "Javaコース".equals(c.getCourseName()))
        .collect(Collectors.toList());
  }

}

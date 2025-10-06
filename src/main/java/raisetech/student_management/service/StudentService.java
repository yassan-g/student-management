package raisetech.student_management.service;

import java.util.List;
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
    return studentRepository.findAllStudents();
  }

  public List<StudentCourse> searchStudentCourses() {
    return  studentRepository.findAllStudentCourses();
  }

}

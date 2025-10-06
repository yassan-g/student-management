package raisetech.student_management.domain;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
public class StudentDetail {
  private Student student;
  private List<StudentCourse> studentCourses;
}

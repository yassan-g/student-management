package raisetech.student_management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "受講生詳細情報(受講生情報＋受講コース)")
public class StudentDetail {

  @Schema(description = "受講生情報")
  @Valid
  private Student student;

  @Schema(description = "受講コース")
  @Valid
  private List<StudentCourse> studentCourses;
}

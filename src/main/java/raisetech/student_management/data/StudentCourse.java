package raisetech.student_management.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentCourse {

  private Integer id;

  private Integer studentId;

  @NotBlank(message = "コース名は必須です")
  @Size(max = 50, message = "コース名は50文字以内で入力してください")
  private String courseName;

  private LocalDate startDate;

  private LocalDate expectedEndDate;
}

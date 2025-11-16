package raisetech.student_management.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "受講生IDは必須です")
  private Integer studentId;

  @NotBlank(message = "コース名は必須です")
  @Size(max = 50, message = "コース名は50文字以内で入力してください")
  private String courseName;

  @NotNull(message = "開始日は必須です")
  private LocalDate startDate;

  private LocalDate expectedEndDate;
}

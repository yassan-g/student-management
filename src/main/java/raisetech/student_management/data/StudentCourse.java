package raisetech.student_management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "受講コース")
public class StudentCourse {

  @Schema(description = "コースID（登録と更新時は不要）", example = "1")
  private Integer id;

  @Schema(description = "受講生ID（登録時は不要。更新時はURLのパスに指定）", example = "10")
  private Integer studentId;

  @Schema(description = "コース名", example = "Javaコース")
  @NotBlank(message = "コース名は必須です")
  @Size(max = 50, message = "コース名は50文字以内で入力してください")
  private String courseName;

  @Schema(description = "受講開始日(yyyy-MM-dd)", example = "2025-04-01")
  private LocalDate startDate;

  @Schema(description = "受講終了予定日(yyyy-MM-dd)", example = "2025-10-01")
  private LocalDate expectedEndDate;
}

package raisetech.student_management.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class Student {
  private Integer id;
  private String name;
  private String nameKana;
  private String nickname;
  private String email;
  private String area;
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  private LocalDate birthDate;
  private String gender;
  private String remark;
  private boolean isDeleted;
}

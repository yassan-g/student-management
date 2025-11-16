package raisetech.student_management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "名前は必須です")
  @Size(max = 50, message = "名前は50文字以内で入力してください")
  private String name;

  @NotBlank(message = "名前(カナ)は必須です")
  @Size(max = 50, message = "名前(カナ)は50文字以内で入力してください")
  private String nameKana;

  @Size(max = 50, message = "ニックネームは50文字以内で入力してください")
  private String nickname;

  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスの形式が不正です")
  @Size(max = 50, message = "メールアドレスは50文字以内で入力してください")
  private String email;

  @Size(max = 50)
  private String area;

  @Past(message = "生年月日は過去の日付を入力してください")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  private LocalDate birthDate;

  @Size(max = 10)
  private String gender;

  @Size(max = 255, message = "備考は255文字以内で入力してください")
  private String remark;

  private boolean deleted;
}

package raisetech.student_management.data;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "受講生情報")
public class Student {

  @Schema(description = "受講生ID（登録時は不要。更新時はURLのパスに指定）", example = "10")
  private Integer id;

  @Schema(description = "受講生氏名", example = "山田太郎")
  @NotBlank(message = "名前は必須です")
  @Size(max = 50, message = "名前は50文字以内で入力してください")
  private String name;

  @Schema(description = "受講生氏名(カナ)", example = "ヤマダタロウ")
  @NotBlank(message = "名前(カナ)は必須です")
  @Size(max = 50, message = "名前(カナ)は50文字以内で入力してください")
  private String nameKana;

  @Schema(description = "ニックネーム", example = "タロちゃん")
  @Size(max = 50, message = "ニックネームは50文字以内で入力してください")
  private String nickname;

  @Schema(description = "メールアドレス", example = "taro@example.com")
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスの形式が不正です")
  @Size(max = 50, message = "メールアドレスは50文字以内で入力してください")
  private String email;

  @Schema(description = "地域", example = "東京都足立区")
  @Size(max = 50)
  private String area;

  @Schema(description = "生年月日", example = "1990-03-01")
  @Past(message = "生年月日は過去の日付を入力してください")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  private LocalDate birthDate;

  @Schema(description = "性別", example = "男性")
  @Size(max = 10)
  private String gender;

  @Schema(description = "備考", example = "Javaコースを受講中")
  @Size(max = 255, message = "備考は255文字以内で入力してください")
  private String remark;

  @Schema(description = "削除フラグ", example = "false")
  private boolean deleted;
}

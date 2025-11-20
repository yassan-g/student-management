package raisetech.student_management.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * APIのエラー応答を表現するクラス
 * タイムスタンプ、HTTPステータスコード、エラーメッセージ、
 * およびフィールドごとのバリデーションエラー詳細を保持する
 */
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class ErrorResponse {

  /** エラー発生時刻 */
  private LocalDateTime timestamp;

  /** HTTPステータスコード（数値） */
  private int status;

  /** HTTPステータスの理由区（例: "Bad Request"） */
  private String error;

  /** エラーメッセージ（例: "入力値にエラーがあります"） */
  private String message;

  /** フィールドごとのエラーメッセージ一覧（キー: フィールド名, 値: エラーメッセージリスト） */
  private Map<String, List<String>> fieldErrors;

  /**
   * ErrorResponse を生成する
   *
   * @param status HTTPステータス
   * @param message エラーメッセージ
   * @param fieldErrors フィールドごとのエラーメッセージ一覧（null可）
   */
  public ErrorResponse(HttpStatus status, String message, Map<String, List<String>> fieldErrors) {
    this.timestamp = LocalDateTime.now();
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.message = message;
    this.fieldErrors = fieldErrors;
  }

}

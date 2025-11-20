package raisetech.student_management.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * アプリケーション全体の例外を一元的にハンドリングするクラス
 * 各種例外をキャッチしてHTTP ステータスコードとエラーレスポンスを返す
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 入力チェックのバリデーションエラーをハンドリングするメソッド
   * フィールドごとのエラーメッセージを収集し、HTTP 400（Bad Request）と共に返す
   *
   * @param ex バリデーションエラー情報を保持する例外
   * @return エラーメッセージを含むレスポンス（HTTP 400 Bad Request）
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, List<String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
    log.info("Validation error: {}", fieldErrors);
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "入力値にエラーがあります", fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * 受講生が存在しないエラーをハンドリングするメソッド
   * ログに警告を出力し、HTTP 404 (Not Found) とエラーメッセージを返す
   *
   * @param ex 受講生が存在しないエラー情報を保持する例外
   * @return エラーメッセージを含むレスポンス（HTTP 404 Not Found）
   */
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
    log.warn("Student not found: {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * その他の例外をハンドリングするメソッド
   *
   * @param ex エラー情報を保持する例外
   * @return 予期しないエラーの汎用的なレスポンス（HTTP 500 Internal Server Error）
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    log.error("Unexpected error occurred", ex);
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "予期しないエラーが発生しました", null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

}

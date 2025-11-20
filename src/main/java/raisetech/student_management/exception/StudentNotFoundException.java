package raisetech.student_management.exception;

/**
 * 受講生が存在しない場合にスローされる例外クラス
 * この例外は、指定された受講生IDに対応する受講生がデータベース等に存在しない場合に発生する
 */
public class StudentNotFoundException extends RuntimeException {

  /**
   * 例外を生成するコンストラクタ
   *
   * @param message エラーメッセージ
   */
  public StudentNotFoundException(String message) {
    super(message);
  }

}

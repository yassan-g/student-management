package raisetech.student_management.exception;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAIの共通エラーレスポンスを定義する設定クラス
 */
@Configuration
public class OpenApiErrorConfig {

  /**
   * 共通エラーレスポンスをOpenAPIに登録するカスタマイザBeanを生成
   *
   * @return OpenApiCustomizer 共通レスポンスを追加するカスタマイザ
   */
  @Bean
  public OpenApiCustomizer globalResponses() {
    return openApi -> {
      openApi.getComponents().addSchemas("ErrorResponse",
          ModelConverters.getInstance().read(ErrorResponse.class).get("ErrorResponse"));
      openApi.getComponents().addResponses("BadRequest",
          buildErrorResponse("入力値にエラーがあります", 400, "Bad Request"));
      openApi.getComponents().addResponses("NotFound",
          buildErrorResponse("指定されたIDの受講生が存在しません", 404, "Not Found"));
    };
  }

  /**
   * 共通エラーレスポンスを生成するヘルパーメソッド
   *
   * @param description レスポンスの説明
   * @return ApiResponseErrorResponse スキーマを参照する共通レスポンス
   */
  private ApiResponse buildErrorResponse(String description, int status, String error) {
    String exampleJson = """
    {
      "timestamp": "2025-11-30T12:34:27.844Z",
      "status": %d,
      "error": "%s",
      "message": "%s"
    }""".formatted(status, error, description);

    Example example = new Example().value(exampleJson);

    return new ApiResponse()
        .description(description)
        .content(new Content()
            .addMediaType("application/json",
                new MediaType()
                    .schema(new io.swagger.v3.oas.models.media.Schema<>().$ref("#/components/schemas/ErrorResponse"))
                    .addExamples("default", example)));
  }

}

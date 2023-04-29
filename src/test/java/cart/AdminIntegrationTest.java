package cart;

import static org.hamcrest.core.Is.is;

import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminIntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/admin 으로 get 요청을 보내면 ok 상태코드를 반환한다")
    void adminTest() {
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/admin/product에 정상적인 POST request를 전송하면 created 상태코드를 반환한다")
    void createTest() {
        ProductRequest productRequest = new ProductRequest("테스트", 1000, "http://testtest");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/admin/product")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("/admin/product에 비정상적인 POST request를 전송하면 bad_request를 반환하고 에러 메시지의 size는 비정상적인 파라미터의 개수와 같다")
    void createExceptionTest() {
        ProductRequest productRequest = new ProductRequest("", null, "");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/admin/product")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(3));
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/admin/product에 정상적인 PUT request를 전송하면 ok 상태코드를 반환한다")
    void updateTest() {
        ProductRequest productRequest = new ProductRequest("테스트", 1000, "http://testtest");
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/admin/product");

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(
                1L,
                "테스트",
                10000,
                "http://testtest"
        );

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when()
                .put("/admin/product")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/admin/product에 비정상적인 PUT request를 전송하면 bad_request를 반환하고 에러 메시지의 size는 비정상적인 파라미터의 개수와 같다")
    void updateExceptionTest() {
        ProductRequest productRequest = new ProductRequest("테스트", 1000, "http://testtest");
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/admin/product");

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(null, "", null, "");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when()
                .put("/admin/product")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(4));
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/admin/delete에 정상적인 request를 전송하면 no_content 상태코드를 반환한다")
    void deleteTest() {
        ProductRequest productRequest = new ProductRequest("테스트", 1000, "http://testtest");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/admin/product");

        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/admin/product?id=1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
package cart;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminIntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/admin으로 get 요청을 보내면 ok 상태코드를 반환한다")
    void adminTest() {
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}

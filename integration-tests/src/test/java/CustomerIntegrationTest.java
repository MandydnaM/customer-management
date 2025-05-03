import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomerIntegrationTest {
  @BeforeAll
  static void setUp(){
    RestAssured.baseURI = "http://localhost:4004";
  }

  @Test
  public void shouldReturnCustomersWithValidToken () {
    String loginPayload = """
          {
            "email": "testuser@test.com",
            "password": "password123"
          }
        """;

    String token = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .get("token");

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/customers")
        .then()
        .statusCode(200)
        .body("customers", notNullValue());
  }
}

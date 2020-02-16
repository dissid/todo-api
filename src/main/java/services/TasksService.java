package services;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class TasksService {

  private RequestSpecification setup() {
    return RestAssured
            .given()
            .filters(new ResponseLoggingFilter(), new RequestLoggingFilter())
            .contentType(JSON);
  }

  public Response get() {
    return setup()
            .when()
            .get("/todo/api/v1.0/tasks");
  }

  public Response create(String taskPayLoad) {
    return setup()
            .body(taskPayLoad)
            .when()
            .post("/todo/api/v1.0/tasks");
  }

  public Response update(String taskId, String taskPayLoad) {
    return setup()
            .body(taskPayLoad)
            .when()
            .put("/todo/api/v1.0/tasks/" + taskId);
  }

  public Response delete(String taskId) {
    return setup()
            .when()
            .delete("/todo/api/v1.0/tasks/" + taskId);
  }
}

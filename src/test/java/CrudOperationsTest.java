import org.junit.jupiter.api.Test;
import services.TasksService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CrudOperationsTest extends BaseTest {

  private TasksService tasksService = new TasksService();
  String taskPayLoad = "{\"title\": \"Read a book\"}";

  @Test
  public void getTasks() {
    tasksService.get()
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body(matchesJsonSchemaInClasspath("TaskJsonSchema.json"));
  }

  @Test
  public void createTask() {
    tasksService.create(taskPayLoad)
            .then()
            .assertThat()
            .statusCode(201)
            .and()
            .body("task.title", equalTo("Read a book"))
            .body(matchesJsonSchemaInClasspath("CreateTaskJsonSchema.json"));
  }

  @Test
  public void updateTask() {
    String uri = tasksService.create(taskPayLoad)
            .then()
            .assertThat()
            .statusCode(201)
            .extract().response().body().jsonPath().get("task.uri");

    String updatedDone = "{\"done\":true}";
    tasksService.update(uri.substring(42), updatedDone)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("task.done", equalTo(true));
  }

  @Test
  public void deleteTask() {
    String uri = tasksService.create(taskPayLoad)
            .then()
            .assertThat()
            .statusCode(201)
            .extract().response().body().jsonPath().get("task.uri");

    tasksService.delete(uri.substring(42))
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("result", equalTo(true));
  }

}

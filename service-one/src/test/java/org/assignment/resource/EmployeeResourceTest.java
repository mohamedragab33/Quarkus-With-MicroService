package org.assignment.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.assignment.dto.EmployeeReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
class EmployeeResourceTest {

 private static String employeeId;

 @BeforeEach
 public void setUp() {
  EmployeeReq employeeReq = new EmployeeReq();
  employeeReq.setName("John Doe");
  employeeReq.setDepartmentId("66fbc1cf7b93374adf0e7c8a");

  employeeId = given()
          .contentType(ContentType.JSON)
          .body(employeeReq)
          .when().post("/employees")
          .then()
          .statusCode(200)
          .body("name", equalTo("John Doe"))
          .extract().path("id");
 }

 @Test
 void testGetEmployeeById() {
  System.out.println("Testing with employeeId: " + employeeId);

  given()
          .when().get("/employees/" + employeeId)
          .then()
          .statusCode(200)
          .body("id", equalTo(employeeId))
          .body("name", equalTo("John Doe"));
 }

 @Test
 void testGetAllEmployees() {
  given()
          .when().get("/employees")
          .then()
          .statusCode(200)
          .body("$.size()", greaterThan(0));
 }

 @Test
 void testUpdateEmployee() {
  Map<String, String> updatedReq = new HashMap<>();
  updatedReq.put("name", "Jane Doe");

  given()
          .contentType(ContentType.JSON)
          .body(updatedReq)
          .when().put("/employees/" + employeeId)
          .then()
          .statusCode(200)
          .body("id", equalTo(employeeId))
          .body("name", equalTo("Jane Doe"));
 }

 @Test
 void testDeleteEmployee() {
  given()
          .when().delete("/employees/" + employeeId)
          .then()
          .statusCode(200)
          .body(equalTo("true"));

  given()
          .when().get("/employees/" + employeeId)
          .then()
          .statusCode(404);
 }
}

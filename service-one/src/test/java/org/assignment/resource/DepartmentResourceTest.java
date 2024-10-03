package org.assignment.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.assignment.dto.DepartmentReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
class DepartmentResourceTest {

 private String departmentId;

 @BeforeEach
 public void setUp() {
  departmentId = createDepartment();
 }

 private String createDepartment() {
  DepartmentReq departmentReq = new DepartmentReq();
  departmentReq.setName("Finance");

  return given()
          .contentType(ContentType.JSON)
          .body(departmentReq)
          .when().post("/departments")
          .then()
          .statusCode(200)
          .body("name", equalTo("Finance"))
          .extract().path("id");
 }

 @Test
 void testGetDepartmentById() {
  System.out.println("Testing with departmentId: " + departmentId);

  given()
          .when().get("/departments/" + departmentId)
          .then()
          .statusCode(200) // Check if the id is valid
          .body("id", equalTo(departmentId))
          .body("name", equalTo("Finance"));
 }

 @Test
 void testGetAllDepartments() {
  given()
          .when().get("/departments")
          .then()
          .statusCode(200)
          .body("$.size()", greaterThan(0));
 }

 @Test
 void testUpdateDepartment() {
  Map<String, String> updatedReq = new HashMap<>();
  updatedReq.put("name", "Human Resources");

  given()
          .contentType(ContentType.JSON)
          .body(updatedReq)
          .when().put("/departments/" + departmentId)
          .then()
          .statusCode(200)
          .body("id", equalTo(departmentId))
          .body("name", equalTo("Human Resources"));
 }

}

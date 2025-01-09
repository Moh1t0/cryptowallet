package com.javaacademy.cryptowallet;

import com.javaacademy.cryptowallet.dto.ResetPasswordDto;
import com.javaacademy.cryptowallet.entity.UserEntity;
import com.javaacademy.cryptowallet.storage.UserStorageTestImpl;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private UserStorageTestImpl userStorage;

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/user")
            .setPort(8008)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Создание пользователя")
    public void createUserSuccess() {
        UserEntity userEntity = UserEntity.builder()
                .login("exotic")
                .email("test@mail.ru")
                .password("123123")
                .build();

        given(requestSpecification)
                .body(userEntity)
                .post("/signup")
                .then()
                .spec(responseSpecification)
                .statusCode(201)
                .body("login", equalTo("exotic"))
                .body("email", equalTo("test@mail.ru"))
                .body("password", equalTo("123123"));
    }


    @Test
    @DisplayName("Смена пароля успешно")
    public void resetPasswordSuccess() {
        ResetPasswordDto resetPasswordDto = ResetPasswordDto.builder()
                .login("testuser")
                .oldPassword("oldPassword")
                .newPassword("newPassword")
                .build();

        given(requestSpecification)
                .body(resetPasswordDto)
                .post("/reset-password")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .body("login", equalTo("testuser"))
                .body("old_password", equalTo("oldPassword"))
                .body("new_password", equalTo("newPassword"));
    }
}

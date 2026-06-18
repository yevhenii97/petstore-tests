package com.taf.tests;

import com.taf.restapi.clients.PetClient;
import com.taf.restapi.helper.SchemaValidator;
import com.taf.restapi.models.*;
import com.taf.testdata.CreatePetDataProvider;
import com.taf.testdata.TestDataFactory;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Pet API")
@Feature("Create pet")
@Slf4j
public class CreatePetTests extends BaseTest {

    @Autowired
    private PetClient petClient;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that pet can be created with correct data")
    public void checkThatPetIsBeingCreatedSuccessfully() {
        SoftAssertions softAssertions = new SoftAssertions();
        PetDTO requestBody = TestDataFactory.getBasicPetDto();

        log.info("Request body {}", requestBody);

        ApiResult<PetDTO> response = petClient.createPet(requestBody, PetDTO.class);

//      status code is not correct, because should be 201 Created
        assertThat(response.getStatus())
                .as("Check status code")
                .isEqualTo(200);

        SchemaValidator.validate(response, "schemas/pet-schema.json");

        PetDTO actualPet = response.getBody();

        softAssertions.assertThat(actualPet.getId())
                .as("Check id")
                .isEqualTo(requestBody.getId());

        softAssertions.assertThat(actualPet.getName())
                .as("Check name")
                .isEqualTo(requestBody.getName());

        softAssertions.assertThat(actualPet.getStatus())
                .as("Check status")
                .isEqualTo(requestBody.getStatus());

        softAssertions.assertAll();
    }

    @Test(dataProvider = "getBodyWithOutRequiredFields", dataProviderClass = CreatePetDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that pet cannot be created without required fields. Known issue: PetStore accepts invalid payloads.")
    public void checkThatPetIsNotBeingCreatedWithoutRequiredFields(PetDTO requestBody, String testDescription) {
        log.info("Test description {}", testDescription);

        ApiResult<Object> response = petClient.createPet(requestBody, Object.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isEqualTo(400);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that pet cannot be created with empty JSON object")
    public void checkThatPetIsNotCreatedWithEmptyBody() {
        PetDTO requestBody = PetDTO.builder().build();
        ApiResult<ErrorResponse> response = petClient.createPet(requestBody, ErrorResponse.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isEqualTo(400);
    }
}

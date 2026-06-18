package com.taf.tests;

import com.taf.restapi.clients.PetClient;
import com.taf.restapi.helper.SchemaValidator;
import com.taf.restapi.models.ApiResult;
import com.taf.restapi.models.ErrorResponse;
import com.taf.restapi.models.PetDTO;
import com.taf.restapi.services.PetService;
import com.taf.testdata.GetPetByIdDataProvider;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Pet API")
@Feature("Get pet by id")
@Slf4j
public class GetPetByIdTests extends BaseTest{

    @Autowired
    private PetClient petClient;
    @Autowired
    private PetService petService;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that get pet by id successfully")
    public void checkThatPetIsBeingGotSuccessfully() {
        SoftAssertions softAssertions = new SoftAssertions();

        PetDTO expectedPet = petService.getCreatedPet();

        ApiResult<PetDTO> response = petClient.getPetById(String.valueOf(expectedPet.getId()), PetDTO.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isEqualTo(200);

        SchemaValidator.validate(response, "schemas/pet-schema.json");

        PetDTO actualPet = response.getBody();

        softAssertions.assertThat(actualPet.getId())
                .as("Check id")
                .isEqualTo(expectedPet.getId());

        softAssertions.assertThat(actualPet.getName())
                .as("Check name")
                .isEqualTo(expectedPet.getName());

        softAssertions.assertThat(actualPet.getStatus())
                .as("Check status")
                .isEqualTo(expectedPet.getStatus());

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that we do not get pet by id because such id does not exist")
    public void checkThatPetIsNotBeingGot() {

//      if only I had access to the DB, I would get last id and +1, instead of just put 000000
        ApiResult<ErrorResponse> response = petClient.getPetById("000000", ErrorResponse.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isEqualTo(404);

        SchemaValidator.validate(response, "schemas/error-response-schema.json");

        assertThat(response.getBody().getMessage())
                .as("Check error message")
                .isEqualTo("Pet not found");

    }

    @Test(dataProvider = "invalidPetIds", dataProviderClass = GetPetByIdDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that pet cannot be received by invalid id")
    public void checkThatPetIsNotBeingGotWithInvalidId(String id, String testDescription) {

        log.info("Test description {}", testDescription);

        ApiResult<ErrorResponse> response = petClient.getPetById(id, ErrorResponse.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isIn(400, 404);

    }
}

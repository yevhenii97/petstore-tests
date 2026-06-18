package com.taf.tests;

import com.taf.restapi.clients.PetClient;
import com.taf.restapi.helper.SchemaValidator;
import com.taf.restapi.models.ApiResult;
import com.taf.restapi.models.ErrorResponse;
import com.taf.restapi.models.PetDTO;
import com.taf.restapi.services.PetService;
import com.taf.testdata.DeletePetByIdDataProvider;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Pet API")
@Feature("Delete pet by id")
@Slf4j
public class DeletePetByIdTests extends BaseTest{

    @Autowired
    private PetClient petClient;
    @Autowired
    private PetService petService;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that we delete pet by id successfully")
    public void checkThatPetIsBeingDeletedByIdSuccessfully() {
        PetDTO createdPet = petService.getCreatedPet();

        ApiResult<ErrorResponse> deleteResponse =
                petClient.deletePetById(String.valueOf(createdPet.getId()), ErrorResponse.class);

        assertThat(deleteResponse.getStatus())
                .as("Check status code")
                .isEqualTo(200);

        SchemaValidator.validate(deleteResponse, "schemas/error-response-schema.json");

        assertThat(deleteResponse.getBody().getMessage())
                .as("Check deleted pet id in response")
                .isEqualTo(String.valueOf(createdPet.getId()));

        Optional<PetDTO> pet =
                petService.findPetById(String.valueOf(createdPet.getId()));

        assertThat(pet)
                .as("Check that pet was deleted")
                .isEmpty();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that non-existing pet cannot be deleted")
    public void checkThatNonExistingPetIsNotDeleted() {
        String nonExistingPetId = "999999999999";

        ApiResult<ErrorResponse> response =
                petClient.deletePetById(nonExistingPetId, ErrorResponse.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isEqualTo(404);
    }

    @Test(dataProvider = "invalidPetIds", dataProviderClass = DeletePetByIdDataProvider.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that pet cannot be deleted by invalid id")
    public void checkThatPetIsNotBeingDeletedWithInvalidId(String id, String testDescription) {

        log.info("Test description {}", testDescription);

        ApiResult<ErrorResponse> response = petClient.deletePetById(id, ErrorResponse.class);

        assertThat(response.getStatus())
                .as("Check status code")
                .isIn(400, 404);

    }
}

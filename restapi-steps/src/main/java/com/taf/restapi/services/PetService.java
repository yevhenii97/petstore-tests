package com.taf.restapi.services;

import com.taf.restapi.clients.PetClient;
import com.taf.restapi.enums.PetStatus;
import com.taf.restapi.models.ApiResult;
import com.taf.restapi.models.Category;
import com.taf.restapi.models.PetDTO;
import com.taf.restapi.models.Tag;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetClient petClient;

    @Step("Create base pet")
    public PetDTO getCreatedPet(){
        PetDTO requestBody = PetDTO.builder()
                .id(1L)
                .category(Category.builder()
                        .id(1L)
                        .name("Dogs")
                        .build())
                .name("Doggie")
                .photoUrls(List.of("https://example.com/photo.jpg"))
                .tags(List.of(
                        Tag.builder()
                                .id(1L)
                                .name("friendly")
                                .build()
                ))
                .status(PetStatus.AVAILABLE)
                .build();

        ApiResult<PetDTO> petResponse = petClient.createPet(requestBody, PetDTO.class);

        if (petResponse.getBody() == null) {
            throw new RuntimeException("Pet was not created. Response: " + petResponse);
        }

        log.info("Pet {} was created", petResponse);

        return petResponse.getBody();
    }

    @Step("Get pet by id")
    public PetDTO getPetById(String petId) {
        return findPetById(petId)
                .orElseThrow(() -> new RuntimeException("Pet was not found by id: " + petId));
    }

    @Step("Find pet by id")
    public Optional<PetDTO> findPetById(String petId) {
        ApiResult<PetDTO> response = petClient.getPetById(petId, PetDTO.class);

        if (response.getStatus() == 200) {
            return Optional.ofNullable(response.getBody());
        }

        if (response.getStatus() == 404) {
            return Optional.empty();
        }

        throw new RuntimeException("Failed to get pet. Status: " + response.getStatus());
    }
}

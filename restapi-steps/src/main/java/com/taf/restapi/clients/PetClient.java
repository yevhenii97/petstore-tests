package com.taf.restapi.clients;

import com.taf.restapi.models.ApiResult;
import com.taf.restapi.models.PetDTO;
import com.taf.restapi.utils.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PetClient extends AbstractApiClient{

    @Value("${api.pet.base-url}")
    private String baseUrl;

    @Value("${api.pet.create-pet}")
    private String createPetPath;

    @Value("${api.pet.pet-path-with-id}")
    private String petWithIdPath;

    @Step("Create pet request")
    public <T> ApiResult<T> createPet(PetDTO payload, Class<T> type) {
        Response response = post(baseUrl + createPetPath, payload);
        return JsonMapper.map(response, type);
    }

    @Step("Get pet by id request")
    public <T> ApiResult<T> getPetById(String id, Class<T> type) {
        Response response = get(String.format(baseUrl + petWithIdPath, id));
        return JsonMapper.map(response, type);
    }

    @Step("Delete pet by id request")
    public <T> ApiResult<T> deletePetById(String editor, Class<T> type) {
        Response response = delete(String.format(baseUrl + petWithIdPath, editor));
        return JsonMapper.map(response, type);
    }

}

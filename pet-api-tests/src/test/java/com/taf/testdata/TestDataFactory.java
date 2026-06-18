package com.taf.testdata;

import com.taf.restapi.enums.PetStatus;
import com.taf.restapi.models.Category;
import com.taf.restapi.models.PetDTO;
import com.taf.restapi.models.Tag;

import java.util.List;

public class TestDataFactory {

    private TestDataFactory() {
    }

    public static PetDTO getBasicPetDto() {
         return PetDTO.builder()
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
    }
}

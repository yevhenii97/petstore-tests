package com.taf.restapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taf.restapi.enums.PetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private PetStatus status;
}

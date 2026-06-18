package com.taf.restapi.helper;

import com.taf.restapi.models.ApiResult;
import lombok.NoArgsConstructor;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

@NoArgsConstructor
public class SchemaValidator {

    public static void validate(ApiResult<?> result, String schemaPath) {
        if (result == null || result.isEmptyBody()) {
            throw new AssertionError("Response body is empty. Schema validation cannot be performed.");
        }

        assertThat(
                "JSON schema validation failed for schema: " + schemaPath,
                result.getRawBody(),
                matchesJsonSchemaInClasspath(schemaPath)
        );
    }
}

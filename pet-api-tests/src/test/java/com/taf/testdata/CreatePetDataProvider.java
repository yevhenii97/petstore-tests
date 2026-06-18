package com.taf.testdata;

import org.testng.annotations.DataProvider;

public class CreatePetDataProvider {


    @DataProvider(name = "getBodyWithOutRequiredFields")
    public static Object[][] getBodyWithOutRequiredFields() {
        return new Object[][]{
                {
                        TestDataFactory.getBasicPetDto()
                                .toBuilder()
                                .id(null)
                                .build(),

                        "Send request without id"
                },

                {
                        TestDataFactory.getBasicPetDto()
                                .toBuilder()
                                .name(null)
                                .build(),

                        "Send request without name"
                },

                {
                        TestDataFactory.getBasicPetDto()
                                .toBuilder()
                                .photoUrls(null)
                                .build(),

                        "Send request without photoUrls"
                }
        };
    }
}

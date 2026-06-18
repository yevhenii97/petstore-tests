package com.taf.testdata;

import org.testng.annotations.DataProvider;

public class GetPetByIdDataProvider {

    @DataProvider(name = "invalidPetIds")
    public static Object[][] invalidPetIds() {
        return new Object[][]{
                {"abc", "String id"},
                {"1.5", "Decimal id"},
                {" ", "Blank id"},
                {"-1", "Negative id"},
                {"#$#T##$@$$#", "Symbols"},
                {"null", "null"}
        };
    }
}

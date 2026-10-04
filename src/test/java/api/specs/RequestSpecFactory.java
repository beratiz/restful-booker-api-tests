package api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private static final String DEFAULT_BASE_URL =
            "https://restful-booker.herokuapp.com";

    private RequestSpecFactory() {
    }

    public static RequestSpecification defaultSpec() {

        String baseUrl = System.getProperty(
                "baseUrl",
                DEFAULT_BASE_URL
        );
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .build();
    }
}

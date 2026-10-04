package api.clients;

import api.dto.BookingRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookingApiClient {

    private final RequestSpecification requestSpec;

    public BookingApiClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getBookings() {
        return given()
                .spec(requestSpec)
            .when()
                .get("/booking");
    }

    public Response getBooking(int bookingId) {
        return given()
                .spec(requestSpec)
            .when()
                .get("/booking/{id}", bookingId);
    }

    public Response createBooking(BookingRequestDto request) {
        return given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post("/booking");
    }
}

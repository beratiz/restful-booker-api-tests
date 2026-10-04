package api.tests;

import api.base.BaseApiTest;
import api.data.BookingTestData;
import api.dto.BookingRequestDto;
import api.dto.BookingResponseDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class BookingApiTest extends BaseApiTest {

    @Test
    public void getBookingsShouldReturnNonEmptyList() {

        Response response = bookingApiClient.getBookings();

        response.then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("size()", greaterThan(0));

        List<Integer> bookingIds =
                response.jsonPath().getList("bookingid", Integer.class);
      //  System.out.println(bookingIds.toString());
        Assert.assertFalse(bookingIds.isEmpty());
    }

    @Test
    public void getExistingBookingShouldReturnBookingDetails() {

        int bookingId = getFirstBookingId();

        Response response =
                bookingApiClient.getBooking(bookingId);

        BookingResponseDto booking =
                response.then()
                        .statusCode(200)
                        .extract()
                        .as(BookingResponseDto.class);

        Assert.assertNotNull(booking.getFirstname());
        Assert.assertNotNull(booking.getLastname());
        Assert.assertTrue(booking.getTotalprice() >= 0);
        Assert.assertNotNull(booking.getBookingdates());
        Assert.assertNotNull(booking.getBookingdates().getCheckin());
        Assert.assertNotNull(booking.getBookingdates().getCheckout());
    }

    @Test
    public void createBookingShouldReturnCreatedBooking() {

        BookingRequestDto request =
                BookingTestData.validBooking();

        Response response =
                bookingApiClient.createBooking(request);

        response.then()
                .statusCode(200)
                .body("bookingid", greaterThan(0))
                .body("booking", notNullValue())
                .body("booking.firstname", org.hamcrest.Matchers.equalTo(request.getFirstname()))
                .body("booking.lastname", org.hamcrest.Matchers.equalTo(request.getLastname()))
                .body("booking.totalprice", org.hamcrest.Matchers.equalTo(request.getTotalprice()))
                .body("booking.depositpaid", org.hamcrest.Matchers.equalTo(request.isDepositpaid()));

        Integer bookingId =
                response.jsonPath().getInt("bookingid");

        Assert.assertNotNull(bookingId);
        Assert.assertTrue(bookingId > 0);
    }

    private int getFirstBookingId() {

        List<Map<String, Integer>> bookings =
                bookingApiClient.getBookings()
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("$");

        Assert.assertFalse(bookings.isEmpty(), "No bookings returned.");

        Integer bookingId =
                bookings.get(0).get("bookingid");

        Assert.assertNotNull(bookingId, "First booking has no bookingid.");

        return bookingId;
    }
}

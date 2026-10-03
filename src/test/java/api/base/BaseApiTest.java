package api.base;

import api.clients.BookingApiClient;
import api.specs.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    protected RequestSpecification requestSpec;
    protected BookingApiClient bookingApiClient;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecFactory.defaultSpec();
        bookingApiClient = new BookingApiClient(requestSpec);
    }
}

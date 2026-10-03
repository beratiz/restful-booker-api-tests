package api.data;

import api.dto.BookingDatesDto;
import api.dto.BookingRequestDto;

public final class BookingTestData {

    private BookingTestData() {
    }

    public static BookingRequestDto validBooking() {
        return new BookingRequestDto(
                "Berat",
                "Zengo",
                150,
                true,
                new BookingDatesDto(
                        "2026-10-10",
                        "2026-10-15"
                ),
                "Breakfast"
        );
    }
}

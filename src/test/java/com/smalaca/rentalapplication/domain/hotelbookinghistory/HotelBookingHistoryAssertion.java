package com.smalaca.rentalapplication.domain.hotelbookinghistory;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class HotelBookingHistoryAssertion {
    private final HotelBookingHistory actual;

    private HotelBookingHistoryAssertion(HotelBookingHistory actual) {
        this.actual = actual;
    }

    public static HotelBookingHistoryAssertion assertThat(HotelBookingHistory actual) {
        return new HotelBookingHistoryAssertion(actual);
    }

    public HotelBookingHistoryAssertion hasHotelRoomBookingHistoryFor(String hotelRoomId, LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        return hasHotelRoomBookingHistoryFor(hotelRoomBookingHistory -> {
            HotelRoomBookingHistoryAssertion.assertThat(hotelRoomBookingHistory)
                    .hasHotelRoomIdEqualTo(hotelRoomId)
                    .hasHotelRoomBookingFor(bookingDateTime, tenantId, days);
        });
    }

    public HotelBookingHistoryAssertion hasHotelRoomBookingHistoryFor(int hotelNumber, String tenantId, List<LocalDate> days) {
        return hasHotelRoomBookingHistoryFor(hotelRoomBookingHistory -> {
            HotelRoomBookingHistoryAssertion.assertThat(hotelRoomBookingHistory)
                    .hasHotelRoomBookingFor(tenantId, days);
        });
    }

    public HotelBookingHistoryAssertion hasHotelRoomBookingHistoryFor(String hotelRoomId, String tenantId, List<LocalDate> days) {
        return hasHotelRoomBookingHistoryFor(hotelRoomBookingHistory -> {
            HotelRoomBookingHistoryAssertion.assertThat(hotelRoomBookingHistory)
                    .hasHotelRoomIdEqualTo(hotelRoomId)
                    .hasHotelRoomBookingFor(tenantId, days);
        });
    }

    public HotelBookingHistoryAssertion hasInformationAboutHistoryOfHotelRoom(String hotelRoomId, int size) {
        return hasHotelRoomBookingHistoryFor(hotelRoomBookingHistory -> {
            HotelRoomBookingHistoryAssertion.assertThat(hotelRoomBookingHistory)
                    .hasHotelRoomIdEqualTo(hotelRoomId)
                    .hasInformationAboutBookings(size);
        });
    }

    private HotelBookingHistoryAssertion hasHotelRoomBookingHistoryFor(Consumer<HotelRoomBookingHistory> consumer) {
        hasHotelRoomBookingHistories().satisfies(actualBookings -> {
            Assertions.assertThat(asHotelRoomHistories(actualBookings)).anySatisfy(consumer);
        });

        return this;
    }

    public HotelBookingHistoryAssertion hasInformationAboutHistoryOfHotelRooms(int size) {
        hasHotelRoomBookingHistories().satisfies(actualBookings -> {
            Assertions.assertThat(asHotelRoomHistories(actualBookings)).hasSize(size);
        });

        return this;
    }

    private AbstractObjectAssert<?, ?> hasHotelRoomBookingHistories() {
        return Assertions.assertThat(actual).extracting("hotelRoomBookingHistories");
    }

    private List<HotelRoomBookingHistory> asHotelRoomHistories(Object actualBookings) {
        return (List<HotelRoomBookingHistory>) actualBookings;
    }
}
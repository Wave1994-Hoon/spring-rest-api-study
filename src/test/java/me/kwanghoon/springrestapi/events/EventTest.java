package me.kwanghoon.springrestapi.events;


// option + control + o : remove not used import


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
            .name("spring rest api")
            .description("rest api with spring")
            .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        String name = "Event";
        String description = "Spring";

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    public void checkEventIsFree() {
        // Given
        Event freeEvent = Event.builder()
            .name("spring rest api")
            .description("rest api with spring")
            .basePrice(0)
            .maxPrice(0)
            .build();

        Event notFreeEvent = Event.builder()
            .name("spring rest api")
            .description("rest api with spring")
            .basePrice(100)
            .maxPrice(1000)
            .build();

        // When
        freeEvent.update();
        notFreeEvent.update();

        // Then
        assertThat(freeEvent.isFree()).isTrue();
        assertThat(notFreeEvent.isFree()).isFalse();
    }

    @Test
    public void checkEventIsOffline() {
        // Given
        Event offlineEvent = Event.builder()
            .name("spring rest api")
            .description("rest api with spring")
            .location("강남")
            .build();

        Event onlineEvent = Event.builder()
            .name("spring rest api")
            .description("rest api with spring")
            .build();

        // When
        offlineEvent.update();
        onlineEvent.update();

        // Then
        assertThat(offlineEvent.isOffline()).isTrue();
        assertThat(onlineEvent.isOffline()).isFalse();
    }
}
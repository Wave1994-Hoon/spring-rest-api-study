package me.kwanghoon.springrestapi.events;

import me.kwanghoon.springrestapi.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest // Web 용 Bean 들만 가져옴
public class EventControllerTests extends BaseControllerTest {

    @Test
    @DisplayName("이벤트 생성 테스트")
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
//            .id(100)
            .name("spring")
            .description("rest api with spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2021, 4, 4, 4, 4))
            .closeEnrollmentDateTime(LocalDateTime.of(2021, 5, 4, 4, 4))
            .beginEventDateTime(LocalDateTime.of(2021, 6, 4, 4, 4))
            .endEventDateTime(LocalDateTime.of(2021, 7, 4, 4, 4))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남")
            .build();

        mockMvc.perform(
            post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event))
            )
            .andDo(print())  // 결과 값 출력
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists());
    }

    @Test
    @DisplayName("입력 받을 수 없는 값을 받았을 때 이벤트 생성 테스트")
    public void createEventBadRequest() throws Exception {
        Event event = Event.builder()
            .id(100)
            .name("spring")
            .description("rest api with spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2021, 4, 4, 4, 4))
            .closeEnrollmentDateTime(LocalDateTime.of(2021, 5, 4, 4, 4))
            .beginEventDateTime(LocalDateTime.of(2021, 6, 4, 4, 4))
            .endEventDateTime(LocalDateTime.of(2021, 7, 4, 4, 4))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남")
            .build();

        mockMvc.perform(
            post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event))
        )
            .andDo(print())  // 결과 값 출력
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 받을 수 없는 값을 누락되었을 때 이벤트 생성 테스트")
    public void createEventBadRequestEmptyInput() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(
            post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto))
        )
            .andDo(print())  // 결과 값 출력
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 받을 수 없는 값이 잘못되었을 때 이벤트 생성 테스트")
    public void createEventBadRequestWrongInput() throws Exception {
        EventDto eventDto = EventDto.builder()
            .name("spring")
            .description("rest api with spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2021, 4, 4, 4, 4))
            .closeEnrollmentDateTime(LocalDateTime.of(2020, 5, 4, 4, 4))
            .beginEventDateTime(LocalDateTime.of(2021, 6, 4, 4, 4))
            .endEventDateTime(LocalDateTime.of(2021, 7, 4, 4, 4))
            .basePrice(1000)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남")
            .build();

        mockMvc.perform(
            post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto))
        )
            .andDo(print())  // 결과 값 출력
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].objectName").exists())
            .andExpect(jsonPath("$[0].defaultMessage").exists())
            .andExpect(jsonPath("$[0].code").exists())
        ;
    }

    @Test
    @DisplayName("30개의 이벤트를 10개씩 두번쨰 페이지 조회")
    public void queryEvents() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When & Then
        mockMvc.perform(get("/api/events")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("page").exists())
        ;
    }

    @Test
    @DisplayName("기존 이벤트를 하나 조회")
    public void getEvent() throws Exception {
        // Given
        Event event = generateEvent(100);

        // When & Then
        mockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").exists())
            .andExpect(jsonPath("id").exists());
    }

    @Test
    @DisplayName("없는 이벤트 조회 시, 404 응답")
    public void getNotfoundEvent() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/events/1111111"))
            .andExpect(status().isNotFound());
    }

    private Event generateEvent(int index) {
        Event event = Event.builder()
            .name("event" + index)
            .description("test event")
            .build();

        return eventRepository.save(event);
    }
}

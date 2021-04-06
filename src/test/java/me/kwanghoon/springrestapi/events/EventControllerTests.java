package me.kwanghoon.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest // Web 용 Bean 들만 가져옴
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc; // 웹 서버를 띄우지 않지만 dispatcherServlet은 띄움

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean
//    EventRepository eventRepository;

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
}

package me.kwanghoon.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest // Web 용 Bean 들만 가져옴
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc; // 웹 서버를 띄우지 않지만 dispatcherServlet은 띄움

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {
        Event event = Event.builder()
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

        event.setId(10);
        Mockito.when(eventRepository.save(event)).thenReturn(event);

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
}

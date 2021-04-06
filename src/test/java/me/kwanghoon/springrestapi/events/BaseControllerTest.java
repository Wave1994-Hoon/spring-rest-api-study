package me.kwanghoon.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Ignore // test를 가지고 있는 테스트가 아니기 떄문에 필요함
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc; // 웹 서버를 띄우지 않지만 dispatcherServlet은 띄움

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected EventRepository eventRepository;
}

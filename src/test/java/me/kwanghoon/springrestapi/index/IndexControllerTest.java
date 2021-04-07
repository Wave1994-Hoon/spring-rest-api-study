package me.kwanghoon.springrestapi.index;

import me.kwanghoon.springrestapi.BaseControllerTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexControllerTest extends BaseControllerTest {

    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/api/index"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("_links.events").exists());
    }

}

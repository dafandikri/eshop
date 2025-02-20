package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testMainPage() throws Exception {
        mockMvc.perform(get(""))
               .andExpect(status().isOk())
               .andExpect(view().name("homePage"));
    }
}

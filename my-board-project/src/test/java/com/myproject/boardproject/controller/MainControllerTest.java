package com.myproject.boardproject.controller;

import com.myproject.boardproject.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    private final MockMvc mvc;

    public MainControllerTest(@Autowired  MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void givenNothing_whenRequestingRootPate_thenRedirectsToArticlesPage() throws Exception {
        //given

        //when & then
        mvc.perform(get("/")).andExpect(status().is3xxRedirection());
    }

}
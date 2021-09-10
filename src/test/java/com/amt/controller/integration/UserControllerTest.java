package com.amt.controller.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.amt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:springorm-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class UserControllerTest {
/*
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ShipDao shipDao;
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	@Transactional
	@Order(0)
	@Commit
	void test_addShip_andReceiveJsonResponse() throws Exception {
		AddShipDTO addShipDto = new AddShipDTO();
		addShipDto.setName("Black Pearl");
		addShipDto.setAge(100);
		
		String addShipDtoJson = objectMapper.writeValueAsString(addShipDto);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/ship")
				.contentType(MediaType.APPLICATION_JSON)
				.content(addShipDtoJson);
		
		User expected = new User("Black Pearl", 100);
		expected.setId(1);
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
*/
}

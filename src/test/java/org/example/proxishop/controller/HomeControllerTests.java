package org.example.proxishop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
		assertNotNull(mockMvc);
	}

	/**
	 * Test une erreur 404 si mauvaise route
	 *
	 */
	@Test
	public void testError404() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/123");

		mockMvc.perform(request)
				.andExpect(status().is4xxClientError());
	}

	/**
	 * Test le success de la route "/"
	 * template : index.html
	 *
	 */
	@Test
	public void testShowIndex() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("index"));
	}

	/**
	 * Test le success de la route "/proxishop"
	 * template : proxishop.html
	 *
	 */
	@Test
	public void testShowProxishop() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/proxishop");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("proxishop"));
	}

	/**
	 * Test le success de la route "/template_choice1"
	 * template : template_choice1.html
	 *
	 */
	@Test
	public void testShowTemplate1() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/template_choice1");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("template_choice1"));
	}

	/**
	 * Test le success de la route "/template_choice2"
	 * template : template_choice2.html
	 *
	 */
	@Test
	public void testShowTemplate2() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/template_choice2");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("template_choice2"));
	}

	/**
	 * Test le success de la route "/contact"
	 * template : contact.html
	 *
	 */
	@Test
	public void testShowContact() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/contact");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("contact"));
	}

	/**
	 * Test le success de la route "/mentions"
	 * template : mentions.html
	 *
	 */
	@Test
	public void testShowMentions() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/mentions");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("mentions"));
	}

	/**
	 * Test le success de la route "/cookies"
	 * template : cookies.html
	 *
	 */
	@Test
	public void testShowCookies() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/cookies");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("cookies"));
	}

}

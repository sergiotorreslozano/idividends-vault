package com.idividends.vault.restrepository;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.idividends.vault.domain.Client;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientRestRepositoryTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ClientRestRepository clientRepository;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void findAll() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/clients").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void addOne() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/clients").content("{\"email\":\"email@mail.com\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void addOneFail() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/clients").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void removeOne() throws Exception {
		Client client = new Client();
		client.setEmail("email@mail.com");
		clientRepository.save(client);
		mvc.perform(MockMvcRequestBuilders.delete("/clients/" + client.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void updateOne() throws Exception {
		Client client = new Client();
		client.setEmail("email@mail.com");
		clientRepository.save(client);
		mvc.perform(MockMvcRequestBuilders.put("/clients/" + client.getId())
				.content("{\"email\":\"updatedemail@mail.com\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
		assertTrue(clientRepository.findOne(client.getId()).getEmail().equals("updatedemail@mail.com"));
	}

}

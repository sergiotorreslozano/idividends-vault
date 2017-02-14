package com.idividends.vault.restrepository;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idividends.vault.domain.Client;
import com.idividends.vault.domain.Portfolio;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PortfolioRestRepositoryTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ClientRestRepository clientRepository;

	@Autowired
	private PortfolioRestRepository portofolioRepository;

	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void cleanUp()throws Exception{
		portofolioRepository.deleteAll();
		clientRepository.deleteAll();
	}

	@Test
	public void findAll() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/portfolios").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void addOne() throws Exception {
		Client client = new Client("email@mail.com");
		clientRepository.save(client);
		Portfolio portfolio = new Portfolio("name",client.getId() );
		mvc.perform(MockMvcRequestBuilders.post("/portfolios").content(objectMapper.writeValueAsString(portfolio))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void addOneFail() throws Exception {
		// client ID big enough...
		Portfolio portfolio = new Portfolio("name", null);
		mvc.perform(MockMvcRequestBuilders.post("/portfolios").content(objectMapper.writeValueAsString(portfolio))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void removeOne() throws Exception {
		Client client = new Client();
		client.setEmail("email@mail.com");
		clientRepository.save(client);
		Portfolio portfolio = new Portfolio("name", client.getId());
		portofolioRepository.save(portfolio);
		mvc.perform(
				MockMvcRequestBuilders.delete("/portfolios/" + portfolio.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void updateOne() throws Exception {
		Client client = new Client();
		client.setEmail("email@mail.com");
		clientRepository.save(client);
		Portfolio portfolio = new Portfolio("name", client.getId());
		portofolioRepository.save(portfolio);
		portfolio.setName("updatedname");
		mvc.perform(MockMvcRequestBuilders.put("/portfolios/" + portfolio.getId())
				.content(objectMapper.writeValueAsString(portfolio))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
		assertTrue(portofolioRepository.findOne(portfolio.getId()).getName().equals("updatedname"));
	}

}

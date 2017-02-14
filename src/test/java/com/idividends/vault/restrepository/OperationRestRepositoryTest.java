package com.idividends.vault.restrepository;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

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
import com.idividends.vault.domain.Operation;
import com.idividends.vault.domain.Portfolio;
import com.idividends.vault.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OperationRestRepositoryTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private OperationRestRepository operationRepository;

	@Autowired
	private PortfolioRestRepository portfolioRepository;

	@Autowired
	private ProductRestRepository productRepository;

	@Autowired
	private ClientRestRepository clientRepository;

	@Autowired

	private ObjectMapper objectMapper;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void cleanUp() throws Exception{
		operationRepository.deleteAll();
		productRepository.deleteAll();
		portfolioRepository.deleteAll();
		clientRepository.deleteAll();
	}

	@Test
	public void findAll() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/operations").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void addOne() throws Exception {
		Product product = new Product("symbol", "name");
		productRepository.save(product);
		Client client = new Client("email@mail.com");
		clientRepository.save(client);
		Portfolio portfolio = new Portfolio("name", client.getId());
		portfolioRepository.save(portfolio);
		mvc.perform(
				MockMvcRequestBuilders.post("/operations")
						.content(objectMapper.writeValueAsString(new Operation(1d, 1L, 1d, "currency", Instant.now(),
								"op",
								product.getId(), portfolio.getId())))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void addOneFail() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/operations").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void removeOne() throws Exception {
		Product product = new Product("symbol", "name");
		productRepository.save(product);
		Client client = new Client("email@mail.com");
		clientRepository.save(client);
		Portfolio portfolio = new Portfolio("name",client.getId());
		portfolioRepository.save(portfolio);
		Operation operation = new Operation(1d, 1L, 1d, "currency", Instant.now(), "type", product.getId(),
				portfolio.getId());
		operationRepository.save(operation);
		mvc.perform(
				MockMvcRequestBuilders.delete("/operations/" + operation.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void updateOne() throws Exception {
		Product product = new Product("symbol", "name");
		productRepository.save(product);
		Client client = new Client("email@mail.com");
		clientRepository.save(client);
		Portfolio portfolio = new Portfolio("name", client.getId());
		portfolioRepository.save(portfolio);
		Operation operation = new Operation(1d, 1L, 1d, "currency", Instant.now(), "type", product.getId(),
				portfolio.getId());
		operationRepository.save(operation);
		operation.setCurrency("updatedcurrency");
		mvc.perform(MockMvcRequestBuilders.put("/operations/" + product.getId())
				.content(objectMapper.writeValueAsString(operation))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
		assertTrue(operationRepository.findOne(operation.getId()).getCurrency().equals("updatedcurrency"));
	}

}

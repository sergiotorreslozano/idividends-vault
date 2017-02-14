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
import com.idividends.vault.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductRestRepositoryTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ProductRestRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void cleanUp(){
		productRepository.deleteAll();
	}

	@Test
	public void findAll() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void addOne() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/products")
				.content(objectMapper.writeValueAsString(new Product("symbol", "name")))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void addOneFail() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void removeOne() throws Exception {
		Product product = new Product("symbol", "name");
		productRepository.save(product);
		mvc.perform(MockMvcRequestBuilders.delete("/products/" + product.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void updateOne() throws Exception {
		Product product = new Product("symbol", "name");
		productRepository.save(product);
		product.setSymbol("updatedsymbol");
		mvc.perform(MockMvcRequestBuilders.put("/products/" + product.getId())
				.content(objectMapper.writeValueAsString(product))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
		assertTrue(productRepository.findOne(product.getId()).getSymbol().equals("updatedsymbol"));
	}

}

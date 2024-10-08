package org.example.proxishop.controller;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.shopkeeper.Product;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BdCategories bdCategories;

	@Mock
	private BdProducts bdProducts;

	@InjectMocks
	private ProductController productController; // Remplacez par votre contrôleur

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreaProd() throws Exception {
		// Simulez les objets de retour de BdCategories et BdProducts
		when(bdCategories.getAllCategories(anyString())).thenReturn(Arrays.asList(new ProductCategory(1.0,"Cat1"), new ProductCategory(2.0,"Cat2")));
		when(bdCategories.getAllSubCategories(anyString())).thenReturn(Arrays.asList(new ProductSubCategory(1.0,"SubCat1"), new ProductSubCategory(2.0,"SubCat2")));
		when(bdProducts.getAllProducts(anyString())).thenReturn(Arrays.asList(new Product(1.0, "Prod1", "description1", 1.0 , "image1", 1.0, 1.0), new Product(2.0, "Prod2", "description2", 2.0 , "image2", 2.0, 2.0)));

		// Simulez l'appel HTTP
		mockMvc.perform(get("/products/clearSession").param("bddname", "testdb"))
				.andExpect(status().is3xxRedirection())  // Vérifiez que le statut est OK
				.andExpect(view().name("products"))  // Vérifiez que la vue retournée est "products"
				.andExpect(model().attributeExists("categoryNamesList"))
				.andExpect(model().attributeExists("subCategoryList"))
				.andExpect(model().attributeExists("productList"))
				.andExpect(model().attribute("bddname", "testdb"));

		// Vérifiez que les méthodes des mocks ont été appelées correctement
		verify(bdCategories).getAllCategories("testdb");
		verify(bdCategories).getAllSubCategories("testdb");
		verify(bdProducts).getAllProducts("testdb");
	}

	@Test
	public void testClearSession() throws Exception {
		// Effectue une requête GET à l'URL "/clearSession"
		mockMvc.perform(get("/products/clearSession"))
				.andExpect(status().is3xxRedirection()) // Vérifie que la réponse est une redirection
				.andExpect(redirectedUrl("/index")); // Vérifie que la redirection est vers "/index"
	}
}

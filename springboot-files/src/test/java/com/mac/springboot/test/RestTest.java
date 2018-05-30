package com.mac.springboot.test;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mac.springboot.repository.DocumentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DocumentRepository documentRepository;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		documentRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/document/status")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void shouldCreateEntity() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file", "origin.txt", null, "bar".getBytes());

		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/document/upload").file(file)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.title").value("origin.txt"));
	}
}
package com.cloudcampaignexam.tinyurlapi;

import java.util.List;

import com.cloudcampaignexam.tinyurlapi.models.TinyUrl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TinyUrlResource.class)
class TinyUrlResourceTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TinyUrlResource tinyUrlResource;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAllUrls() throws Exception {
		String userId = "fakeUserid";
		TinyUrl tinyUrl = new TinyUrl("id", userId, "baseUrl", "abcdefg", "http://CloudCampaign.com");

 
		List<TinyUrl> allTinyUrls = singletonList(tinyUrl);
 
		given(tinyUrlResource.getAllUrls(userId)).willReturn(allTinyUrls);
 
		mvc.perform(get("/urls")
				.header("userId", userId)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].realUrl", is(tinyUrl.getRealUrl())));
	}
	@Test
	void testGetUrl() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "http://CloudCampaign.com");

 
		given(tinyUrlResource.getUrl(userId,id)).willReturn(tinyUrl);
 
		mvc.perform(get("/urls/{id}", id)
				.header("userId", userId)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("realUrl", is(tinyUrl.getRealUrl())));
	}
	@Test
	void testPostReturnsOK() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "http://CloudCampaign.com");
 
		mvc.perform(post("/urls")
				.header("userId", userId)
				.content(objectMapper.writeValueAsString(tinyUrl))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	void testPostReturnsBadRequestWhenRealURLMissing() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "");

 
		mvc.perform(post("/urls")
				.header("userId", userId)
				.content(objectMapper.writeValueAsString(tinyUrl))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	@Test
	void testPostReturnsBadRequestWhenRealURLMalformed() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "garbageURL");
 
		mvc.perform(post("/urls")
				.header("userId", userId)
				.content(objectMapper.writeValueAsString(tinyUrl))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	@Test
	void testPutReturnsOk() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "http://CloudCampaign.com");

		mvc.perform(put("/urls/{id}", id)
				.header("userId", userId)
				.content(objectMapper.writeValueAsString(tinyUrl))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	void testPutReturnsBadRequestWhenRealURLMissing() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "");

		mvc.perform(put("/urls/{id}", id)
				.header("userId", userId)
				.content(objectMapper.writeValueAsString(tinyUrl))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	@Test
	void testPutReturnsBadRequestWhenRealURLMalformed() throws Exception {
		String userId = "fakeUserid";
		String id = "fakeId";
		TinyUrl tinyUrl = new TinyUrl(id, userId, "baseUrl", "abcdefg", "garbageURL");

		mvc.perform(put("/urls/{id}", id)
				.header("userId", userId)
				.content(objectMapper.writeValueAsString(tinyUrl))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


}

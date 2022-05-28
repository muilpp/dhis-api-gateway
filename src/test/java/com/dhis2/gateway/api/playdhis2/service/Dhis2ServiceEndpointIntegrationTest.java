package com.dhis2.gateway.api.playdhis2.service;

import com.dhis2.gateway.api.playdhis2.model.elements.DataElements;
import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupElements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class Dhis2ServiceEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();

        GroupDataElementsDTO groupDataElementsDTO = getGroupElements();
        Mockito.when(restTemplate.getForEntity(
                        "https://play.dhis2.org/2.37.6/api/dataElementGroups.json?paging=false&fields=id,displayName,dataElements[id]",
                        GroupDataElementsDTO.class))
                .thenReturn(new ResponseEntity<>(groupDataElementsDTO, HttpStatus.OK));
    }

    @Test
    @DisplayName("Data element is correctly fetched")
    public void dataElementIsCorrectlyFetchedFromEndpoint() throws Exception {
        //Given the data element and the mocked restTemplate
        DataElementsDTO dataElementsDTO = new DataElementsDTO(Arrays.asList(
                new DataElements("1", "firstElement", Collections.emptyList()),
                new DataElements("2", "secondElement", Collections.emptyList())));

        Mockito.when(restTemplate.getForEntity(
                        "https://play.dhis2.org/2.37.6/api/dataElements.json?paging=false&fields=id,displayName,dataElementGroups[id]", DataElementsDTO.class))
                .thenReturn(new ResponseEntity<>(dataElementsDTO, HttpStatus.OK));

        //When the request is triggered, then the response contains the expected data element
        mockMvc.perform(get("http://localhost:8091/api/dataElements")
                        .contentType("application/json")
                        .header("Authorization", "Basic bWFyYzpzZWNyZXQz"))
                .andDo(document("data-elements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gatewayElementsList[0].id", is("1")))
                .andExpect(jsonPath("$.gatewayElementsList[1].name", is("secondElement")));
    }

    @Test
    @DisplayName("Group element is correctly fetched")
    public void groupElementIsCorrectlyFetchedFromEndpoint() throws Exception {
        mockMvc.perform(get("http://localhost:8091/api/dataElementGroups")
                        .contentType("application/json")
                        .header("Authorization", "Basic bWFyYzpzZWNyZXQz"))
                .andDo(document("group-elements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gatewayElementsList[0].name", is("firstElement")))
                .andExpect(jsonPath("$.gatewayElementsList[1].id", is("2")));
    }

    @Test
    @DisplayName("Unauthorized if wrong credentials supplied")
    public void unauthorizedResponseIfBadCredentialsSupplied() throws Exception {
        mockMvc.perform(get("http://localhost:8091/api/dataElementGroups")
                        .contentType("application/json")
                        .header("Authorization", "Basic badCredentials"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Xml format in response")
    public void xmlFormatResponseWhenXmlExtensionUsed() throws Exception {
        requestInSpecificExtension(MediaType.APPLICATION_XML, ".xml");
    }

    @Test
    @DisplayName("Json format in response")
    public void jsonFormatResponseWhenJsonExtensionUsed() throws Exception {
        requestInSpecificExtension(MediaType.APPLICATION_JSON, ".json");
    }

    public void requestInSpecificExtension(MediaType mediaType, String extension) throws Exception {
        mockMvc.perform(get("http://localhost:8091/api/dataElementGroups"+extension)
                        .header("Authorization", "Basic bWFyYzpzZWNyZXQz"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType));
    }

    public GroupDataElementsDTO getGroupElements() {
        return new GroupDataElementsDTO(Arrays.asList(
                new GroupElements("1", "firstElement", Collections.emptyList()),
                new GroupElements("2", "secondElement", Collections.emptyList())));
    }
}
package com.dhis2.gateway.api.playdhis2.service;

import com.dhis2.gateway.api.playdhis2.model.elements.DataElements;
import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupElements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Dhis2ServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private Dhis2Service dhis2Service;

    @Test
    @DisplayName("Data element is correctly fetched")
    public void dataElementIsRequestedAndFetchedCorrectly() {
        //Given the data element and the mocked endpoint
        DataElementsDTO dataElementsDTO = new DataElementsDTO(Arrays.asList(
                new DataElements("1", "firstElement", Collections.emptyList()),
                new DataElements("2", "secondElement", Collections.emptyList())));

        Mockito.when(restTemplate.getForEntity(
                        "https://play.dhis2.org/2.37.6/api/dataElements.json?paging=false&fields=id,displayName,dataElementGroups[id]", DataElementsDTO.class))
          .thenReturn(new ResponseEntity<>(dataElementsDTO, HttpStatus.OK));

        //When request is triggered
        DataElementsDTO elementsResponse = dhis2Service.getDataElements();

        //Then the response contains the expected data element
        Assertions.assertEquals(dataElementsDTO.getDataElements().size(), elementsResponse.getDataElements().size());
        Assertions.assertEquals(dataElementsDTO.getDataElements().get(0).getDisplayName(), elementsResponse.getDataElements().get(0).getDisplayName());
    }

    @Test
    @DisplayName("Group element is correctly fetched")
    public void dataElementGroupsAreRequestedAndFetchedCorrectly() {
        //Given the group element and the mocked endpoint
        GroupDataElementsDTO groupDataElementsDTO = new GroupDataElementsDTO(Arrays.asList(
                new GroupElements("1", "firstElement", Collections.emptyList()),
                new GroupElements("2", "secondElement", Collections.emptyList())));

        Mockito.when(restTemplate.getForEntity(
                        "https://play.dhis2.org/2.37.6/api/dataElementGroups.json?paging=false&fields=id,displayName,dataElements[id]", GroupDataElementsDTO.class))
                .thenReturn(new ResponseEntity<>(groupDataElementsDTO, HttpStatus.OK));

        //When request is triggered
        GroupDataElementsDTO groupsResponse = dhis2Service.getDataElementGroups();

        //Then the response contains the expected group data element
        Assertions.assertEquals(groupDataElementsDTO.getGroupElementsGroups().size(), groupsResponse.getGroupElementsGroups().size());
        Assertions.assertEquals(groupDataElementsDTO.getGroupElementsGroups().get(0).getDisplayName(), groupsResponse.getGroupElementsGroups().get(0).getDisplayName());
    }
}

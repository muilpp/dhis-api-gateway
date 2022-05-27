package com.dhis2.test.api.playdhis2.service;

import com.dhis2.test.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.test.api.playdhis2.model.groups.GroupDataElementsDTO;
import com.dhis2.test.config.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@Service
public class Dhis2ServiceImpl implements Dhis2Service {

    private static final Logger LOGGER = Logger.getLogger(Dhis2ServiceImpl.class.getName());

    @Autowired
    RestTemplate restTemplate;

    @Value("${dhis2.url}")
    private String dhis2Url;

    @Override
    @Cacheable(value = CacheConfig.DATA_ELEMENTS_CACHE, key = "#root.method.name")
    public DataElementsDTO getDataElements() {
        ResponseEntity<DataElementsDTO> response = restTemplate
                .getForEntity(dhis2Url + "/api/dataElements.json?paging=false&fields=id,displayName,dataElementGroups[id]", DataElementsDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        LOGGER.warning("Failed to get data elements elements groups, error code: " + response.getStatusCodeValue());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    @Cacheable(value = CacheConfig.DATA_ELEMENTS_CACHE, key = "#root.method.name")
    public GroupDataElementsDTO getDataElementGroups() {
        ResponseEntity<GroupDataElementsDTO> response = restTemplate
                .getForEntity(dhis2Url + "/api/dataElementGroups.json?paging=false&fields=id,displayName,dataElements[id]", GroupDataElementsDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        LOGGER.warning("Failed to get elements groups, error code: " + response.getStatusCodeValue());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

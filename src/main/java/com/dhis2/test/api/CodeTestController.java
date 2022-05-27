package com.dhis2.test.api;

import com.dhis2.test.api.gateway.model.GatewayElementsDTO;
import com.dhis2.test.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.test.api.playdhis2.model.groups.GroupDataElementsDTO;
import com.dhis2.test.api.playdhis2.service.Dhis2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE })
public class CodeTestController {
    private static final Logger LOGGER = Logger.getLogger(CodeTestController.class.getName());

    @Autowired
    private Dhis2Service dhis2Service;

    @GetMapping("dataElements")
    private GatewayElementsDTO getDataElements() {
        DataElementsDTO dataElementsDTO = dhis2Service.getDataElements();
        return new GatewayElementsDTO(dataElementsDTO);
    }

    @GetMapping("dataElementGroups")
    private GatewayElementsDTO getDataElementGroups() {
        GroupDataElementsDTO groupDataElementsDTO = dhis2Service.getDataElementGroups();
        return new GatewayElementsDTO(groupDataElementsDTO);
    }
}

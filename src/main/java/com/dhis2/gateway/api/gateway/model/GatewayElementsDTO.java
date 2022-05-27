package com.dhis2.gateway.api.gateway.model;

import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsGroups;
import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElement;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElementsDTO;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GatewayElementsDTO {
    private List<GatewayElements> gatewayElementsList;

    public GatewayElementsDTO(DataElementsDTO elementsDTO) {
        gatewayElementsList = elementsDTO.getDataElements().stream()
                .map(e -> {
                    GatewayElements gatewayElements = new GatewayElements();
                    gatewayElements.setId(e.getId());
                    gatewayElements.setName(e.getDisplayName());
                    gatewayElements.setGroups(e.getDataElementsGroups().stream().map(DataElementsGroups::getId).collect(Collectors.toList()));
                    return gatewayElements;})
                .collect(Collectors.toList());
    }

    public GatewayElementsDTO(GroupDataElementsDTO groupsDTO) {
        gatewayElementsList = groupsDTO.getGroupElementsGroups().stream()
                .map(e -> {
                    GatewayElements gatewayElements = new GatewayElements();
                    gatewayElements.setId(e.getId());
                    gatewayElements.setName(e.getDisplayName());
                    gatewayElements.setMembers(e.getGroupDataElements().stream().map(GroupDataElement::getId).collect(Collectors.toList()));
                    return gatewayElements;})
                .collect(Collectors.toList());
    }
}

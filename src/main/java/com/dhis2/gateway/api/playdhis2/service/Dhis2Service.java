package com.dhis2.gateway.api.playdhis2.service;

import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElementsDTO;

public interface Dhis2Service {
    DataElementsDTO getDataElements();
    GroupDataElementsDTO getDataElementGroups();
}

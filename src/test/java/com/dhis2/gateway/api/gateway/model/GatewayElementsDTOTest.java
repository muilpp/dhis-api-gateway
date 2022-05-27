package com.dhis2.gateway.api.gateway.model;

import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsGroups;
import com.dhis2.gateway.api.playdhis2.model.elements.DataElements;
import com.dhis2.gateway.api.playdhis2.model.elements.DataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElement;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupDataElementsDTO;
import com.dhis2.gateway.api.playdhis2.model.groups.GroupElements;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GatewayElementsDTOTest {

    @Test
    public void elementDataIsTransformedToGatewayGroupElement() {
        //Given all the element data
        DataElementsDTO elementsDTO = new DataElementsDTO();
        DataElements firstElement = new DataElements();
        firstElement.setDataElementsGroups(Arrays.asList(new DataElementsGroups("1"), new DataElementsGroups("2"),
                new DataElementsGroups("3"), new DataElementsGroups("4")));
        firstElement.setId("1");
        firstElement.setDisplayName("First element");

        DataElements secondElement = new DataElements();
        secondElement.setDataElementsGroups(Arrays.asList(new DataElementsGroups("1"), new DataElementsGroups("2")));
        secondElement.setId("2");
        secondElement.setDisplayName("Second element");

        List<DataElements> elementsList = new ArrayList<>();
        elementsList.add(firstElement);
        elementsList.add(secondElement);
        elementsDTO.setDataElements(elementsList);

        //When the constructor of a gatewayElement is called
        GatewayElementsDTO gatewayElementsDTO = new GatewayElementsDTO(elementsDTO);

        //Then all the elements are transformed correctly
        Assert.notNull(gatewayElementsDTO, "GatewayDTO is null");
        Assert.noNullElements(gatewayElementsDTO.getGatewayElementsList(), "GatewayDTO has null elements");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().size() == 2, "Size of elements not expected");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().get(0).getGroups().size() == 4, "Size of first element groups not expected");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().get(1).getGroups().size() == 2, "Size of second element groups not expected");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().get(1).getName().equalsIgnoreCase("Second element"), "Second element name not expected");
    }

    @Test
    public void groupDataIsTransformedToGatewayMemberElement() {
        //Given all the group data
        GroupDataElementsDTO groupsDTO = new GroupDataElementsDTO();
        GroupElements firstElement = new GroupElements();
        firstElement.setId("1");
        firstElement.setDisplayName("First element");
        firstElement.setGroupDataElements(Arrays.asList(new GroupDataElement("1"), new GroupDataElement("2")
                , new GroupDataElement("3")));

        GroupElements secondElement = new GroupElements();
        secondElement.setId("2");
        secondElement.setDisplayName("Second element");
        secondElement.setGroupDataElements(Arrays.asList(new GroupDataElement("4"), new GroupDataElement("5")));

        List<GroupElements> groupElements = new ArrayList<>();
        groupElements.add(firstElement);
        groupElements.add(secondElement);
        groupsDTO.setGroupElementsGroups(groupElements);

        //When the constructor of a gatewayElement is called
        GatewayElementsDTO gatewayElementsDTO = new GatewayElementsDTO(groupsDTO);

        //Then all the elements are transformed correctly
        Assert.notNull(gatewayElementsDTO, "GatewayDTO is null");
        Assert.noNullElements(gatewayElementsDTO.getGatewayElementsList(), "GatewayDTO has null elements");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().size() == 2, "Size of elements not expected");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().get(0).getMembers().size() == 3, "Size of first element groups not expected");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().get(1).getMembers().size() == 2, "Size of second element groups not expected");
        Assert.isTrue(gatewayElementsDTO.getGatewayElementsList().get(0).getName().equalsIgnoreCase("First element"), "First element name not expected");
    }
}

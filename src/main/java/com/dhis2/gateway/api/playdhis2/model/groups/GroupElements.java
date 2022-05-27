package com.dhis2.gateway.api.playdhis2.model.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupElements {
    private String id;
    private String displayName;
    @JsonProperty("dataElements")
    private List<GroupDataElement> groupDataElements;
}

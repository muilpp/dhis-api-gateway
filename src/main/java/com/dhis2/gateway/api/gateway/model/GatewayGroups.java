package com.dhis2.gateway.api.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GatewayGroups {
    private String id;
    private String name;
    private List<String> members;
}
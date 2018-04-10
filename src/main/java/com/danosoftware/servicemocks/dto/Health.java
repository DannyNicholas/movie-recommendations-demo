package com.danosoftware.servicemocks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Health {

    @JsonProperty("status")
    private final String status;

    public Health(
            String status) {

        this.status = status;
    }
}

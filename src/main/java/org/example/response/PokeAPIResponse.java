package org.example.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PokeAPIResponse {
    private int id;
    private String name;
    private List<PokeAPITypesResponse> types;
}

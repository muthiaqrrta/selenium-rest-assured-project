package org.example.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PokeAPITypesResponse {
    private PokeAPITypeResponse type;
}

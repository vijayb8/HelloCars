package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Rent {
    @NonNull
    private UUID Id;

    @JsonProperty(required = true)
    private Location CurrentLocation;

    @JsonProperty(required = true)
    private Location Destination;
}

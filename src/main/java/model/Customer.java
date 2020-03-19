package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Customer {
    @NonNull
    private UUID Id;

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private String firstName;

    @JsonProperty(required = true)
    private String lastName;

    private float currentBalance;

    private Location currentLocation;
}

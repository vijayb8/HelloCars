package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor (force = true)
public class Car {
    @NonNull
    private UUID Id;

    private String model;
    private String company;
    private float rent;
    private boolean isAvailable;
}

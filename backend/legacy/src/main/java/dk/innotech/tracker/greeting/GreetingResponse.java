package dk.innotech.tracker.greeting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GreetingResponse {
    private String greeting;
    private String target;
}

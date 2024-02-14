package backend.zip.dto.main.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CurrentLocationRequest {
    private Double x;
    private Double y;

}

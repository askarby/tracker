package dk.innotech.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Auditing information model for creation or updating")
public class AuditDetailsDTO {
    @JsonProperty("by")
    @ApiModelProperty(notes = "Unique identifier of user producing auditing information", example = "1L", required = true)
    private Long userId;

    @JsonProperty("at")
    @ApiModelProperty(notes = "Timestamp of when the auditing information was applied", example = "19238723191238", required = true)
    private Long timestamp;
}

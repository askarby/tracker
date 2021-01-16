package dk.innotech.user.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@ApiModel(description = "Auditing information model")
public class AuditDTO {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = AuditDetailsDTO.class)
    @ApiModelProperty(notes = "Auditing information about creation", required = true)
    private AuditDetailsDTO created;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = AuditDetailsDTO.class)
    @ApiModelProperty(notes = "Auditing information about updates")
    private AuditDetailsDTO updated;
}

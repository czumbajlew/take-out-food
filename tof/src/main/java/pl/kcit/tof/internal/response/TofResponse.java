package pl.kcit.tof.internal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pl.kcit.tof.internal.Response;
import pl.kcit.tof.internal.response.error.TofErrorResponse;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TofResponse<T extends Response> {

    private TofErrorResponse error;
    private String message;
    private T item;

}

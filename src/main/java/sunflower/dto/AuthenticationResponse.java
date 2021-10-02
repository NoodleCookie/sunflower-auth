package sunflower.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class AuthenticationResponse {

    private int code;

    private String msg;
}

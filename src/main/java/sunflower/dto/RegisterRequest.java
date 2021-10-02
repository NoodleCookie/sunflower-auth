package sunflower.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

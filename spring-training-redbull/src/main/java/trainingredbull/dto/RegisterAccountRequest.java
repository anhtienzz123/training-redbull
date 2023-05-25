package trainingredbull.dto;

import lombok.Data;

@Data
public class RegisterAccountRequest {

    private String name;

    private String email;

    private String password;
}

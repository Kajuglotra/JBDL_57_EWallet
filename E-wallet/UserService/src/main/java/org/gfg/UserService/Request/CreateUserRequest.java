package org.gfg.UserService.Request;

import lombok.*;
import org.gfg.UserService.model.User;
import org.gfg.Utils.UserIdentifier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateUserRequest {
    private String name;
    @NotBlank
    private String contact;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private UserIdentifier userIdentifier;
    private String userIdentifierValue;

    public User toUser() {
        return User.builder().
                name(this.name).
                contact(this.contact).
               // password(this.password).
                email(this.email).
                userIdentifier(this.userIdentifier).
                userIdentifierValue(this.userIdentifierValue).
                build();
    }
}

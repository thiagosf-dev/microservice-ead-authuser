package com.ead.authuser.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ead.authuser.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface UserView {
        public static interface RegistrationPost {
        }

        public static interface UserPut {
        }

        public static interface PasswordPut {
        }

        public static interface ImagePut {
        }
    }

    @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
    @Size(max = 11, min = 11, groups = { UserView.RegistrationPost.class })
    private String cpf;

    @JsonView({ UserView.RegistrationPost.class })
    @NotBlank(groups = { UserView.RegistrationPost.class })
    @Email(groups = { UserView.RegistrationPost.class })
    @Size(max = 50, min = 4, groups = { UserView.RegistrationPost.class })
    private String email;

    @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
    @Size(max = 50, groups = { UserView.RegistrationPost.class, UserView.UserPut.class })
    private String fullName;

    @JsonView({ UserView.ImagePut.class })
    @NotBlank(groups = { UserView.ImagePut.class })
    private String imageUrl;

    @JsonView({ UserView.PasswordPut.class })
    @NotBlank(groups = { UserView.PasswordPut.class })
    @Size(max = 20, min = 6, groups = { UserView.RegistrationPost.class, UserView.PasswordPut.class })
    private String oldPassword;

    @JsonView({ UserView.RegistrationPost.class, UserView.PasswordPut.class })
    @NotBlank(groups = { UserView.RegistrationPost.class, UserView.PasswordPut.class })
    @Size(max = 20, min = 6, groups = { UserView.RegistrationPost.class, UserView.PasswordPut.class })
    private String password;

    @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
    private String phoneNumber;

    @JsonView({ UserView.RegistrationPost.class })
    @NotBlank(groups = { UserView.RegistrationPost.class })
    @Size(max = 50, min = 4, groups = { UserView.RegistrationPost.class })
    @UsernameConstraint(groups = { UserView.RegistrationPost.class })
    private String username;

}

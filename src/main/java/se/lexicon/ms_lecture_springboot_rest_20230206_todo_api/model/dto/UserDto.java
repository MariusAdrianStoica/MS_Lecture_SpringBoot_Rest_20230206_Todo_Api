package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto;

import lombok.Getter;
import lombok.Setter;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter

public class UserDto {
   @NotEmpty
   @Size(min = 4, max = 50)
   private String username;
    @Size(min = 4)
   private String password;
   @NotNull
   @Valid // in order to use all validations from RoleDto
   private List<RoleDto> roles;

    //because userDto contains password -> we can create a Custom UserDto that not contains password
    //we don't want to return password when find after user

}

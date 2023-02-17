package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto;

import lombok.Getter;
import lombok.Setter;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;

import java.util.List;

@Getter
@Setter

public class UserDto {
    private String username;
    private String password;
    private List<RoleDto> roles;

    //because userDto contains password -> we can create a Custom UserDto that not contains password
    //we don't want to return password when find after user

}

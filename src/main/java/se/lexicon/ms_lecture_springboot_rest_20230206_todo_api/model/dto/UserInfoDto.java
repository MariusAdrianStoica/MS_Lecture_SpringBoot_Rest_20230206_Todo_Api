package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto;

import lombok.Getter;
import lombok.Setter;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;

import java.util.List;

@Getter
@Setter

public class UserInfoDto {
    private String username;
    private List<RoleDto> roles;

    //because userDto contains password -> we can create a Custom UserDto that not contains password
    //we don't want to return password when find after user

    //the main purpose of DTO is to customize the Json message (the response data)

}

package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.service;

import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.UserDto;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.UserInfoDto;

import java.util.Map;

public interface UserService {

    UserDto register(UserDto userDTO);
    //UserInfoDto findByUsernameX(String username);

    // -> if we have to customize many users, then it is better to create Map
    Map<String, Object> findByUsername(String username);
    //String == attributes name
    //Object ==

    void disableUserByUsername(String username);
    //if the user can be found, it will be disabled -> no other answer
    //if the user can not be found, it will throw Exception (DataNotFound)

}

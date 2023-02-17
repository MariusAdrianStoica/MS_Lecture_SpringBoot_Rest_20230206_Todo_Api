package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception.DataDuplicateException;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception.DataNotFoundException;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.RoleDto;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.UserDto;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.User;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.repository.RoleRepository;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    //step1: add @Service in front of the class

    // when we need to use a method defined in another class -> we need dependency injection

    //@Autowired
    RoleRepository roleRepository;
    //@Autowired
    UserRepository userRepository;
    //@Autowired
    ModelMapper modelMapper;

    // but instead declaring @Autowired on each field, we can use a Constructor -> and add @Autowired there

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class }) // but we can define specific exceptions
    // in order to execute all methods in the same time and if one fails, DB should not be modified
    //if the register() method throws any exception, we want to roll-back all modified data
    public UserDto register(UserDto dto) {
        //step1: check all params - to create a User, we need username, password and List>Role>
        if (dto == null)throw new IllegalArgumentException("UserDto data was null");
        if (dto.getUsername() == null || dto.getPassword() == null)throw new IllegalArgumentException("Username or password data was null");
        if (dto.getRoles() == null || dto.getRoles().size() == 0)throw new IllegalArgumentException("role Data was null");

        //step2: check the roles Data - if there is not a valid RoleId
        for (RoleDto element: dto.getRoles()){
            //check if Role exists in DB -> findRoleById()
            roleRepository.findById(element.getId()).orElseThrow(()-> new DataNotFoundException("roleId was not valid"));
        }

        //step3: username is unique -> should not be duplicate

        if(userRepository.existsByUsername(dto.getUsername()))
            throw new DataDuplicateException("duplicate");


        //step4: convert UserDto to UserEntity
        User convertedToEntity = modelMapper.map(dto, User.class);

        //step5: execute save method in UserRepository
        User createdEntity = userRepository.save(convertedToEntity);
        //step6: convert created Entity to UserDto
        UserDto convertedToUserDto = modelMapper.map(createdEntity, UserDto.class);
        //step7: return it
        return convertedToUserDto;
    }

    @Override
    public Map<String, Object> findByUsername(String username) { //readonly method
        //step1: check the param
        if(username== null) throw new IllegalArgumentException("Username was null");
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new DataNotFoundException("Username not found error"));

        //we can define a Map == Collection
        Map< String , Object> map = new HashMap<>();
        // String == variable(key) name
        //Object == variable(key) value

        //creating the Json message
        map.put("username", user.getUsername());
        map.put("roles", user.getRoles());
        map.put("expired", user.isExpired());


        //in case of list , we use add - > list.add()
        //in case of map  , we use put - > map.put()

        return map;
    }


    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class })
    // it is better to use @Transactional(rollbackFor = {Exception.class } in all methods that modifies the DB
    public void disableUserByUsername(String username) {
        if(username== null) throw new IllegalArgumentException("Username was null");
        if(!userRepository.existsByUsername(username)) throw new DataNotFoundException("Username was not valid");
        userRepository.updateExpiredByUsername(username, true);

        // userRepository.updateExpiredByUsername(username, false); -> this is enable method
    }

}

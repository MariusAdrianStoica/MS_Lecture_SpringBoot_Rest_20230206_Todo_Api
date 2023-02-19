package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.UserDto;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Map;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/api/v1/user/")
@Validated
// if we put @Validated in front of the class, it will work for all class constraints except @RequestBody
// for methods with @Request - > add @Valid after @RequestBody

public class UserController {

    //Controller is responsible to control all the requests and make(return) the responses
    //step1: add @Controller & @ResponseBody // these 2 annotation == @RestController
    //@RestController -> responsible to  create Restfull web services available using HTTP protocol
    //step2: add @RequestMapping("/api/v1/user/") (user/role - depends on entity
    //step3: inject userService (entityService)

    @Autowired
    UserService userService;

    //creating the methods:

    //                          /           -> signup or register api       -> @RequestBody  -> POST

    //POST http://localhost:8080/api/v1/user/
    // requestBody {username: user, password: 123456, roles= {1, name : ADMIN....}

    @PostMapping("")
    //@RequestMapping(path = {}, method = RequestMethod.POST) -> this is the old version
    // in this case we can also use another method @RequestMapping

    public ResponseEntity<UserDto> signup(@RequestBody @Valid UserDto dto){
        // we have to add @RequestBody - when we want to create
        System.out.println("Username : "+ dto.getUsername());
        UserDto serviceResult = userService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResult);
    }




    //                          /{username} -> search or findBy             -> @PathVariable -> GET

    //GET http://localhost:8080/api/v1/user/ADMIN
    @GetMapping("{username}")
    public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable("username") @NotEmpty @Size(min = 4, max = 50) String un){
        // it is important that param name from @GetMapping is the same with one from @PathVariable (username)
        return ResponseEntity.ok().body(userService.findByUsername(un));
    }

    //enable/disable (update)   /{username} -> @PathVariable                -> @PathVariable -> PUT

    @PutMapping("disable")
    public ResponseEntity<Void> disableUserByUsername(@RequestParam("username") @NotEmpty @Size(min = 4, max = 50) String username){
        //using @RequestParam -> we can use different params : username, firstname, lastname, etc
        userService.disableUserByUsername(username);
        return ResponseEntity.noContent().build();

        //in postman, we can add more fields in Param section


    }


    //other type of HTTP methods

    //todo:

    // ! - when we run the app, DB will be recreated -> we can modify in app properties (Row32) from create to update
}

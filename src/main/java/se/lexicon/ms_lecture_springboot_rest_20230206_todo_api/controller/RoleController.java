package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.RoleDto;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.service.RoleService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role/")
@Validated // if we put @Validated in front of the class, it will work for all class constraints
public class RoleController {

    //Controller is responsible to control all the requests and make(return) the responses
    @Autowired
    RoleService roleService;

    //step1 -> add @RestController in front of the class Controller
    //http://localhost:8080/api/v1/role/ (IPAddress : port number)
    @GetMapping("") // Get is used in readonly mode
    // in order to publish this method as a web service -> @GetMapping
    // add the url
    // modify : public List<Role> getAll(){ -> public ResponseEntity<List<Role>> getAll(){
    //-> it will convert List<Role> into a Json message
    // modify return roles -> return ResponseEntity.ok(roles)

    // after creating Dto package, we can modify List<Role> -> List<RoleDto>
    public ResponseEntity<List<RoleDto>> getAll(){
        /*List<RoleDto> roles = new ArrayList<>();
        roles.add(new RoleDto(1, "ADMIN"));
        roles.add(new RoleDto(2, "USER"));
         */

        //return ResponseEntity.ok(roleService.getAll()); // ok = http response code 200

        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll());
    }

    @Operation(summary = "Get a role by its Id") // to print a description in Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Role", content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content})
    })
    @GetMapping("{id}") // if we use the same uri -> Postman don't know how to execute
    // -> add @PathVariable (before the Type of param
    // and add {id} to uri

    // because /api/v1/role/ it is the same -> we can put this in the beginning adding @RequestMapping("/api/v1/role/")

    //@Validated // @Validated is for parameters -> can be moved in front of the class
    public ResponseEntity<RoleDto> findById(@PathVariable("id") @Min(value = 1, message = "greater than 1") @Max(10) Integer id){
        //if we try to find after a letter -> we get error but not captured
        //-> we can add constraints using @Min and @Max, in order to work, add@Validated
        //@Min(1) @Max(10) -> we can't find after other numbers except 1-10
       return ResponseEntity.ok(roleService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        // delete returns Void -> modify the <RoleDto> to <Void>
        roleService.delete(id);
        //return ResponseEntity.noContent().build(); //204 == no content (delete)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("") // -> if you want to attach data to request and send data to back-end
    //if we create a role -> it will return a RoleDto
    @Operation(summary = "Create a role") // to print a description in Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create the Role",
                    content = {@Content(mediaType = "Application/JSON",
                            schema = @Schema(name = "Example", implementation = RoleDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Request Body supplied", content = {@Content})
    })
    // -> content = {@Content(mediaType = "Application/JSON" -
    // -> schema = @Schema(name = "Example", implementation = RoleDto.class))} -

    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto roleDto){

        // we need to validate RoleDto, in order to create a Role
        //-> we can use different types of annotation in RoleDto.class
        // after using annotations in RoleDto, in order to use these constraints
        // -> add @Valid after @RequestBody

        RoleDto createdRoleDto = roleService.create(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoleDto); // 201 == create
    }

    @PutMapping("") //update
    public ResponseEntity<Void> update(@RequestBody @Valid RoleDto roleDto){
        roleService.update(roleDto);
        return ResponseEntity.noContent().build(); // 204 == no content (update)
    }



    // HTTP method types:
    // -> Get       : readOnlyData , NOT modifying (findAll, findById, etc)
    // -> Post      : modifying Data (create/persist)
    // -> Put       : modifying Data (update)
    // -> Delete    : modifying Data (delete)


    //Last but not least, we have to create documentation for URI and ResponseCode,
    // that we used for each method
    // -> we can do it using Spring Framework in POM.xml after the last dependency
    // - but inside dependencies Row58 and reload Maven Changes
    /**
      <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-ui</artifactId>
        <version>1.6.9</version>
     </dependency>
     */
    // then we create a config package in the root to add config implementation

}

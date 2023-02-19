package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    // data transfer object

    // fields name should be the same as in Entity (Role)
    private int id;

    //we need to validate RoleDto, in order to create a Role
    //-> we can use different types of annotation in RoleDto.class

    /*@NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 40, message = "name should be between 2 - 40 characters")
     */
    // after these annotations, we must tell Controller in RequestBody- to use these constraints


    //even if we don¨t have defined a message-> annotation will create a message
    @NotEmpty
    @Size(min = 2, max = 40)
    private String name;
}

package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    // data transfer object

    // fields name should be the same as in Entity (Role)
    private int id;
    private String name;
}

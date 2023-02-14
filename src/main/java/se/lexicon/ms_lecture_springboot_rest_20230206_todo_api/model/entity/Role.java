package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity //without @Entity - > User is just a Java object & with @Entity, it became a TBL in DB
@Table(name = "roles")// exactly the same name we used in User Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) //PK can not be updated
    private int id;
    @Column(unique = true, nullable = false)
    private String name;
}

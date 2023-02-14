package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception.DataDuplicateException;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception.DataNotFoundException;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity //without @Entity - > User is just a Java object & with @Entity, it became a TBL in DB
@Table(name = "users")
public class User {

    @Id
    // we don't want to use generator -> we will ask for it from console
    @Column(updatable = false) //PK can not be updated
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean expired;

    /*
    -> OneToMany
    user A - Admin
    user A - User

    -> ManyToOne
    user A - Admin
    user B - Admin

    -> ManyToMany
    user A - Admin
    user B - user;
    user C - Guest
     */

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
    joinColumns = {@JoinColumn(name = "USERNAME")}, // It is a column name -> we can use USER_ID e.g.
    inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles = new HashSet<>();


    // when we have List/Set, we can add helper methods: Add & Remove
    public void addRole(Role role){
        if (role == null) throw new IllegalArgumentException("Role was  null");
        if (roles.contains(role)) throw new DataDuplicateException("Role was already in the list");
        roles.add(role);
    }

    public void removeRole(Role role){
        if (role == null) throw new IllegalArgumentException("Role was  null");
        if (!roles.contains(role)) throw new DataNotFoundException("Role was not on the list of roles");
            roles.remove(role);
    }

    //custom constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

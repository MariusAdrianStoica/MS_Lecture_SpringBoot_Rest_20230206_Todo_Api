package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    //in CrudRepo exists findById -> but this == findBy primary key (username), or to declare a new method:
    Optional<User> findByUsername(String username);

    Boolean existsByUsername( String username); // ~ existById from CrudRepo

    @Modifying // in order to update DB, we have to add @modifying
    @Query("update User u set u.expired = :expired where u.username= :username")
    void updateExpiredByUsername(@Param("username") String username, @Param("expired") boolean expired);

    @Modifying // update password using username
    @Query("update User u set u.password= :newPassword where u.username= :username")
    void resetPassword(@Param("username") String username, @Param("newPassword") String newPassword);
}



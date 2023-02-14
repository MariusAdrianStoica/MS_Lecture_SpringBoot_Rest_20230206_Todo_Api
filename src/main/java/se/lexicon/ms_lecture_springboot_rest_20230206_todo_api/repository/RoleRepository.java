package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    // select * from roles order by id desc
    List<Role> findAllByOrderByIdDesc(); // findAll from CrudRepo but ordered by id;



}

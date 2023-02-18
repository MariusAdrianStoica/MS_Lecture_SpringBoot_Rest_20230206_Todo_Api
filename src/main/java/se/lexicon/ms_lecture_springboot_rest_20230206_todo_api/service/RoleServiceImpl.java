package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.service;

import ch.qos.logback.core.subst.Token;
import jdk.nashorn.internal.parser.TokenType;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception.DataDuplicateException;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception.DataNotFoundException;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.RoleDto;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.Role;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// import the modelMapper class manually
import org.modelmapper.ModelMapper;

@Service
public class RoleServiceImpl implements RoleService{

    //step1: add @Service in front of the class
    //step2: DI : RoleRepository (@Autowired)

    @Autowired
    RoleRepository roleRepository;

    @Autowired // after defining as a @Bean in AppConfig, we can inject it here
    ModelMapper modelMapper;

    @Override
    public List<RoleDto> getAll() {

        List<Role> roleList = roleRepository.findAllByOrderByIdDesc();
        // we should convert roleList from Role to RoleDto

        /**List<RoleDto> roleDtoList= roleList.stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList());

        return roleDtoList;
         */ //- comment this in order to use ModelMapper

        return modelMapper.map(roleList,new TypeToken<List<RoleDto>>(){}.getType());
    }

    @Override
    public RoleDto findById(Integer roleId) {
        if (roleId == null)throw new IllegalArgumentException("roleId was null");
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        //if (optionalRole.isPresent()) { -> modified in order to handle exception
        //else  throw new DataNotFoundException("Data not found!");
        if (!optionalRole.isPresent()) throw new DataNotFoundException("RoleId was not valid");
           // convert the Role in to RoleDto
            Role entity = optionalRole.get();

            //RoleDto roleDto = new RoleDto(entity.getId(), entity.getName());
            //return roleDto;

            // -> convert using modelMapper

            return modelMapper.map( entity,RoleDto.class);
            // but modelMapper was not instantiated - -> we have NullPointerException
            //we will define it as a @Bean in AppConfig
        }


    @Override
    public RoleDto create(RoleDto roleDto) {
        if (roleDto == null) throw new IllegalArgumentException("role data was null");
        if (roleDto.getId() != 0)  throw new IllegalArgumentException("role Id should be null or zero");

        //todo: check if the roleDto.getName() already exists

        //Role convertedToEntity = new Role( roleDto.getName()); -> comment this in order to use modelMapper
        // we need to create Constructor inside the Role
        // we don't need roleId -> Name is enough

        //Role createdEntity = roleRepository.save(convertedToEntity);-> comment this in order to use modelMapper
        //because the return type is RoleDto -> we need to convert again RoleEntity to Dto

        Role createdEntity = roleRepository.save(modelMapper.map(roleDto, Role.class));


        //RoleDto convertedToRoleDto = new RoleDto(createdEntity.getId(), createdEntity.getName()); -> comment this in order to use modelMapper
        // because controller will work only with dto, not with entity
        //return convertedToRoleDto; -> comment this in order to use modelMapper

        return modelMapper.map(createdEntity, RoleDto.class);
    }

    @Override
    public void update(RoleDto roleDto) {

        //step 1 -> validate param
        if (roleDto == null) throw new IllegalArgumentException("role data was null");
        if (roleDto.getId() == 0)  throw new IllegalArgumentException("role Id should not be zero");

        if(!roleRepository.findById(roleDto.getId()).isPresent())throw new DataNotFoundException("data not found error");
        //because the name should be unique-> we have to check the name
        if (roleRepository.findByName(roleDto.getName()).isPresent()) throw new DataDuplicateException("Data duplicate exception!");

        //Role convertedToEntity =new Role(roleDto.getId(), roleDto.getName()); -> comment this in order to use modelMapper
        //roleRepository.save(convertedToEntity);  -> comment this in order to use modelMapper
        // we need to save Role but we have RoleDto -> conversion above
        roleRepository.save(modelMapper.map(roleDto, Role.class));
    }

    @Override
    public void delete(Integer roleId) {
        RoleDto toDelete = findById(roleId);
        if (toDelete == null) throw new DataNotFoundException("id was not valid");

        roleRepository.deleteById(roleId); // this is not enough
        // -> if roleId doesn't exist -> we must check first using findById
        //
    }

    // in order to convert entity to RoleDto and from RoleDto to Entity (all methods except delete), we can use a framework
    // -> add dependency modelMapper in Pom.xml and reload Maven project

    /**<dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.4.4</version>
        </dependency>
     */
}

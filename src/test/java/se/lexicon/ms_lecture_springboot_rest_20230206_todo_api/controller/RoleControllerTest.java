package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.dto.RoleDto;

@SpringBootTest
@AutoConfigureMockMvc //used to test Controller layer
@Transactional
public class RoleControllerTest {

    //step1: add @SpringBootTest
    //step2: add @AutoConfigureMockMvc

    @Autowired // transform it into a bean -> no need to instantiate
    private MockMvc mockMvc; //provides required pre-made methods to test controller
    // contains GET, POST, PUT, DELETE, etc operations


    ObjectMapper objectMapper; //if defined in AutoConfig we can add @Autowired
    // -> but in this case we will instantiate it manually -> in setup()

    @BeforeEach
    public void setup() throws Exception {
        String requestBodyAdmin = "{ \"name\": \"ADMIN\"}"; //JSON message
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/role/") //type of operation(POST) and path
                .content(requestBodyAdmin)      // requestBody
                .contentType(MediaType.APPLICATION_JSON)) // type of application -> Json
                .andExpect(MockMvcResultMatchers
                        .status().isCreated()) //status 201 Created
                .andExpect(MockMvcResultMatchers
                        .jsonPath("name") // What we get like field
                        .value("ADMIN")); // What we get like fieldValue

        String requestBodyUser = "{ \"name\": \"USER\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/role/").content(requestBodyUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("USER"));

        objectMapper = new ObjectMapper();
        // used to transform a Json message into a Java object with these 3 lines
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());

    }

    @Test
    public void test_findAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/role/"))    //findAll has no content
                .andDo(MockMvcResultHandlers.print())                          // print result in the console
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())                     //in order to use array -> use $[]
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))          //in order to check the content
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("USER"))  //in order to check the content

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))          //2nd element field1
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("ADMIN")); //2nd element field2
    }

    @Test
    public void test_createRole() throws Exception {
        String requestBodyGuest = "{ \"name\": \"GUEST\"}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/role/")
                        .content(requestBodyGuest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn(); // if we don't want to execute Expect method -> add .andReturn()

        int actualStatusCode = mvcResult.getResponse().getStatus();
        int expectedStatusCode = 201;

        Assertions.assertEquals(expectedStatusCode, actualStatusCode);

        String responseJsonBody = mvcResult.getResponse().getContentAsString();

        //transforming JSON into RoleDto object
        RoleDto actualRoleDto = objectMapper.readValue(responseJsonBody, RoleDto.class);
        RoleDto expectedRoleDto =new RoleDto(3, "GUEST");

        Assertions.assertEquals(expectedRoleDto.getName(), actualRoleDto.getName());
        // before comparing 2 Java objects, be sure that we have equals() & hashcode() methods in the specific class
        Assertions.assertEquals(expectedRoleDto, actualRoleDto);


    }

}

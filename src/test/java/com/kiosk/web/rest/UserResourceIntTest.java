package com.kiosk.web.rest;

import com.kiosk.KioskApp;
import com.kiosk.domain.User;
import com.kiosk.repository.UserRepository;
import com.kiosk.service.UserService;
import com.kiosk.web.rest.dto.UserDTO;
import com.kiosk.web.rest.mapper.UserMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KioskApp.class)
@WebAppConfiguration
@IntegrationTest
public class UserResourceIntTest {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBB";
    private static final String DEFAULT_CUSTOMER_DETAILS = "AAAAA";
    private static final String UPDATED_CUSTOMER_DETAILS = "BBBBB";

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserMapper userMapper;

    @Inject
    private UserService userService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserMockMvc;

    private User user;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserResource userResource = new UserResource();
        ReflectionTestUtils.setField(userResource, "userService", userService);
        ReflectionTestUtils.setField(userResource, "userMapper", userMapper);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        user = new User();
        user.setCustomerName(DEFAULT_CUSTOMER_NAME);
        user.setCustomerDetails(DEFAULT_CUSTOMER_DETAILS);
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User
        UserDTO userDTO = userMapper.userToUserDTO(user);

        restUserMockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isCreated());

        // Validate the User in the database
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = users.get(users.size() - 1);
        assertThat(testUser.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testUser.getCustomerDetails()).isEqualTo(DEFAULT_CUSTOMER_DETAILS);
    }

    @Test
    @Transactional
    public void checkCustomerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        // set the field null
        user.setCustomerName(null);

        // Create the User, which fails.
        UserDTO userDTO = userMapper.userToUserDTO(user);

        restUserMockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc.perform(get("/api/users?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(user.getId().intValue())))
                .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
                .andExpect(jsonPath("$.[*].customerDetails").value(hasItem(DEFAULT_CUSTOMER_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get the user
        restUserMockMvc.perform(get("/api/users/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(user.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.customerDetails").value(DEFAULT_CUSTOMER_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        // Get the user
        restUserMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setCustomerName(UPDATED_CUSTOMER_NAME);
        updatedUser.setCustomerDetails(UPDATED_CUSTOMER_DETAILS);
        UserDTO userDTO = userMapper.userToUserDTO(updatedUser);

        restUserMockMvc.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(databaseSizeBeforeUpdate);
        User testUser = users.get(users.size() - 1);
        assertThat(testUser.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testUser.getCustomerDetails()).isEqualTo(UPDATED_CUSTOMER_DETAILS);
    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Get the user
        restUserMockMvc.perform(delete("/api/users/{id}", user.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(databaseSizeBeforeDelete - 1);
    }
}

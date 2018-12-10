package amm.web.rest;

import amm.ArtistsApp;

import amm.domain.Group0;
import amm.repository.Group0Repository;
import amm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static amm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Group0Resource REST controller.
 *
 * @see Group0Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtistsApp.class)
public class Group0ResourceIntTest {

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    @Autowired
    private Group0Repository group0Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroup0MockMvc;

    private Group0 group0;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Group0Resource group0Resource = new Group0Resource(group0Repository);
        this.restGroup0MockMvc = MockMvcBuilders.standaloneSetup(group0Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Group0 createEntity(EntityManager em) {
        Group0 group0 = new Group0()
            .nickname(DEFAULT_NICKNAME);
        return group0;
    }

    @Before
    public void initTest() {
        group0 = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroup0() throws Exception {
        int databaseSizeBeforeCreate = group0Repository.findAll().size();

        // Create the Group0
        restGroup0MockMvc.perform(post("/api/group-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group0)))
            .andExpect(status().isCreated());

        // Validate the Group0 in the database
        List<Group0> group0List = group0Repository.findAll();
        assertThat(group0List).hasSize(databaseSizeBeforeCreate + 1);
        Group0 testGroup0 = group0List.get(group0List.size() - 1);
        assertThat(testGroup0.getNickname()).isEqualTo(DEFAULT_NICKNAME);
    }

    @Test
    @Transactional
    public void createGroup0WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = group0Repository.findAll().size();

        // Create the Group0 with an existing ID
        group0.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroup0MockMvc.perform(post("/api/group-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group0)))
            .andExpect(status().isBadRequest());

        // Validate the Group0 in the database
        List<Group0> group0List = group0Repository.findAll();
        assertThat(group0List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNicknameIsRequired() throws Exception {
        int databaseSizeBeforeTest = group0Repository.findAll().size();
        // set the field null
        group0.setNickname(null);

        // Create the Group0, which fails.

        restGroup0MockMvc.perform(post("/api/group-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group0)))
            .andExpect(status().isBadRequest());

        List<Group0> group0List = group0Repository.findAll();
        assertThat(group0List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroup0S() throws Exception {
        // Initialize the database
        group0Repository.saveAndFlush(group0);

        // Get all the group0List
        restGroup0MockMvc.perform(get("/api/group-0-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(group0.getId().intValue())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getGroup0() throws Exception {
        // Initialize the database
        group0Repository.saveAndFlush(group0);

        // Get the group0
        restGroup0MockMvc.perform(get("/api/group-0-s/{id}", group0.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(group0.getId().intValue()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGroup0() throws Exception {
        // Get the group0
        restGroup0MockMvc.perform(get("/api/group-0-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroup0() throws Exception {
        // Initialize the database
        group0Repository.saveAndFlush(group0);

        int databaseSizeBeforeUpdate = group0Repository.findAll().size();

        // Update the group0
        Group0 updatedGroup0 = group0Repository.findById(group0.getId()).get();
        // Disconnect from session so that the updates on updatedGroup0 are not directly saved in db
        em.detach(updatedGroup0);
        updatedGroup0
            .nickname(UPDATED_NICKNAME);

        restGroup0MockMvc.perform(put("/api/group-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroup0)))
            .andExpect(status().isOk());

        // Validate the Group0 in the database
        List<Group0> group0List = group0Repository.findAll();
        assertThat(group0List).hasSize(databaseSizeBeforeUpdate);
        Group0 testGroup0 = group0List.get(group0List.size() - 1);
        assertThat(testGroup0.getNickname()).isEqualTo(UPDATED_NICKNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGroup0() throws Exception {
        int databaseSizeBeforeUpdate = group0Repository.findAll().size();

        // Create the Group0

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroup0MockMvc.perform(put("/api/group-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group0)))
            .andExpect(status().isBadRequest());

        // Validate the Group0 in the database
        List<Group0> group0List = group0Repository.findAll();
        assertThat(group0List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroup0() throws Exception {
        // Initialize the database
        group0Repository.saveAndFlush(group0);

        int databaseSizeBeforeDelete = group0Repository.findAll().size();

        // Get the group0
        restGroup0MockMvc.perform(delete("/api/group-0-s/{id}", group0.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Group0> group0List = group0Repository.findAll();
        assertThat(group0List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Group0.class);
        Group0 group01 = new Group0();
        group01.setId(1L);
        Group0 group02 = new Group0();
        group02.setId(group01.getId());
        assertThat(group01).isEqualTo(group02);
        group02.setId(2L);
        assertThat(group01).isNotEqualTo(group02);
        group01.setId(null);
        assertThat(group01).isNotEqualTo(group02);
    }
}

package amm.web.rest;

import amm.ArtistsApp;

import amm.domain.Artist;
import amm.repository.ArtistRepository;
import amm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static amm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import amm.domain.enumeration.Sex;
/**
 * Test class for the ArtistResource REST controller.
 *
 * @see ArtistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtistsApp.class)
public class ArtistResourceIntTest {

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final Sex DEFAULT_SEX = Sex.MAN;
    private static final Sex UPDATED_SEX = Sex.WOMAN;

    private static final String DEFAULT_SITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_URL = "BBBBBBBBBB";

    @Autowired
    private ArtistRepository artistRepository;

    @Mock
    private ArtistRepository artistRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArtistMockMvc;

    private Artist artist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArtistResource artistResource = new ArtistResource(artistRepository);
        this.restArtistMockMvc = MockMvcBuilders.standaloneSetup(artistResource)
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
    public static Artist createEntity(EntityManager em) {
        Artist artist = new Artist()
            .nickname(DEFAULT_NICKNAME)
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .birthday(DEFAULT_BIRTHDAY)
            .sex(DEFAULT_SEX)
            .siteUrl(DEFAULT_SITE_URL);
        return artist;
    }

    @Before
    public void initTest() {
        artist = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtist() throws Exception {
        int databaseSizeBeforeCreate = artistRepository.findAll().size();

        // Create the Artist
        restArtistMockMvc.perform(post("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isCreated());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeCreate + 1);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArtist.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testArtist.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testArtist.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testArtist.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
    }

    @Test
    @Transactional
    public void createArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artistRepository.findAll().size();

        // Create the Artist with an existing ID
        artist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistMockMvc.perform(post("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNicknameIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistRepository.findAll().size();
        // set the field null
        artist.setNickname(null);

        // Create the Artist, which fails.

        restArtistMockMvc.perform(post("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistRepository.findAll().size();
        // set the field null
        artist.setName(null);

        // Create the Artist, which fails.

        restArtistMockMvc.perform(post("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistRepository.findAll().size();
        // set the field null
        artist.setSex(null);

        // Create the Artist, which fails.

        restArtistMockMvc.perform(post("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArtists() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList
        restArtistMockMvc.perform(get("/api/artists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artist.getId().intValue())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllArtistsWithEagerRelationshipsIsEnabled() throws Exception {
        ArtistResource artistResource = new ArtistResource(artistRepositoryMock);
        when(artistRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restArtistMockMvc = MockMvcBuilders.standaloneSetup(artistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArtistMockMvc.perform(get("/api/artists?eagerload=true"))
        .andExpect(status().isOk());

        verify(artistRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllArtistsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ArtistResource artistResource = new ArtistResource(artistRepositoryMock);
            when(artistRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restArtistMockMvc = MockMvcBuilders.standaloneSetup(artistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArtistMockMvc.perform(get("/api/artists?eagerload=true"))
        .andExpect(status().isOk());

            verify(artistRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get the artist
        restArtistMockMvc.perform(get("/api/artists/{id}", artist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artist.getId().intValue()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.siteUrl").value(DEFAULT_SITE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArtist() throws Exception {
        // Get the artist
        restArtistMockMvc.perform(get("/api/artists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // Update the artist
        Artist updatedArtist = artistRepository.findById(artist.getId()).get();
        // Disconnect from session so that the updates on updatedArtist are not directly saved in db
        em.detach(updatedArtist);
        updatedArtist
            .nickname(UPDATED_NICKNAME)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .birthday(UPDATED_BIRTHDAY)
            .sex(UPDATED_SEX)
            .siteUrl(UPDATED_SITE_URL);

        restArtistMockMvc.perform(put("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtist)))
            .andExpect(status().isOk());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArtist.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testArtist.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testArtist.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testArtist.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // Create the Artist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistMockMvc.perform(put("/api/artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeDelete = artistRepository.findAll().size();

        // Get the artist
        restArtistMockMvc.perform(delete("/api/artists/{id}", artist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artist.class);
        Artist artist1 = new Artist();
        artist1.setId(1L);
        Artist artist2 = new Artist();
        artist2.setId(artist1.getId());
        assertThat(artist1).isEqualTo(artist2);
        artist2.setId(2L);
        assertThat(artist1).isNotEqualTo(artist2);
        artist1.setId(null);
        assertThat(artist1).isNotEqualTo(artist2);
    }
}

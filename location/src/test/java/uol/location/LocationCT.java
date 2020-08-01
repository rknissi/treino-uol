package uol.location;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uol.location.location.application.LocationApplication;
import uol.location.location.objects.Location;
import uol.location.location.repository.LocationRepository;
import uol.location.location.repository.objects.LocationRepositoryEntity;
import uol.location.location.repository.objects.WeatherRepositoryEntity;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class LocationCT {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    LocationApplication locationApplication;

    @Autowired
    LocationRepository locationRepository;

    @Before
    public void populateDatabase() {
        addLocation(1L, "Brazil", "SP", 1L, 20L, 10L);
    }

    @Test
    public void createLocationCT() {
        Location location = locationApplication.create("127.0.0.1");

        Assert.assertEquals("2", location.getId().toString());
        Assert.assertNull(location.getCountry());
        Assert.assertNull(location.getCity());
        Assert.assertNull(location.getWeather().getId());
        Assert.assertNull(location.getWeather().getMaxTemp());
        Assert.assertNull(location.getWeather().getMinTemp());
    }

    @Test
    public void deleteLocationById() {
        addLocation(2L, "Teste", "Teste2", 2L, 10L, 5L);
        Boolean aBoolean = locationApplication.deleteById(2L);

        Assert.assertEquals(true, aBoolean);
    }


    @Test
    public void getLocationByIdCT() {
        Location location = locationApplication.getById(1L);

        Assert.assertEquals("1", location.getId().toString());
        Assert.assertEquals("Brazil", location.getCountry());
        Assert.assertEquals("SP", location.getCity());
        Assert.assertEquals("1", location.getWeather().getId().toString());
        Assert.assertEquals("20", location.getWeather().getMaxTemp().toString());
        Assert.assertEquals("10", location.getWeather().getMinTemp().toString());
    }

    private void addLocation(Long id, String country, String city, Long weatherId, Long maxTemp, Long minTemp) {
        LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();

        locationRepositoryEntity.setId(id);
        locationRepositoryEntity.setCountry(country);
        locationRepositoryEntity.setCity(city);

        WeatherRepositoryEntity weatherRepositoryEntity = new WeatherRepositoryEntity();
        weatherRepositoryEntity.setId(weatherId);
        weatherRepositoryEntity.setMaxTemp(maxTemp);
        weatherRepositoryEntity.setMinTemp(minTemp);

        locationRepositoryEntity.setWeather(weatherRepositoryEntity);

        locationRepository.save(locationRepositoryEntity);
    }



}

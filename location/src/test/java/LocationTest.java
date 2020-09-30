import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uol.location.Application;
import uol.location.location.application.LocationApplication;
import uol.location.location.domain.Location;
import uol.location.location.queue.LocationCreationMessage;
import uol.location.location.repository.LocationRepository;
import uol.location.location.repository.entity.LocationRepositoryEntity;
import uol.location.location.repository.entity.WeatherRepositoryEntity;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    LocationApplication locationApplication;

    @Autowired
    LocationRepository locationRepository;

    @Before
    public void populateDatabase() throws Exception {
        Broker broker = new Broker();
        BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", "6000");
        brokerOptions.setConfigProperty("qpid.broker.defaultPreferenceStoreAttributes", "{\"type\": \"Noop\"}");
        brokerOptions.setConfigProperty("qpid.vhost", "localhost");
        brokerOptions.setConfigurationStoreType("Memory");
        brokerOptions.setStartupLoggedToSystemOut(false);
        broker.startup(brokerOptions);


        addLocation(1L, "Brazil", "São Paulo", 20L, 10L);
        addLocation(2L, "Brazil", "São Paulo", 20L, 10L);
    }

    @Test
    public void createLocationCT() throws InterruptedException {
        LocationCreationMessage locationCreationMessage = new LocationCreationMessage();
        locationCreationMessage.setId(5L);
        locationCreationMessage.setIp("127.0.0.1");
        locationApplication.populateData(locationCreationMessage);

        Location location = locationApplication.getById(5L);

        Assert.assertThat(location.getId(), is(notNullValue()));
        Assert.assertEquals("Brazil", location.getCountry());
        Assert.assertEquals("São Paulo", location.getCity());
        Assert.assertThat(location.getWeather().getId(), is(notNullValue()));
        Assert.assertEquals("25", location.getWeather().getMaxTemp().toString());
        Assert.assertEquals("14", location.getWeather().getMinTemp().toString());
    }

    @Test
    public void deleteLocationById() {
        addLocation(3L, "Teste", "Teste2", 10L, 5L);
        Boolean aBoolean = locationApplication.deleteById(3L);

        Assert.assertEquals(true, aBoolean);
    }


    @Test
    public void getLocationByIdCT() {
        addLocation(4L, "Teste", "Teste2", 10L, 5L);
        Location location = locationApplication.getById(4L);

        Assert.assertThat(location.getId(), is(notNullValue()));
        Assert.assertEquals("Teste", location.getCountry());
        Assert.assertEquals("Teste2", location.getCity());
        Assert.assertThat(location.getWeather().getId(), is(notNullValue()));
        Assert.assertEquals("10", location.getWeather().getMaxTemp().toString());
        Assert.assertEquals("5", location.getWeather().getMinTemp().toString());
    }

    private void addLocation(Long personId, String country, String city, Long maxTemp, Long minTemp) {
        LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();

        locationRepositoryEntity.setPersonId(personId);
        locationRepositoryEntity.setCountry(country);
        locationRepositoryEntity.setCity(city);

        WeatherRepositoryEntity weatherRepositoryEntity = new WeatherRepositoryEntity();
        weatherRepositoryEntity.setMaxTemp(maxTemp);
        weatherRepositoryEntity.setMinTemp(minTemp);

        locationRepositoryEntity.setWeather(weatherRepositoryEntity);

        locationRepository.save(locationRepositoryEntity);
    }



}

package rest.client;

import game.services.rest.ServiceException;
import model.Meci;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class GameClient {
    public static final String URL = "http://localhost:8080/basketball/games";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws Exception {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) {
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Meci[] getAll() throws Exception {
        return execute(() -> restTemplate.getForObject(URL, Meci[].class));
    }

    public Meci getById(Integer id) throws Exception {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Meci.class));
    }

    public Meci create(Meci meci) throws Exception {
        return execute(() -> restTemplate.postForObject(URL, meci, Meci.class));
    }

    public void update(Meci meci, int s) throws Exception {
        execute(() -> {
            restTemplate.put(String.format("%s/%s/%s", URL, meci.getId(), s), null);
            return null;
        });
    }

    public void delete(Integer id) throws Exception {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}

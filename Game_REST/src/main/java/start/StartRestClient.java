package start;

import game.services.rest.ServiceException;
import model.Meci;
import org.springframework.web.client.RestClientException;
import rest.client.GameClient;

public class StartRestClient {
    private final static GameClient gameClient=new GameClient();
    public static void main(String[] args) {
        Meci meciT=new Meci("Jazz","Heat", "Regular season", 30000);
        try{
            show(()-> {
                try {
                    System.out.println(gameClient.create(meciT));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            show(()->{
                Meci[] res = new Meci[0];
                try {
                    res = gameClient.getAll();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for(Meci u:res){
                    System.out.println(u.getId()+": "+u.getHome()+" "+u.getAway());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... " + ex.getMessage());
        }

        show(()-> {
            try {
                System.out.println(gameClient.getById(6));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service exception"+ e);
        }
    }
}

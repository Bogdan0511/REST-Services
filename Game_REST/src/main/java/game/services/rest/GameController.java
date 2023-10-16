package game.services.rest;


import jdbc.MeciDBRepo;
import model.Meci;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistence.RepositoryException;

import java.util.List;


@RestController
@RequestMapping("/basketball/games")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    private static final String template = "Hello, %s!";

    @Autowired
    private MeciDBRepo gameRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Meci[] getAll(){
        System.out.println("Get all games ...");
        return ((List<Meci>)gameRepository.findAll()).toArray(new Meci[0]);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id) throws RepositoryException {
        Integer myId = Integer.parseInt(id);
        System.out.println("Get by id "+id);
        Meci meci = gameRepository.find(myId);
        if (meci==null)
            return new ResponseEntity<String>("Game not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Meci>(meci, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Meci create(@RequestBody Meci meci){
        System.out.println("Creating a new game ...");
        return gameRepository.save(meci);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Meci meci) throws RepositoryException{
        System.out.println("Updating game with id ... " + id);
        Integer myId = Integer.parseInt(id);
        Meci searched = gameRepository.find(myId);
        if (searched == null)
            return new ResponseEntity<String>("Game not found", HttpStatus.NOT_FOUND);
        else {
            Meci updated = gameRepository.update(meci, myId);
            updated.setId(myId);
            return new ResponseEntity<Meci>(updated, HttpStatus.OK);
        }
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting game with id ... "+ id );
        try {
            Integer myId = Integer.parseInt(id);
            Meci searched = gameRepository.find(myId);
            if (searched == null) {
                return new ResponseEntity<String>("Game not found", HttpStatus.NOT_FOUND);
            }
            else {
                gameRepository.delete_id(myId);
                return new ResponseEntity<String>("Game deleted succesfully", HttpStatus.OK);
            }
        }catch (Exception ex){
            System.out.println("Ctrl Delete game exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}

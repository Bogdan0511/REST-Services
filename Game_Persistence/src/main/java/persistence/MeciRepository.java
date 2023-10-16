package persistence;


import model.Entity;
import model.Meci;

import java.util.List;

public interface MeciRepository extends Repository<Meci, Entity<Integer>>{
    Meci find(Integer id) throws RepositoryException;
    List<Meci> findAll();
    Meci save(Meci meci);
    Meci delete(Meci meci);
    Meci update(Meci meci, int s);
}

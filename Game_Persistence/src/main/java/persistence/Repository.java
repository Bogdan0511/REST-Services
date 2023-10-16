package persistence;

import java.util.List;

public interface Repository<E, ID>{

    E find(ID id) throws RepositoryException;
    List<E> findAll();
    E save(E entity) throws RepositoryException;
    E delete(E entity) throws RepositoryException;
    E update(E updateEntity) throws RepositoryException;
}
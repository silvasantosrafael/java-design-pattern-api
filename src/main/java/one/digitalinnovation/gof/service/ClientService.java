package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Client;

public interface ClientService {
    Iterable<Client> findAll();
    Client findById(Long id);
    void save(Client Client);
    void update(Client Client);
    void delete(Long id);
}

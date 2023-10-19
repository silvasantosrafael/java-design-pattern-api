package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.model.Address;
import one.digitalinnovation.gof.model.AddressRepository;
import one.digitalinnovation.gof.model.Client;
import one.digitalinnovation.gof.model.ClientRepository;
import one.digitalinnovation.gof.service.ClientService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository ClientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Client> findAll() {
        return ClientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> client = ClientRepository.findById(id);
        return client.get();
    }

    @Override

    public void save(Client client) {
        saveWithCep(client);
    }

    @Override
    public void update(Client client) {
        Optional<Client> clientBd = ClientRepository.findById(client.getId());
        if (clientBd.isPresent()) {
            saveWithCep(client);
        }
    }

    @Override
    public void delete(Long id) {
        ClientRepository.deleteById(id);
    }

    private void saveWithCep(Client client) {
        String cep = client.getAddress().getCep();
        Address address = addressRepository.findById(cep).orElseGet(() -> {
            Address newAddress = viaCepService.checkCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });

        client.setAddress(address);
        ClientRepository.save(client);
    }

}
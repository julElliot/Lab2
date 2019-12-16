package by.bsuir.service;

import by.bsuir.bean.Client;
import by.bsuir.dao.DaoClient;

import java.util.List;

/**
 * The type Client service.
 */
public class ClientService implements Service<Client> {
    private DaoClient daoClient;

    /**
     * Instantiates a new Client service.
     *
     * @param daoClient the dao client
     */
    public ClientService(DaoClient daoClient) {
        this.daoClient = daoClient;
    }

    @Override
    public List<Client> getAll() {
        return daoClient.getAll();
    }
}
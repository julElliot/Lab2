package by.bsuir.dao;

import by.bsuir.bean.Client;
import by.bsuir.parser.ClientXmlParser;

import java.util.List;

/**
 * The type Dao client.
 */
public class DaoClient implements Dao<Client> {
    private static final String NOT_EXIST = "This client isn't exist";

    private ClientXmlParser xmlParser;

    /**
     * Instantiates a new Dao client.
     */
    public DaoClient() {
    }

    /**
     * Instantiates a new Dao client.
     *
     * @param clientXmlParser the client xml parser
     */
    public DaoClient(ClientXmlParser clientXmlParser) {
        this.xmlParser = clientXmlParser;
    }

    public List<Client> getAll() {
        return xmlParser.getData();
    }

    @Override
    public Client get(int id) throws DaoException {
        return xmlParser.getData().stream().filter(x -> x.getId() == id).findFirst()
                .orElseThrow(() -> new DaoException(NOT_EXIST));
    }
}
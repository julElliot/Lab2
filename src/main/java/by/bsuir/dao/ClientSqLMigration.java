package by.bsuir.dao;

import by.bsuir.bean.Client;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Client sq l migration.
 */
public class ClientSqLMigration extends BaseMysql<Client> implements Migration<Client> {
    private static final String INSERT_CLIENT = "insert into `client` (`id`, `name`, `surname`, `discount`, `login`, `password`) values (?, ?, ?, ?, ?, ?)";
    private static final String READ_ID = "select c.id from `client` c";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String DISCOUNT = "discount";

    private static final Logger logger = Logger.getLogger(ClientSqLMigration.class);

    /**
     * Instantiates a new Client sq l migration.
     */
    public ClientSqLMigration() {
    }

    @Override
    protected void setFieldStatement(PreparedStatement statement, Client entity) throws SQLException {
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getSurname());
        statement.setInt(4, entity.getDiscount());
        statement.setString(5, entity.getLogin());
        statement.setString(6, entity.getPassword());
    }

    @Override
    public void create(Client entity) throws SQLException {
        if (getClientIds().stream().noneMatch(x -> x == entity.getId())) {
            defaultCreate(INSERT_CLIENT, entity);
            return;
        }

        logger.warn("WARN: id = " + entity.getId() + "; the client with that id already exists");
    }

    private List<Integer> getClientIds() throws SQLException {
        var resultSet = statement.executeQuery(READ_ID);
        var ids = new ArrayList<Integer>();

        while (resultSet.next()) {
            ids.add(resultSet.getInt(1));
        }

        resultSet.close();

        return ids;
    }
}
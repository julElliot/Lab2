package by.bsuir.service.migration;

import javax.sql.rowset.serial.SerialException;

/**
 * The interface Migration service.
 */
public interface MigrationService {
    /**
     * Migrate.
     *
     * @throws SerialException the serial exception
     */
    void Migrate() throws SerialException;
}
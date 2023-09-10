package org.example.data;

import org.apache.log4j.Logger;
import org.example.models.Member;
import org.flywaydb.core.Flyway;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.example.data.Config.*;

public class OsbbCrud implements Closeable {

    private String sqlMembersWithAutoNotAllowedQuery =
            "SELECT " +
                    "m.id AS id, " +
                    "m.name AS owner_name, " +
                    "m.email AS owner_email, " +
                    "b.address AS building_address, " +
                    "f.number AS flat_number, " +
                    "f.square AS flat_square " +
                    "FROM members m " +
                    "JOIN flats f ON m.flat_id = f.id " +
                    "JOIN buildings b ON f.building_id = b.id " +
                    "WHERE m.access_right = 0 AND m.flat_ownership < 2";

    private String sqlMembersByIdQuery = "SELECT * FROM members WHERE id = ?";

    private Connection connection = null;
    private static final Logger logger = Logger.getLogger(OsbbCrud.class);

    public OsbbCrud init() {
        logger.info("Crud has initialized");
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public void fwMigration() {
        logger.debug("Flyway migration execute");
        Flyway.configure()
                .dataSource(JDBC_URL, USERNAME, PASSWORD)
                .locations("classpath:flyway/scripts")
                .load()
                .migrate();
    }

    public List<Member> getMembersWithAutoNotAllowed() {
        logger.trace("Call getting members with auto not allowed method");
        final List<Member> result = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlMembersWithAutoNotAllowedQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new Member()
                        .setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("owner_name"))
                        .setEmail(resultSet.getString("owner_email"))
                        .setBuildingAddress(resultSet.getString("building_address"))
                        .setFlatNumber(resultSet.getInt("flat_number"))
                        .setFlatSquare(resultSet.getFloat("flat_square"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public Optional<Member> getMemberById(final int id) {
        logger.trace("Call getting member by ID method");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlMembersByIdQuery)) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return Optional.of(
                        new Member()
                                .setId(resultSet.getInt(1))
                                .setName(resultSet.getString(2))
                );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

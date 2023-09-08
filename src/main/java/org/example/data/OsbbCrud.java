package org.example.data;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;


import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import static org.example.Config.*;

public class OsbbCrud implements Closeable {

    private static String sqlMembersWithAutoNotAllowedQuery = "SELECT m. *\n";
    private static String sqlMembersByIdQuery = "SELECT *";
    private Connection connection = null;
    private static final Logger logger = Logger.getLogger(OsbbCrud.class);

    public OsbbCrud init() {
        logger.info("Crud has initialized");
        fwMigration();

        try {
            connection= DriverManager.getConnection(jdbcUrl,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this;
    }
    private void fwMigration (){
        logger.debug("Flyway migration execute");

        Flyway.configure()
                .dataSource(jdbcUrl, username, password)
                .locations("classpath:flyway/scripts")
                .load()
                .migrate();
    }
    public List<Member> getMembersWithAutoNotAllowed () {
        logger.trace("Call getting members with auto not allowed method");
    final List <Member> result = new LinkedList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlMembersWithAutoNotAllowedQuery)){
        final ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()) {
            result.add (new Member()
                    .setId (resultSet.getInt(1))
                    .setName(resultSet.getString(2)));
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return result;
    }
    public Optional<Member> getMemberById (final int id )  {
        logger.trace("Call getting member by ID method");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlMembersByIdQuery)) {
           preparedStatement.setInt(1,id);
           preparedStatement.setString(2, "Nikolay");
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return Optional.of(
                        new Member()
                                .setId(resultSet.getInt(1))
                                .setName(resultSet.getString(2))
                );
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
    }
        return Optional.empty();
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
            connection=null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

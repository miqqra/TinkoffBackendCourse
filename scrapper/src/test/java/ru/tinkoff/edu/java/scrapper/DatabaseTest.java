package ru.tinkoff.edu.java.scrapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DatabaseTest extends IntegrationEnvironment {
    @BeforeAll
    static void setConnection() {
        runMigrations(DB_CONTAINER);
    }

    @Test
    public void connectionTest() throws SQLException {
        try (var conn = DriverManager.getConnection(
                DB_CONTAINER.getJdbcUrl(),
                DB_CONTAINER.getUsername(),
                DB_CONTAINER.getPassword())) {

            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"});
            List<String> tableNames = new ArrayList<>();
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }

            assertThat(tableNames, is(notNullValue()));
            assertThat(tableNames, is(not(empty())));
            assertThat(tableNames.size(), equalTo(4));
            assertThat(tableNames.get(0), equalTo("chat"));
            assertThat(tableNames.get(1), equalTo("databasechangelog"));
            assertThat(tableNames.get(2), equalTo("databasechangeloglock"));
            assertThat(tableNames.get(3), equalTo("link"));
        }
    }

    @Test
    public void fillDataTest() {
        String SQLRequest = "select id from scrapper.public.chat;";

        String query = """
                INSERT INTO link(id, urL)
                values (1, 'github.com');
                                
                INSERT INTO link(id, urL)
                values (2, 'vk.com');
                                
                INSERT INTO link(id, urL)
                values (3, 'stackoverflow.com');
                                
                INSERT INTO link(id, urL)
                values (4, 'mit.com');
                                
                INSERT INTO chat(id, tgchatid, trackedlink)
                    values (100, 200, 1);
                                
                INSERT INTO chat(id, tgchatid, trackedlink)
                    values (101, 200, 2);
                                
                INSERT INTO chat(id, tgchatid, trackedlink)
                    values (102, 200, 3);
                """;

        try (
                Connection connection = DriverManager.getConnection(
                        DB_CONTAINER.getJdbcUrl(),
                        DB_CONTAINER.getUsername(),
                        DB_CONTAINER.getPassword());
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
            ResultSet resultSet = statement.executeQuery(SQLRequest);
            List<Long> ids = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getLong("id"));
            }

            assertThat(ids, is(notNullValue()));
            assertThat(ids.size(), is(equalTo(3)));
            assertThat(ids.get(0), is(equalTo(100L)));
            assertThat(ids.get(1), is(equalTo(101L)));
            assertThat(ids.get(2), is(equalTo(102L)));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

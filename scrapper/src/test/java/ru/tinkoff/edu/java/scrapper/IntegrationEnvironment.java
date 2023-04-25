package ru.tinkoff.edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public class IntegrationEnvironment {
    public static JdbcDatabaseContainer<?> DB_CONTAINER;
    public static JdbcConnection connection;

    static {
        DB_CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("scrapper")
                .withUsername("scrapper")
                .withPassword("scrapper");
        DB_CONTAINER.start();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DB_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTAINER::getPassword);
    }

    static void runMigrations(JdbcDatabaseContainer<?> container) {
        var changelogPath = new File(".").toPath().toAbsolutePath()
                .getParent().resolve("src/test/resources/test-migrations");
        try (var conn = DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())) {
            var changelogDir = new DirectoryResourceAccessor(changelogPath);

            var db = new PostgresDatabase();
            connection = new JdbcConnection(conn);
            db.setConnection(new JdbcConnection(conn));

            var liquibase = new Liquibase("master.xml", changelogDir, db);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException | FileNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public JdbcConnection getConnection() {
        return connection;
    }
}

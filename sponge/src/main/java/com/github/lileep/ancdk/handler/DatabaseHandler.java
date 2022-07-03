package com.github.lileep.ancdk.handler;

import com.github.lileep.ancdk.config.ConfigLoader;
import javafx.util.Pair;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    private SqlService service;
    private DataSource dataSource;

    public DatabaseHandler() {
        instance = this;
        this.setService();
        this.setDataSource();
        this.createDatabase();
        this.createTable();
    }

    private void setService() {
        service = Sponge.getServiceManager().provide(SqlService.class).get();
    }

    private void setDataSource() {
        if (service == null) {
            setService();
        }
        try {
            ConfigurationNode dbNode = ConfigLoader.getInstance().getRootNode().getNode("Database");
            dataSource = service.getDataSource(
                    "jdbc:mysql://"
                            + dbNode.getNode("databaseIP").getString() + ":"
                            + dbNode.getNode("databasePort").getString()
                            + "/?user=" + dbNode.getNode("databaseUsername").getString()
                            + "&password=" + dbNode.getNode("databasePassword").getString()
                            + "&useSSL=false&characterEncoding=UTF-8&serverTimezone=" + TimeZone.getDefault().getID()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase() {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "create database if not exists " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString()
                )
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement cdksOnceTable = connection.prepareStatement(
                        "create table if not exists " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks" +
                                "(cdk varchar(16)," +
                                "command tinytext not null," +
                                "once tinyint default 0," +
                                "index(once)," +
                                "primary key (cdk))Engine=InnoDB default charset=utf8mb4"
                );
                PreparedStatement cdksTable = connection.prepareStatement(
                        "create table if not exists " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks_used" +
                                "(cdk varchar(16)," +
                                "used_player varchar(32) not null," +
                                "index(cdk)," +
                                "index(used_player))Engine=InnoDB default charset=utf8mb4"
                )
        ) {
            cdksOnceTable.execute();
            cdksTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int create(String command, boolean once, List<String> cdks) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "insert into " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks" +
                                " (cdk, command, once)" +
                                " values (?, ?, ?)"
                )
        ) {
            connection.setAutoCommit(false);
            int res = 0;
            for (String cdk : cdks) {
                statement.setString(1, cdk);
                statement.setString(2, command);
                statement.setInt(3, once ? 1 : 0);
                res += statement.executeUpdate();
            }
            connection.commit();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int del(String cdk) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "delete from " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks" +
                                " where cdk = ?"
                )
        ) {
            statement.setString(1, cdk);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insert(String cdk, String playerName) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "insert into " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks_used" +
                                " (cdk, used_player)" +
                                " values (?, ?)"
                )
        ) {
            statement.setString(1, cdk);
            statement.setString(2, playerName);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Pair<String, Boolean> getCommandByCDK(String cdk) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "select command, once from " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks" +
                                " where cdk = ?"
                )
        ) {
            statement.setString(1, cdk);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Pair<>(resultSet.getString(1), resultSet.getBoolean(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Pair<>("", false);
    }

    public int checkPlayerNameAndCDK(String cdk, String playerName) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "select count(*) from " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks_used" +
                                " where cdk = ? and used_player = ?"
                )
        ) {
            statement.setString(1, cdk);
            statement.setString(2, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Set<String> getAllCDKs() {
        Set<String> cdks = new HashSet<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "select cdk from " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks"
                )
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cdks.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdks;
    }

    public Set<Pair<String, String>> getAllCDKsAndCommands() {
        Set<Pair<String, String>> cdks = new HashSet<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "select cdk, command from " + ConfigLoader.getInstance().getRootNode().getNode("Database", "databaseName").getString() + ".cdks"
                )
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cdks.add(new Pair<>(resultSet.getString(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdks;
    }
}

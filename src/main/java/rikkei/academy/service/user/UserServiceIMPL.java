package rikkei.academy.service.user;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceIMPL implements IUserService {

    Connection connection = ConnectMySQL.getConnection();

    String SQL_SAVE = "insert into `users` (`name`, `username`,`email`, `password`) value (?,?,?,?)";
    String SQL_CHECK_EMAIL = "select id from `users` where email = ?";
    String SQL_CHECK_USERNAME = "select id from `users` where username = ?";
    String SQL_CHECK_LOGIN = "select id, name, email from `users` where username = ? and password = ?";
    String SQL_UPDATE = "update users set password = ? where id = ?";

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User user) {
        try (
                PreparedStatement ps = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            String SQ_INSERT_ROLE = "INSERT INTO role_user(id_role, id_user) value (?,?)";
            Set<Role> roleSet = user.getRoleSet();
            PreparedStatement ps_role = connection.prepareStatement(SQ_INSERT_ROLE);
            for (Role role : roleSet) {
                ps_role.setInt(1, role.getId());
                ps_role.setInt(2, userId);
                ps_role.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) throws SQLException {
        try (
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
        ) {
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existByEmail(String email) {
        try (
                PreparedStatement ps = connection.prepareStatement(SQL_CHECK_EMAIL);
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean existByUsername(String username) {
        try (
                PreparedStatement ps = connection.prepareStatement(SQL_CHECK_USERNAME);
        ) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User checkLogin(String username, String password) {
        User user = null;
        try (
                PreparedStatement ps = connection.prepareStatement(SQL_CHECK_LOGIN)
        ) {
            connection.setAutoCommit(false);

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);

                user = new User(id, name, username, email, password);
                Set<Role> roleSet = new HashSet<>();
                String SQL_FIND_ROLE = "select r.id, rolename from role_user ru join role r on ru.id_role = r.id where id_user = ?";
                PreparedStatement ps_role = connection.prepareStatement(SQL_FIND_ROLE);
                ps_role.setInt(1, user.getId());
                ResultSet rs_role = ps_role.executeQuery();

                while (rs_role.next()) {
                    int id_role = rs_role.getInt(1);
                    String roleName = rs_role.getString(2);
                    roleSet.add(new Role(id_role, RoleName.valueOf(roleName.toUpperCase())));
                }

                user.setRoleSet(roleSet);

            }


            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

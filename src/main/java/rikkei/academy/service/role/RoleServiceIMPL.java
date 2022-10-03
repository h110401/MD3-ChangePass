package rikkei.academy.service.role;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleServiceIMPL implements IRoleService {
    @Override
    public Role findByRoleName(RoleName roleName) {
        String SQL_FIND_ROLE_NAME = "SELECT (id) from role where rolename = ?";
        try (
                Connection conn = ConnectMySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ROLE_NAME);
        ) {
            ps.setString(1, String.valueOf(roleName));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Role(id, roleName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

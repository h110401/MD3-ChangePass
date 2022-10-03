package rikkei.academy.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private String username;

    private String email;
    private String password;

    private Set<Role> roleSet = new HashSet<>();

    public User() {
    }

    public User(int id, String name, String username, String email, String password, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleSet = roleSet;
    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(int id, String name, String username, String email, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleName getRoleName() {
        for (Role role : roleSet) {
            if (role.getRoleName() == RoleName.USER) {
                return RoleName.USER;
            }
            if (role.getRoleName() == RoleName.PM) {
                return RoleName.PM;
            }
            if (role.getRoleName() == RoleName.ADMIN) {
                return RoleName.ADMIN;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleSet=" + roleSet +
                '}';
    }
}

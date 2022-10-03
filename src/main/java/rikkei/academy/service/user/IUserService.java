package rikkei.academy.service.user;

import rikkei.academy.model.User;
import rikkei.academy.service.IGeneric;

public interface IUserService extends IGeneric<User> {
    boolean existByEmail(String email);
    boolean existByUsername(String username);
    User checkLogin(String username, String password);

}

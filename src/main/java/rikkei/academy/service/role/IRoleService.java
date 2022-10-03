package rikkei.academy.service.role;

import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;


public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}

package bulgakov.userManagement.service;

import bulgakov.userManagement.entity.Role;

import java.util.List;

public interface RoleService {

    Role add(Role role);

    void delete(int id);

    Role getById(int id);

    Role edit(Role role);

    List<Role> getAll();

}
package bulgakov.userManagement.service.Impl;

import bulgakov.userManagement.entity.Role;
import bulgakov.userManagement.repository.RoleRepository;
import bulgakov.userManagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Role getById(int id) {
        return roleRepository.getById(id);
    }

    @Override
    @Transactional
    public Role edit(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    @Transactional
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}

package bulgakov.userManagement.service.Impl;

import bulgakov.userManagement.entity.UserAccount;
import bulgakov.userManagement.repository.UserAccountRepository;
import bulgakov.userManagement.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserAccountImpl implements UserAccountService, UserDetailsService {

    private UserAccountRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserAccountImpl(UserAccountRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public boolean add(UserAccount user) {
        UserAccount userFromDB = userRepository.findByUsername(user.getUsername());
        if (Objects.nonNull(userFromDB)){
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    @Transactional
    public UserAccount getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public UserAccount edit(UserAccount user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserAccount> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserAccount> findPaginated(int page, int pageSize) {
        Sort sort = Sort.by("username").ascending();
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return userRepository.findAll(pageable);
    }
}

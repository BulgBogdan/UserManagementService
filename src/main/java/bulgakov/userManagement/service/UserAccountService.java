package bulgakov.userManagement.service;

import bulgakov.userManagement.entity.UserAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserAccountService {

    boolean add(UserAccount user);

    void delete(int id);

    UserAccount getById(int id);

    UserAccount edit(UserAccount user);

    List<UserAccount> getAll();

    Page<UserAccount> findPaginated(int page, int pageSize);

    Page<UserAccount> getByInputText(String inputText, int page, int pageSize);

}
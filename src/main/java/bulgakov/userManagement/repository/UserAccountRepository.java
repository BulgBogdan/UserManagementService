package bulgakov.userManagement.repository;

import bulgakov.userManagement.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);

    @Query(value = "SELECT * FROM user_account AS u " +
            "JOIN user_account_role AS ur ON u.user_id = ur.user_id " +
            "JOIN role AS r ON r.role_id = ur.role_id " +
            "WHERE u.username LIKE %:inputText% OR r.role_name LIKE %:inputText%", nativeQuery = true)
    Page<UserAccount> findByText(@Param("inputText") String inputText, Pageable pageable);
}
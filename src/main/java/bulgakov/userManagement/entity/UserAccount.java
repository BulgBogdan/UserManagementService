package bulgakov.userManagement.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    @Size(min = 3, max = 16, message = "Invalid Input. Min 3 and max 16")
    @Pattern(regexp = "^[A-Za-z\\ b]*$", message = "Invalid Input. Only latin alphabet.")
    private String username;

    @Column(name = "password")
    @Size(min = 3, max = 16, message = "Invalid Input. Min 3 and max 16")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,16}$",
            message = "Invalid Input. Need min 1 symbol and number.")
    private String password;

    @Column(name = "first_name")
    @Size(min = 1, max = 16, message = "Invalid Input. Min 1 and max 16")
    @Pattern(regexp = "^[A-Za-z\\ b]*$", message = "Invalid Input. Only latin alphabet.")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 1, max = 16, message = "Invalid Input. Min 1 and max 16")
    @Pattern(regexp = "^[A-Za-z\\ b]*$", message = "Invalid Input. Only latin alphabet.")
    private String lastName;

    @Column(name = "user_status", length = 1)
    private Integer userStatus;

    @Column(name = "created_date")
    private Date createdDate = Date.valueOf(LocalDate.now());

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_account_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userStatus == that.userStatus &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, firstName, lastName, userStatus, createdDate);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userStatus=" + userStatus +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        boolean statusUser = true;
        if (getUserStatus() == 0) {
            statusUser = false;
        }
        return statusUser;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

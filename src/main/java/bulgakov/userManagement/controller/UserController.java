package bulgakov.userManagement.controller;

import bulgakov.userManagement.entity.UserAccount;
import bulgakov.userManagement.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private UserAccountService userAccountService;

    @Autowired
    public UserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/user")
    public String listUsers(Model model, String inputText) {
        return paginatedUsers(1, model, inputText);
    }

    @GetMapping("/page/{page}")
    public String paginatedUsers(@PathVariable(name = "page") int page, Model model, String inputText) {
        int pageSize = 5;
        Page<UserAccount> userAccountPage;
        if (Objects.nonNull(inputText)) {
            userAccountPage = userAccountService.getByInputText(inputText, page, pageSize);
        } else {
            userAccountPage = userAccountService.findPaginated(page, pageSize);
        }
        List<UserAccount> users = userAccountPage.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userAccountPage.getTotalPages());
        model.addAttribute("totalItems", userAccountPage.getTotalElements());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("user", userAccountService.getById(id));
        return "user";
    }

    @PostMapping("/user/{id}")
    public String correctStatus(
            @PathVariable("id") int id,
            @ModelAttribute("user") UserAccount userAccount) {
        UserAccount user = userAccountService.getById(id);
        user.setUserStatus(userAccount.getUserStatus());
        userAccountService.edit(user);
        return "redirect:/user/{id}";
    }
}
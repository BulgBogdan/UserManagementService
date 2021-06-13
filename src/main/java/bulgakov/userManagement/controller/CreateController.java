package bulgakov.userManagement.controller;

import bulgakov.userManagement.entity.UserAccount;
import bulgakov.userManagement.service.RoleService;
import bulgakov.userManagement.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class CreateController {

    private UserAccountService userAccountService;

    private RoleService roleService;

    @Autowired
    public CreateController(UserAccountService userAccountService, RoleService roleService) {
        this.userAccountService = userAccountService;
        this.roleService = roleService;
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("userForm", new UserAccount());
        model.addAttribute("listRoles", roleService.getAll());
        return "create";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("userForm") @Valid UserAccount userForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        if (!userAccountService.add(userForm)) {
            model.addAttribute("usernameError", "User with this login exists");
            return "create";
        }
        return "redirect:/user";
    }
}

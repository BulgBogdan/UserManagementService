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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EditController {

    private UserAccountService userAccountService;

    private RoleService roleService;

    @Autowired
    public EditController(UserAccountService userAccountService, RoleService roleService) {
        this.userAccountService = userAccountService;
        this.roleService = roleService;
    }

    @GetMapping("/user/{id}/edit")
    public String edit(@PathVariable("id") int userId, Model model) {
        model.addAttribute("userForm", userAccountService.getById(userId));
        model.addAttribute("listRoles", roleService.getAll());
        return "edit";
    }

    @PostMapping("/user/{id}/edit")
    public String addUser(@PathVariable("id") int userId,
                          @ModelAttribute("userForm") @Valid UserAccount userForm,
                          BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors("username")
                || bindingResult.hasFieldErrors("firstName")
                || bindingResult.hasFieldErrors("lastName")) {
            return "edit";
        }
        if (userForm.getPassword().isEmpty()) {
            String pass = userAccountService.getById(userForm.getUserId()).getPassword();
            userForm.setPassword(pass);
        } else {
            if (bindingResult.hasFieldErrors("password")) {
                return "edit";
            }
        }
        userAccountService.edit(userForm);
        return "redirect:/user";
    }
}

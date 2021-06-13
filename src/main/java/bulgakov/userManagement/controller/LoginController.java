package bulgakov.userManagement.controller;

import bulgakov.userManagement.entity.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String singIn(Model model) {
        model.addAttribute("userForm", new UserAccount());
        return "login";
    }

}
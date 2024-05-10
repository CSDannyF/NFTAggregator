package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.UserService;
import com.ferndani00.NFTAggregator.Service.UserServiceImpl;
import com.ferndani00.NFTAggregator.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private UserService userService;

    private UserDto userDto = new UserDto();

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", userDto);
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "account";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDto") UserDto userDto,
                           BindingResult result,
                           Model model) {
        UserDto existing = userService.findByEmail(userDto.getEmail());

        if(existing.getEmail() != null) {
            result.rejectValue("email", null, "There is already an account with that email");
        }
        if(result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        return "login";
    }
}

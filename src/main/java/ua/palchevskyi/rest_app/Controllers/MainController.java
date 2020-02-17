package ua.palchevskyi.rest_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.palchevskyi.rest_app.Entity.User;
import ua.palchevskyi.rest_app.Repos.DataRepository;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController{
    private DataRepository dataRepository;

    @Autowired
    public MainController(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    @GetMapping
    public String viewMain(Model model, @AuthenticationPrincipal User user){
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("messages", dataRepository.findAll());

        model.addAttribute("frontendData", data);
        return "index";
    }
}

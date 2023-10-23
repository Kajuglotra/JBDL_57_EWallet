package org.gfg.UserService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gfg.UserService.Request.CreateUserRequest;
import org.gfg.UserService.Service.UserService;
import org.gfg.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody @Valid CreateUserRequest createUserRequest) throws JsonProcessingException {
        return userService.create(createUserRequest);
    }

    @GetMapping("/userDetails")
    public User find(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userService.loadUserByUsername(user.getContact());
    }
//    @GetMapping("/getAllusers")
//    public List<User> findAll(){
//        return userService.getAllUsers();
//    }
//
    @GetMapping("/getUser")
    public User findUser(@RequestParam("contact") String contact){
        return userService.loadUserByUsername(contact);
    }



}

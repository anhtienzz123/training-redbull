package trainingredbull.controller.admin;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import trainingredbull.dto.UserDTO;
import trainingredbull.service.UserService;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserManagerController {

    private UserService userService;

    @GetMapping
    public List<UserDTO> getUserList() {
        return userService.getUserList();
    }
    
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}

package trainingredbull.service;

import java.util.List;
import trainingredbull.dto.UserDTO;
import trainingredbull.dto.UserProfileDTO;

public interface UserService {

    List<UserDTO> getUserList();
    
    void deleteUser(Long userId);
    
    UserProfileDTO getUserProfile();
}

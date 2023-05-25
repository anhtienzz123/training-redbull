package trainingredbull.service.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import trainingredbull.dto.UserDTO;
import trainingredbull.dto.UserProfileDTO;
import trainingredbull.entity.User;
import trainingredbull.repository.UserRepository;
import trainingredbull.service.UserService;
import trainingredbull.util.CommonUtils;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> modelMapper.map(u, UserDTO.class)).toList();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserProfileDTO getUserProfile() {
        Long userId = CommonUtils.getUserId();

        return userRepository.findById(userId).map(u -> modelMapper.map(u, UserProfileDTO.class))
                .get();
    }
}

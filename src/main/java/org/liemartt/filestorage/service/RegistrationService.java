package org.liemartt.filestorage.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.liemartt.filestorage.DTO.UserDTO;
import org.liemartt.filestorage.exception.InternalServerException;
import org.liemartt.filestorage.exception.UserAlreadyExistsException;
import org.liemartt.filestorage.model.User;
import org.liemartt.filestorage.repository.UserRepository;
import org.liemartt.filestorage.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

@Service
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FolderService folderService;


    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, FolderService folderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.folderService = folderService;
    }

    @Transactional
    public User register(UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        try {
            User registredUser = userRepository.save(user);
            folderService.uploadEmptyFolder(MinioUtil.getUserPrefix(registredUser.getId()));
            log.info("User {} successfully registered", user.getUsername());
            return registredUser;

        } catch (DataIntegrityViolationException e) {
            log.info("An existing user {} is being registered", user.getUsername());
            throw new UserAlreadyExistsException();

        } catch (Exception e) {
            log.error("Error when creating a user folder");
            throw new InternalServerException();
        }
    }
}

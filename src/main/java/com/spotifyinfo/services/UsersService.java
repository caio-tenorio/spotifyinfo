package com.spotifyinfo.services;

import com.spotifyinfo.domain.UserDTO;
import com.spotifyinfo.domain.model.User;
import com.spotifyinfo.repositories.UserRepository;
import com.spotifyinfo.utils.PasswordEncryptorUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Objects;


@Service
@AllArgsConstructor
public class UsersService {
    @Autowired
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    public UserDTO getUserByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null");
        }
        User user = this.userRepository.findByEmail(email);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return UserDTO.builder().email(user.getEmail()).username(user.getUsername()).build();
    }

    public void createUser(UserDTO dto) {
        validateUserDTO(dto);
        User existingUser = findUserByEmail(dto.getEmail());
        if (existingUser != null) {
            logger.error("A user with the same email already exists!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A user with the same email already exists!");
        }

        try {
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());
            user.setEncryptedPassword(PasswordEncryptorUtil.encrypt(dto.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }

    private User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    private void validateUserDTO(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body found");
        }

        if (StringUtils.isBlank(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be blank");
        }

        if (StringUtils.isBlank(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be blank");
        }

        if (StringUtils.isBlank(userDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be blank");
        }
    }
}

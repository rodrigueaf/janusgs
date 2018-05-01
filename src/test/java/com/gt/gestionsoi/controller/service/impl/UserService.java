package com.gt. gestionsoi.controller.service.impl;

import com.gt. gestionsoi.controller.entity.User;
import com.gt. gestionsoi.controller.repository.UserRepository;
import com.gt. gestionsoi.controller.service.IUserService;
import com.gt. gestionsoi.service.impl.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
public class UserService extends BaseEntityService<User, Long> implements IUserService {

    /**
     * Le constructeur
     *
     * @param userRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }
}

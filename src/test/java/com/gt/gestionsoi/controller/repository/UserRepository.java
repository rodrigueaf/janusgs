package com.gt. gestionsoi.controller.repository;

import com.gt. gestionsoi.controller.entity.User;
import com.gt. gestionsoi.repository.BaseEntityRepository;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends BaseEntityRepository<User, Long> {
}

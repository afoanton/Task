package net.afo.todoappdemo.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.afo.todoappdemo.model.EStatus;
import net.afo.todoappdemo.model.RoleEntity;
import net.afo.todoappdemo.model.UserEntity;
import net.afo.todoappdemo.repository.RoleRepository;
import net.afo.todoappdemo.repository.UserRepository;
import net.afo.todoappdemo.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
	public UserEntity setUserActive(UserEntity user) {
		if ( user !=null ) {
			user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
			user.setStatus(EStatus.ACTIVE);
			UserEntity updatedUser = userRepository.save(user);
			return updatedUser;
		}
		return new UserEntity();
	}
    
    
    @Override
	public UserEntity setUserDeactivated(UserEntity user) {
		if ( user !=null ) {
			user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
			user.setStatus(EStatus.NOT_ACTIVE);
			UserEntity updatedUser = userRepository.save(user);
			return updatedUser;
		}
		return new UserEntity();
	}

    @Override
    public UserEntity register(UserEntity user) {
		return register(user, false);
	}
    
    @Override
    public UserEntity register(UserEntity user, boolean isAdmin) {
        RoleEntity roleUser = roleRepository.findByName("ROLE_USER");
        RoleEntity roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        if (isAdmin)        	
        	userRoles.add(roleAdmin);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(EStatus.ACTIVE);

        UserEntity registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public UserEntity findByUsername(String username) {
    	UserEntity result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public UserEntity findById(Long id) {
    	UserEntity result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }

}

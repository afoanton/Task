package net.afo.todoappdemo.service;

import java.util.List;

import net.afo.todoappdemo.model.UserEntity;

public interface UserService {

		UserEntity setUserActive(UserEntity user);
	
		UserEntity setUserDeactivated(UserEntity user);
		
		UserEntity register(UserEntity user);
	
		UserEntity register(UserEntity user, boolean isAdmin);

	    List<UserEntity> getAll();

	    UserEntity findByUsername(String username);

	    UserEntity findById(Long id);

	    void delete(Long id);

}

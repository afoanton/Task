package net.afo.todoappdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.afo.todoappdemo.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String name);
}

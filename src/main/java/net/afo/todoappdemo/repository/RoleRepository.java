package net.afo.todoappdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.afo.todoappdemo.model.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findByName(String name);
}

package com.ferndani00.NFTAggregator.repository;

import com.ferndani00.NFTAggregator.models.databaseModels.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

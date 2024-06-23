package com.ferndani00.NFTAggregator.repository;

import com.ferndani00.NFTAggregator.databaseModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

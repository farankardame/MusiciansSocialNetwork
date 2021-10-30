package com.social.network.musicians.repository;

import com.social.network.musicians.entity.ProfileMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMessageRepository extends JpaRepository<ProfileMessage, Long> {
}

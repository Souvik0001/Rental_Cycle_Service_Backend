package com.pedalup.hackathon.pedalupApp.repositories;

import com.pedalup.hackathon.pedalupApp.entities.Rider;
import com.pedalup.hackathon.pedalupApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}

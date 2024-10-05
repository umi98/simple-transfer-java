package com.umi98.transfer_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umi98.transfer_app.entity.AppUser;
import com.umi98.transfer_app.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    List<History> findByAppUser(AppUser appUser);
}

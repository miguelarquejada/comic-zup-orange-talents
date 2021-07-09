package com.marb.zupcomics.repository;

import com.marb.zupcomics.model.creator.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}

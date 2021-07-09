package com.marb.zupcomics.repository;

import com.marb.zupcomics.model.User;
import com.marb.zupcomics.model.comic.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<Comic, Long> {
}

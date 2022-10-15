package com.codestates.stackoverflowclone.tag.repository;

import com.codestates.stackoverflowclone.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}

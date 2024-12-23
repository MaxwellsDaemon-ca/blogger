package com.milestone.blogger.repository;

import com.milestone.blogger.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing post data.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {}

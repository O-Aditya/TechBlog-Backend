package com.adityacode.Blog_App.repository;

import com.adityacode.Blog_App.domain.PostStatus;
import com.adityacode.Blog_App.domain.entities.Category;
import com.adityacode.Blog_App.domain.entities.Post;
import com.adityacode.Blog_App.domain.entities.Tag;
import com.adityacode.Blog_App.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findAllByStatusAndCategoryAndTags(PostStatus status, Category category, Tag tag, Pageable pageable);

    Page<Post> findAllByStatusAndCategory(PostStatus status, Category category, Pageable pageable);

    Page<Post> findAllByStatusAndTags(PostStatus status, Tag tag, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.status = :status")
    Page<Post> findAllByStatus(@Param("status") PostStatus status, Pageable pageable);

    List<Post> findAllByStatusAndAuthor(PostStatus status, User author);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.id = :id")
    Optional<Post> findByIdWithTags(@Param("id") UUID id);

    List<Post> findPostById(UUID id);
}

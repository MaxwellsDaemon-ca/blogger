package com.milestone.blogger.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Post {

      /**
      * The unique ID of the post.
      */
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int id;

      /**
       * The user who created this post.
       */
      @ManyToOne
      @JoinColumn(name = "user_id", nullable = false)
      private User user;

      /**
       * The title of the blog post.
       */
      @Column(nullable = false)
      private String title;

      /**
       * The content of the blog post.
       */
      @Column(nullable = false, columnDefinition = "TEXT")
      private String content;

      /**
       * The timestamp when the post was created.
       */
      @Column(nullable = false, updatable = false)
      private Date createdAt;

      /**
       * The timestamp when the post was last updated.
       */
      private Date updatedAt;

      // Getters and Setters

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      public User getUser() {
            return user;
      }

      public void setUser(User user) {
            this.user = user;
      }

      public String getTitle() {
            return title;
      }

      public void setTitle(String title) {
            this.title = title;
      }

      public String getContent() {
            return content;
      }

      public void setContent(String content) {
            this.content = content;
      }

      public Date getCreatedAt() {
            return createdAt;
      }

      public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
      }

      public Date getUpdatedAt() {
            return updatedAt;
      }

      public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
      }
}

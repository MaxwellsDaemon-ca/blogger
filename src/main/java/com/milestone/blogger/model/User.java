package com.milestone.blogger.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents a user in the Blogging application.
 */
@Entity
public class User {

    /**
     * The unique ID of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * The email address of the user.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The hashed password of the user.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * The role of the user (e.g., admin, user).
     */
    @Column(nullable = false)
    private String role = "user";

    /**
     * The date the user joined the application.
     */
    private Date datejoined;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(Date datejoined) {
        this.datejoined = datejoined;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

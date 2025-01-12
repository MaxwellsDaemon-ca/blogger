package com.milestone.blogger.controller;

import com.milestone.blogger.model.Post;
import com.milestone.blogger.model.User;
import com.milestone.blogger.repository.PostRepository;
import com.milestone.blogger.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Controller to handle post-related operations such as displaying posts,
 * creating new posts,
 * editing existing posts, and deleting posts.
 */
@Controller
@RequestMapping("/posts")
public class PostController {

      @Autowired
      private PostRepository postRepository;

      @Autowired
      private UserRepository userRepository;

      private static final Logger logger = LoggerFactory.getLogger(PostController.class);

      /**
       * Displays a list of all posts.
       *
       * @param model   the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to check login status.
       * @return the name of the Thymeleaf template for the post list.
       */
      @GetMapping
      public String listPosts(Model model, HttpSession session) {
            logger.info("listPosts(): Accessing all posts.");
            model.addAttribute("posts", postRepository.findAll());
            return "post-list";
      }

      /**
       * Displays a single post by its ID.
       *
       * @param id      the ID of the post to display.
       * @param model   the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to check login status.
       * @return the name of the Thymeleaf template for the single post view.
       */
      @GetMapping("/{id}")
      public String viewPost(@PathVariable int id, Model model, HttpSession session) {
            logger.info("viewPost(): Attempting to View Post.");
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                  logger.warn("viewPost(): Post is empty.");
                  return "redirect:/posts";
            }

            Long postCreatorId = (Long) session.getAttribute("userId");
            model.addAttribute("userId", postCreatorId);
            model.addAttribute("post", post.get());
            model.addAttribute("formattedDate",
                        new SimpleDateFormat("dd MMM yyyy HH:mm").format(post.get().getCreatedAt()));
            logger.info("viewPost(): Exiting.");
            return "view-post";
      }

      /**
       * Displays the form to create a new post.
       *
       * @param model   the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to check login status.
       * @return the name of the Thymeleaf template for the new post form.
       */
      @GetMapping("/new")
      public String showNewPostForm(Model model, HttpSession session) {
            logger.info("showNewPostForm(): Attempting to view create new Post page.");
            Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
            if (!loggedIn) {
                  logger.warn("showNewPostForm(): User not logged in, unable to view new Post page.");
                  return "redirect:/users/login";
            }

            model.addAttribute("post", new Post());
            logger.info("showNewPostForm(): Exiting.");
            return "new-post";
      }

      /**
       * Handles the creation of a new post.
       *
       * @param post    the new post details from the form.
       * @param session the {@link HttpSession} to identify the logged-in user.
       * @return a redirect to the list of posts after successful creation.
       */
      @PostMapping("/new")
      public String createPost(@ModelAttribute Post post, HttpSession session) {
            logger.info("createPost(): Attempting to create new post.");
            Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
            Long userId = (Long) session.getAttribute("userId");

            if (!loggedIn || userId == null) {
                  logger.warn("createPost(): unable to create new post, user not logged in.");
                  return "redirect:/users/login";
            }

            // Fetch the user who is creating the post
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                  logger.warn("createPost(): unable to create new post, user is empty.");
                  return "redirect:/users/login";
            }

            post.setUser(user.get());
            post.setCreatedAt(new Date());
            post.setUpdatedAt(new Date());
            postRepository.save(post);

            logger.info("createPost(): New post created. Exiting.");

            return "redirect:/posts";
      }

      /**
       * Displays the form to edit an existing post.
       * Only the user who created the post can edit it.
       *
       * @param id      the ID of the post to edit.
       * @param model   the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to identify the logged-in user.
       * @return the name of the Thymeleaf template for the edit post form, or a
       *         redirect if not authorized.
       */
      @GetMapping("/{id}/edit")
      public String showEditPostForm(@PathVariable int id, Model model, HttpSession session) {
            logger.info("showEditPostForm(): Attempting to show edit post form.");
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                  logger.warn("showEditPostForm(): Post empty, unable to edit.");
                  return "redirect:/posts";
            }

            if(!verifyUserOwnsPost(session, post.get())) {
                  logger.warn("showEditPostForm(): Post not owned by logged in user, cannot edit post.");
                  return "redirect:/posts";
            }

            logger.info("showEditPostForm(): Exiting.");
            model.addAttribute("post", post.get());
            return "edit-post";
      }

      /**
       * Handles the updating of an existing post.
       * Only the user who created the post can update it.
       *
       * @param id          the ID of the post to update.
       * @param updatedPost the updated post details from the form.
       * @param session     the {@link HttpSession} to identify the logged-in user.
       * @return a redirect to the list of posts or the updated post view.
       */
      @PostMapping("/{id}/edit")
      public String updatePost(@PathVariable int id, @ModelAttribute Post updatedPost, HttpSession session) {
            logger.info("updatePost(): Attempting to edit post.");
            Optional<Post> existingPost = postRepository.findById(id);
            if (existingPost.isEmpty()) {
                  logger.warn("updatePost(): Post is empty, unable to edit.");
                  return "redirect:/posts";
            }

            if(!verifyUserOwnsPost(session, existingPost.get())) {
                  logger.warn("updatePost(): User not owner of post. Unable to edit.");
                  return "redirect:/posts";
            }

            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setUpdatedAt(new Date());
            postRepository.save(post);

            logger.info("updatePost(): Post successfully edited. Exiting.");

            return "redirect:/posts/" + post.getId();
      }

      /**
       * Handles the deletion of a post.
       * Only the user who created the post can delete it.
       *
       * @param id      the ID of the post to delete.
       * @param session the {@link HttpSession} to identify the logged-in user.
       * @return a redirect to the list of posts after successful deletion.
       */
      @GetMapping("/{id}/delete")
      public String deletePost(@PathVariable int id, HttpSession session) {
            logger.info("deletePost(): Attempting to delete post.");
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                  logger.warn("deletePost(): Post is empty, unable to delete.");
                  return "redirect:/posts";
            }

            if(!verifyUserOwnsPost(session, post.get())) {
                  logger.warn("deletePost(): Post not owned by user, unable to delete.");
                  return "redirect:/posts";
            }

            postRepository.delete(post.get());
            logger.info("deletePost(): Post successfully deleted. Exiting.");
            return "redirect:/posts";
      }

      /**
       * Verifies if the logged-in user is the owner of the specified post.
       *
       * @param session the {@link HttpSession} to check the logged-in user.
       * @param post    the {@link Post} to verify ownership.
       * @return true if the user is logged in and owns the post; false otherwise.
       */
      private boolean verifyUserOwnsPost(HttpSession session, Post post) {
            logger.info("verifyUserOwnsPost(): Attempting to verify post ownership.");
            // Check if the user is logged in
            Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
            if (!loggedIn) {
                  logger.warn("verifyUserOwnsPost(): User not logged in.");
                  return false;
            }

            // Get the logged-in user's ID from the session
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                  logger.warn("verifyUserOwnsPost(): UserID is null.");
                  return false;
            }

            logger.info("verifyUserOwnsPost(): Exiting.");
            // Check if the logged-in user is the owner of the post
            return userId.equals(post.getUser().getId());
      }

}

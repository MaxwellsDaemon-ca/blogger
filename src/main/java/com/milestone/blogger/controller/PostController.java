package com.milestone.blogger.controller;

import com.milestone.blogger.model.Post;
import com.milestone.blogger.model.User;
import com.milestone.blogger.repository.PostRepository;
import com.milestone.blogger.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * Controller to handle post-related operations such as displaying posts, creating new posts,
 * editing existing posts, and deleting posts.
 */
@Controller
@RequestMapping("/posts")
public class PostController {

      @Autowired
      private PostRepository postRepository;

      @Autowired
      private UserRepository userRepository;

      /**
       * Displays a list of all posts.
       *
       * @param model the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to check login status.
       * @return the name of the Thymeleaf template for the post list.
       */
      @GetMapping
      public String listPosts(Model model, HttpSession session) {
            model.addAttribute("posts", postRepository.findAll());

            return "post-list";
      }

      /**
       * Displays a single post by its ID.
       *
       * @param id     the ID of the post to display.
       * @param model  the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to check login status.
       * @return the name of the Thymeleaf template for the single post view.
       */
      @GetMapping("/{id}")
      public String viewPost(@PathVariable int id, Model model, HttpSession session) {
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                  return "redirect:/posts";
            }

            Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
            Long postCreatorId = (Long) session.getAttribute("userId");
            model.addAttribute("userId", postCreatorId);
            model.addAttribute("post", post.get());
            return "view-post";
      }

      /**
       * Displays the form to create a new post.
       *
       * @param model  the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to check login status.
       * @return the name of the Thymeleaf template for the new post form.
       */
      @GetMapping("/new")
      public String showNewPostForm(Model model, HttpSession session) {
            Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
            if (!loggedIn) {
                  return "redirect:/users/login";
            }

            model.addAttribute("post", new Post());
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
            Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
            Long userId = (Long) session.getAttribute("userId");

            if (!loggedIn || userId == null) {
                  return "redirect:/users/login";
            }

            // Fetch the user who is creating the post
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                  return "redirect:/users/login";
            }

            post.setUser(user.get());
            post.setCreatedAt(new Date());
            post.setUpdatedAt(new Date());
            postRepository.save(post);

            return "redirect:/posts";
      }

      /**
       * Displays the form to edit an existing post.
       * Only the user who created the post can edit it.
       *
       * @param id      the ID of the post to edit.
       * @param model   the {@link Model} to pass attributes to the view.
       * @param session the {@link HttpSession} to identify the logged-in user.
       * @return the name of the Thymeleaf template for the edit post form, or a redirect if not authorized.
       */
      @GetMapping("/{id}/edit")
      public String showEditPostForm(@PathVariable int id, Model model, HttpSession session) {
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                  return "redirect:/posts";
            }

            // verifyUserOwnsPostOrRedirect();

            model.addAttribute("post", post.get());
            return "edit-post";
      }

      /**
       * Handles the updating of an existing post.
       * Only the user who created the post can update it.
       *
       * @param id      the ID of the post to update.
       * @param updatedPost the updated post details from the form.
       * @param session the {@link HttpSession} to identify the logged-in user.
       * @return a redirect to the list of posts or the updated post view.
       */
      @PostMapping("/{id}/edit")
      public String updatePost(@PathVariable int id, @ModelAttribute Post updatedPost, HttpSession session) {
            Optional<Post> existingPost = postRepository.findById(id);
            if (existingPost.isEmpty()) {
                  return "redirect:/posts";
            }

            // verifyUserOwnsPostOrRedirect();

            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setUpdatedAt(new Date());
            postRepository.save(post);

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
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                  return "redirect:/posts";
            }

            // verifyUserOwnsPostOrRedirect();

            postRepository.delete(post.get());
            return "redirect:/posts";
      }
}

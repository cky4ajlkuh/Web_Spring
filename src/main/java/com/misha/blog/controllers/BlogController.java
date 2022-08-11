package com.misha.blog.controllers;

import com.misha.blog.models.Post;
import com.misha.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private PostRepository repository;

    @GetMapping
    public String blog(Model model) {
        Iterable<Post> posts = repository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/add")
    public String blogAdd(Model model) {
        return "add";
    }

    @PostMapping("/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String text, Model model) {
        Post post = new Post(title, anons, text);
        repository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id") int id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = repository.findById(id);
        ArrayList<Post> posts = new ArrayList<>();
        post.ifPresent(posts::add);
        model.addAttribute("post", posts);
        return "details";
    }

    @GetMapping("/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") int id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = repository.findById(id);
        ArrayList<Post> posts = new ArrayList<>();
        post.ifPresent(posts::add);
        model.addAttribute("post", posts);
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") int id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String text, Model model) {
        Post post = repository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);
        repository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") int id, Model model) {
        Post post = repository.findById(id).orElseThrow();
        repository.delete(post);
        return "redirect:/blog";
    }
}

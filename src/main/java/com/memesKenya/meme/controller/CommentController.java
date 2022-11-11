package com.memesKenya.meme.controller;

import com.memesKenya.meme.entities.Comment;
import com.memesKenya.meme.service._service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CommentController {
    @Autowired
    private CommentService service;
    @PostMapping("/comment")
    public String comment(@NonNull @RequestBody Comment comment){
        service.comment(comment);
        return "Comment "+comment.getId()+" placed successfully";
    }
    @PutMapping("/edit/{postId}/{newComment}")
    public String editComment(@RequestBody Comment comment, @PathVariable UUID postId,@PathVariable String newComment){
        return service.edit(postId,comment.getId(),newComment);
    }
    @GetMapping("/contents/{id}")
    public String commentContent(@PathVariable Long id){
        return service.getCommentContent(id);
    }
    @PostMapping("/comments")
    public List<Comment> getComments(@RequestParam UUID postId){
        return service.comments(postId);
    }
    @PostMapping("/total")
    public int getTotalComments(@PathVariable UUID postId){
        return service.getTotalPostComments(postId);
    }
}

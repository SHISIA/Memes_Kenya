package com.memesKenya.meme.controller;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaPost;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.service._service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/upload/{uuid}/{description}")
    public String  uploadImageFile(@RequestParam("file") MultipartFile file,
                                   @PathVariable String uuid,
                                   @PathVariable String description) throws Exception {
        List<String> list= List.of(".jpg",".png",".jpeg",".webp",".ico",".jfif");
        if (list.stream().noneMatch(Objects.requireNonNull(file.getOriginalFilename()).toLowerCase()::endsWith)){
            return "This file format currently is not allowed";
        }
        MediaPost mediaPost;
        String downloadURL;
        mediaPost = postService.upload(file,uuid,description);
        downloadURL= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(mediaPost.getPostId().toString())
                .toUriString();
        return  downloadURL;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadImagePost(@PathVariable UUID fileId) throws PostNotFoundException {
        MediaPost mediaPost = postService.findPostByUUID(fileId);
        postService.download(mediaPost.getPostId());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "mediaPost; filename=\""
                                    + mediaPost.getPostId()+".png\""
                    ).contentType(MediaType.parseMediaType(
                            mediaPost.getImageType()
                    ))
                    .body(new ByteArrayResource(mediaPost.getImageData()));
    }

    @PutMapping("/like/{postId}")
    public String likePost(@PathVariable UUID postId) throws Exception {
        postService.like(postId);
        return getLikeCounts(postId)+" Likes";
    }

    @PostMapping("/like/likes/{post}")
    public int getLikeCounts(@PathVariable UUID post){
        return postService.likeCount(post);
    }

    @PutMapping("/share")
    public String share(@RequestParam UUID postId){
        postService.share(postId);
        return postService.getShareCount(postId)+" Shares";
    }

    @GetMapping("/getOwner/{id}")
    public Memer getPostOwner(@RequestParam("owner") UUID postOwner,@PathVariable("id") UUID postId){
        return postService.postOwner(postOwner,postId);
    }

}

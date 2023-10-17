package com.memesKenya.meme.controller;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaPost;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.service._service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/upload/{uuid}/{description}/{nickName}")
    public String  uploadImageFile(@RequestParam("file") MultipartFile file,
                                   @PathVariable String uuid,
                                   @PathVariable String description,
                                   @PathVariable String nickName) throws Exception {
        List<String> list= List.of(".jpg",".png",".jpeg",".webp",".ico",".jfif");
        if (list.stream().noneMatch(Objects.requireNonNull(file.getOriginalFilename()).toLowerCase()::endsWith)){
            return "This file format currently is not allowed";
        }
            postService.upload(file,uuid,description,nickName);
        return "Uploaded Successfully";
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
    public Map<String, Integer> likePost(@PathVariable UUID postId) throws Exception {
        postService.like(postId);
        Map<String,Integer> response=new HashMap<>();
        response.put("Likes",getLikeCounts(postId));
        return response;
    }

    @PutMapping("/downloads/{postId}")
    public Map<String, Integer> downloads(@PathVariable UUID postId) throws Exception {
        postService.getDownloads(postId);
        Map<String,Integer> response=new HashMap<>();
        response.put("Downloads",getLikeCounts(postId));
        return response;
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

    @GetMapping("/getPosts")
    public Page<MediaPost> getPosts(@RequestParam int page,@RequestParam int size){
        PageRequest pageRequest=PageRequest.of(page,size);
        System.out.println("Get posts "+page);
        return postService.getPosts(pageRequest);
    }

    @PutMapping("/unlike/{postId}")
    public Map<String,Integer> unLikePost(@PathVariable UUID postId) throws Exception {
        Map<String,Integer> response=new HashMap<>();
        response.put("unliked",postService.unLike(postId));
       return response;
    }

}

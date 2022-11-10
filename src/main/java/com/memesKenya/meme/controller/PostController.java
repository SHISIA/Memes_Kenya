package com.memesKenya.meme.controller;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaTypeImage;
import com.memesKenya.meme.service._service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
public class PostController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public String  uploadImageFile(@RequestParam("file") MultipartFile file) throws Exception {
        MediaTypeImage mediaTypeImage=null;
        String downloadURL="";
        mediaTypeImage=imageService.upload(file);
        downloadURL= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(mediaTypeImage.getPostId().toString())
                .toUriString();
        return  downloadURL;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadImagePost(@PathVariable UUID fileId) throws PostNotFoundException {
        MediaTypeImage mediaTypeImage =imageService.findPostByUUID(fileId);
        imageService.download(mediaTypeImage.getPostId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        mediaTypeImage.getImageType()
                )).header(HttpHeaders.CONTENT_DISPOSITION,
                        "mediaTypeImage; filename=\""
                        +mediaTypeImage.getPostId()+"\""
                ).body(new ByteArrayResource(mediaTypeImage.getImageData()));
    }

    @PostMapping("/like/{postId}")
    public String likePost(@PathVariable UUID postId) throws Exception {
        imageService.like(postId);
        return getLikeCounts(postId)+" Likes";
    }

    @PostMapping("/like/likes/{post}")
    public int getLikeCounts(@PathVariable UUID post){
        return imageService.likeCount(post);
    }

}

package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaTypeImage;
import com.memesKenya.meme.repository.ImagePostRepo;
import com.memesKenya.meme.service._service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ImagePostRepo repo;

    @Override
    public MediaTypeImage upload(MultipartFile file) throws Exception {
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")){
            throw new Exception("Cannot find file specified");
        }
        MediaTypeImage image=new MediaTypeImage(fileName,file.getSize(),file.getContentType(),file.getBytes());
        return repo.save(image);
    }

    @Override
    public MediaTypeImage findPostByUUID(UUID postId) throws PostNotFoundException {
        return repo.findById(postId).orElseThrow(()->new PostNotFoundException("Post with  ID "+postId+" not found",Timestamp.valueOf(LocalDateTime.now())));
    }

    @Override
    @Transactional
    public int like(UUID uuid) throws Exception {
       MediaTypeImage mediaTypeImage= repo.findById(uuid)
               .orElseThrow(() -> new Exception("Post Not Available" + uuid));
       repo.like(mediaTypeImage.getPostId());
       return likeCount(mediaTypeImage.getPostId());
    }

    @Override
    @Transactional
    public void download(UUID postId) {
        repo.download(postId);
    }

    @Override
    @Transactional
    public void share(UUID postId) {
        repo.share(postId);
    }

    @Override
    public int getShareCount(UUID postId) {
        return repo.getShareCount(postId);
    }

    @Override
    public int likeCount(UUID postId) {
        return repo.getLikeCount(postId);
    }

}

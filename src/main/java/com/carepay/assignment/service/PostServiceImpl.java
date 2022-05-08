package com.carepay.assignment.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.carepay.assignment.domain.model.post.CreatePostRequest;
import com.carepay.assignment.domain.entity.Post;
import com.carepay.assignment.domain.model.post.PostDetails;
import com.carepay.assignment.domain.model.post.PostInfo;
import com.carepay.assignment.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        Post newPostInDb = postRepository.save(newPost);

        logger.info("New post is created with id {} and its title is {} and its content is {}", newPost.getId(), newPost.getTitle(), newPost.getContent());

        PostDetails postDetails = new PostDetails();
        postDetails.setId(newPostInDb.getId());
        postDetails.setTitle(newPostInDb.getTitle());
        postDetails.setContent(newPostInDb.getContent());

        return postDetails;
    }

    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {
        Page<Post> allPosts = postRepository.findAll(pageable);
        return allPosts.map(post -> new PostInfo(post.getId(), post.getTitle()));
    }

    @Override
    public PostDetails getPostDetails(Long id) {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                PostDetails postDetails = new PostDetails();
                postDetails.setId(post.get().getId());
                postDetails.setTitle(post.get().getTitle());
                postDetails.setContent(post.get().getContent());
                return postDetails;
            } else {
                throw new EntityNotFoundException("Post with id: " + id + " cannot be found");
            }
    }

    @Override
    public void deletePost(Long id) {
        getPostDetails(id);
        postRepository.deleteById(id);
    }
}

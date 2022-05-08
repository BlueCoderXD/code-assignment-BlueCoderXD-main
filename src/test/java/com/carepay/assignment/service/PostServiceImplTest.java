package com.carepay.assignment.service;

import com.carepay.assignment.domain.entity.Post;
import com.carepay.assignment.domain.model.post.CreatePostRequest;
import com.carepay.assignment.domain.model.post.PostDetails;
import com.carepay.assignment.domain.model.post.PostInfo;
import com.carepay.assignment.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PostServiceImplTest {
    @MockBean
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewPostSuccessfully() {
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("test new post content");
        createPostRequest.setTitle("test new post title");

        Post newTestPost = new Post();
        newTestPost.setTitle(createPostRequest.getTitle());
        newTestPost.setContent(createPostRequest.getContent());
        newTestPost.setId(1L);

        Mockito.when(postRepository.save(ArgumentMatchers.any())).thenReturn(newTestPost);

        PostDetails result = postServiceImpl.createPost(createPostRequest);
        assertEquals(1L, result.getId());
        assertEquals("test new post title", result.getTitle());
        assertEquals("test new post content", result.getContent());
    }

    @Test
    void getPostDetailsSuccessfully() {
        Post newTestPost = new Post();
        newTestPost.setTitle("test new post title");
        newTestPost.setContent("test new post content");
        newTestPost.setId(1L);

        Mockito.when(postRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(newTestPost));

        PostDetails result = postServiceImpl.getPostDetails(1L);
        assertEquals(1L, result.getId());
        assertEquals("test new post title", result.getTitle());
        assertEquals("test new post content", result.getContent());
    }

    @Test
    void getAllPostsSuccessfully() {
        Pageable pageable = Mockito.mock(Pageable.class);

        Post newTestPost = new Post();
        newTestPost.setTitle("test new post title");
        newTestPost.setId(1L);

        List<Post> listOfPosts = new ArrayList<>();
        listOfPosts.add(newTestPost);

        Page<Post> pagedPosts = new PageImpl(listOfPosts);

        Mockito.when(postRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(pagedPosts);

        Page<PostInfo> result = postServiceImpl.getPosts(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("test new post title", result.getContent().get(0).getTitle());
        assertEquals(1L, result.getContent().get(0).getId());
    }
}

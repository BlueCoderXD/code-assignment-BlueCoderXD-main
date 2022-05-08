package com.carepay.assignment.service;

import com.carepay.assignment.domain.entity.Comment;
import com.carepay.assignment.domain.entity.Post;
import com.carepay.assignment.domain.model.comment.CommentDetails;
import com.carepay.assignment.domain.model.comment.CreateCommentRequest;
import com.carepay.assignment.repository.CommentRepository;
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
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewCommentSuccessfully() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        createCommentRequest.setComment("this is a new test comment");

        Post newTestPost = new Post();
        newTestPost.setTitle("");
        newTestPost.setContent("");
        newTestPost.setId(100L);

        Comment newComment = new Comment();
        newComment.setComment(createCommentRequest.getComment());
        newComment.setId(1L);
        newComment.setPost(newTestPost);

        Mockito.when(postRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(newTestPost));
        Mockito.when(commentRepository.save(ArgumentMatchers.any())).thenReturn(newComment);

        CommentDetails result = commentServiceImpl.createComment(createCommentRequest, newTestPost.getId());
        assertEquals(1L, result.getId());
        assertEquals("this is a new test comment", result.getComment());
        assertEquals(100L, result.getPost_id());
    }

    @Test
    void getCommentDetailsSuccessfully() {
        Post newTestPost = new Post();
        newTestPost.setTitle("");
        newTestPost.setContent("");
        newTestPost.setId(100L);

        Comment newComment = new Comment();
        newComment.setComment("this is a test comment");
        newComment.setId(1L);
        newComment.setPost(newTestPost);

        Mockito.when(commentRepository.findByIdAndPostId(1L, 100L)).thenReturn(Optional.of(newComment));

        CommentDetails result = commentServiceImpl.getCommentDetails(newTestPost.getId(), newComment.getId());
        assertEquals(1L, result.getId());
        assertEquals("this is a test comment", result.getComment());
        assertEquals(100L, result.getPost_id());
    }

    @Test
    void getAllCommentsSuccessfully() {
        Pageable pageable = Mockito.mock(Pageable.class);

        Post newTestPost = new Post();
        newTestPost.setTitle("test new post title");
        newTestPost.setId(100L);

        Comment newComment = new Comment();
        newComment.setComment("this is a test comment on a post");
        newComment.setId(1L);
        newComment.setPost(newTestPost);

        List<Comment> listOfComments = new ArrayList<>();
        listOfComments.add(newComment);

        Page<Comment> pageOfCommentsOnPost = new PageImpl(listOfComments);

        Mockito.when(commentRepository.findByPostId(eq(100L), ArgumentMatchers.isA(Pageable.class))).thenReturn(pageOfCommentsOnPost);

        Page<CommentDetails> result = commentServiceImpl.getComments(100L, pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("this is a test comment on a post", result.getContent().get(0).getComment());
        assertEquals(1L, result.getContent().get(0).getId());
    }
}
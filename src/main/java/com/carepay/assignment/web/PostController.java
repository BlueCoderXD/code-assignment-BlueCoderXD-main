package com.carepay.assignment.web;

import javax.validation.Valid;

import com.carepay.assignment.domain.model.comment.CommentDetails;
import com.carepay.assignment.domain.model.comment.CreateCommentRequest;
import com.carepay.assignment.domain.model.post.CreatePostRequest;
import com.carepay.assignment.domain.model.post.PostDetails;
import com.carepay.assignment.domain.model.post.PostInfo;
import com.carepay.assignment.service.CommentService;
import com.carepay.assignment.service.CommentServiceImpl;
import com.carepay.assignment.service.PostService;
import com.carepay.assignment.service.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostServiceImpl postService, CommentServiceImpl commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    Page<PostInfo> getPosts(Pageable pageable) {
        return postService.getPosts(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @GetMapping("{id}")
    PostDetails getPostDetails(@PathVariable("id") final Long id) {
        return postService.getPostDetails(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable("id") final Long id) {
        postService.deletePost(id);
    }

    @GetMapping("{id}/comments")
    Page<CommentDetails> getComments(@PathVariable("id") final Long id, Pageable pageable) {
        return commentService.getComments(id, pageable);
    }

    @PostMapping("{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    CommentDetails createComment(
            @Valid @RequestBody CreateCommentRequest createCommentRequest,
            @PathVariable("id") final Long id) {
        return commentService.createComment(createCommentRequest, id);
    }

    @GetMapping("{id}/comments/{commentId}")
    CommentDetails getCommentDetails(@PathVariable("id") final Long id, @PathVariable("commentId") final Long commentId) {
        return commentService.getCommentDetails(id, commentId);
    }

    @DeleteMapping("{id}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(@PathVariable("id") final Long id, @PathVariable("commentId") final Long commentId) {
        commentService.deleteComment(id, commentId);
    }
}

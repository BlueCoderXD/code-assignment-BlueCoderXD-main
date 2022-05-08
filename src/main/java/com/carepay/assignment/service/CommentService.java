package com.carepay.assignment.service;

import com.carepay.assignment.domain.model.comment.CommentDetails;
import com.carepay.assignment.domain.model.comment.CreateCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface CommentService {
    CommentDetails createComment(@Valid CreateCommentRequest createCommentRequest, Long postId);

    Page<CommentDetails> getComments(Long id, final Pageable pageable);

    CommentDetails getCommentDetails(Long postId, Long commentId);

    void deleteComment(Long postId, Long commentId);
}

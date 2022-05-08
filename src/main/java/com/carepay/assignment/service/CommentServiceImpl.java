package com.carepay.assignment.service;

import com.carepay.assignment.domain.entity.Comment;
import com.carepay.assignment.domain.entity.Post;
import com.carepay.assignment.domain.model.comment.CommentDetails;
import com.carepay.assignment.domain.model.comment.CreateCommentRequest;
import com.carepay.assignment.repository.CommentRepository;
import com.carepay.assignment.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public CommentDetails createComment(CreateCommentRequest createCommentRequest, Long postId) {
        Optional<Post> postInDB = postRepository.findById(postId);

        if (postInDB.isPresent()) {
            Post postFound = postInDB.get();
            Comment newComment = new Comment();
            newComment.setPost(postFound);
            newComment.setComment(createCommentRequest.getComment());
            Comment newCommentInDB = commentRepository.save(newComment);

            logger.info("New comment is created with id {} and it is linked to post with postId {}", newComment.getId(), newComment.getPost().getId());

            CommentDetails commentDetails = new CommentDetails();
            commentDetails.setId(newCommentInDB.getId());
            commentDetails.setPost_id(postId);
            commentDetails.setComment(newCommentInDB.getComment());
            return commentDetails;
        } else {
            throw new EntityNotFoundException("Post with id: " + postId + " cannot be found");
        }
    }

    @Override
    public Page<CommentDetails> getComments(Long id, Pageable pageable) {
        Page<Comment> allCommentsOnPost = commentRepository.findByPostId(id, pageable);
        return allCommentsOnPost.map(comment -> new CommentDetails(comment.getId(), comment.getPost().getId(), comment.getComment()));
    }

    @Override
    public CommentDetails getCommentDetails(Long postId, Long commentId) {
        Optional<Comment> comment = commentRepository.findByIdAndPostId(commentId, postId);
        if (comment.isPresent()) {
            CommentDetails commentDetails = new CommentDetails();
            commentDetails.setId(comment.get().getId());
            commentDetails.setPost_id(comment.get().getPost().getId());
            commentDetails.setComment(comment.get().getComment());
            return commentDetails;
        } else {
            throw new EntityNotFoundException("Comment with id: " + commentId + " cannot be found");
        }
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        getCommentDetails(postId, commentId);
        commentRepository.deleteById(commentId);
    }
}

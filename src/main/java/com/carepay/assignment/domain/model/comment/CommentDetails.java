package com.carepay.assignment.domain.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDetails {
    private Long id;
    private Long post_id;
    private String comment;
}

package com.carepay.assignment.domain.model.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    @Size(max = 128)
    @NotNull
    private String title;
    @Size(max = 128)
    @NotNull
    private String content;
}

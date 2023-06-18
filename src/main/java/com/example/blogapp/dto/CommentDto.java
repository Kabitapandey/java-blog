package com.example.blogapp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private int id;

    @NotBlank(message = "Comment must not be empty")
    private String comment;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int blogId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int userId;

    private UserDto user;

    private List<ReplyDto> reply;
}

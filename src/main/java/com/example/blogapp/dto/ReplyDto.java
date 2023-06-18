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
public class ReplyDto {
    private int id;

    @NotBlank(message = "Reply must be provided")
    private String reply;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int commentId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int replyId;

    private UserDto user;

    private List<ReplyDto> reply_replies;
}

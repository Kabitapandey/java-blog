package com.example.blogapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BlogDto {
    private int id;

    @NotBlank(message = "Blog title must be provided")
    private String title;

    @NotBlank(message = "Blog description must be provided")
    private String description;

    private Date date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int userId;

    private List<CommentDto> comments;
}

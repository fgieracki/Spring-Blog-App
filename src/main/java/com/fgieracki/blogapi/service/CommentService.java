package com.fgieracki.blogapi.service;

import com.fgieracki.blogapi.payload.CommentDTO;

public interface CommentService {
    CommentDTO createComment(Long postId, CommentDTO commentDTO);
}

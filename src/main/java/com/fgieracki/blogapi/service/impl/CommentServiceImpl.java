package com.fgieracki.blogapi.service.impl;

import com.fgieracki.blogapi.exception.BlogAPIException;
import com.fgieracki.blogapi.exception.ResourceNotFoundException;
import com.fgieracki.blogapi.model.Comment;
import com.fgieracki.blogapi.model.Post;
import com.fgieracki.blogapi.payload.CommentDTO;
import com.fgieracki.blogapi.repository.CommentRepository;
import com.fgieracki.blogapi.repository.PostRepository;
import com.fgieracki.blogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Post post = retrievePostById(postId);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = retrievePostById(postId);
        Comment comment = retrieveCommentById(commentId);
        validatePostAndComment(post, comment);

        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest) {
        Post post = retrievePostById(postId);
        Comment comment = retrieveCommentById(commentId);
        validatePostAndComment(post, comment);

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatetdComment = commentRepository.save(comment);

        return mapToDTO(updatetdComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = retrievePostById(postId);
        Comment comment = retrieveCommentById(commentId);
        validatePostAndComment(post, comment);

        commentRepository.delete(comment);
    }

    private void validatePostAndComment(Post post, Comment comment){
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
    }

    private Post retrievePostById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));
    }

    private Comment retrieveCommentById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId.toString()));
    }

    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        return comment;
    }
}

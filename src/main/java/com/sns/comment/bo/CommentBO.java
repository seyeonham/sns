package com.sns.comment.bo;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentDTO;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentBO {

    private final CommentMapper commentMapper;
    private final UserBO userBO;

    public int addComment(String content, int postId, int userId) {
        return commentMapper.insertComment(content, postId, userId);
    }

    public List<CommentDTO> generateCommentListByPostId(int postId) {
        List<CommentDTO> commentDTOList = new ArrayList<>();

        List<Comment> commentList = commentMapper.selectCommentByPostId(postId);
        for (Comment comment : commentList) {
            CommentDTO commentDTO = new CommentDTO();

            // 댓글 1개
            commentDTO.setComment(comment);

            // 댓글쓴이
            int userId = comment.getUserId();
            UserEntity user = userBO.getUserEntityById(userId);
            commentDTO.setUser(user);

            // !!!!!!! 리스트에 넣기
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
     }

    public List<Comment> getCommentByPostId(int postId) {
        return commentMapper.selectCommentByPostId(postId);
    }

    public int deleteCommentById(int id) {
        return commentMapper.deleteCommentById(id);
    }
}

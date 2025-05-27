package com.longld.feedback_system.Converter;

import com.longld.feedback_system.DTO.request.CommentRequest;
import com.longld.feedback_system.DTO.response.CommentResponse;
import com.longld.feedback_system.Entity.InternalComment;
import org.springframework.stereotype.Component;

@Component
public class CommentRevert {
    public CommentResponse revert(InternalComment internalComment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(internalComment.getId());
        commentResponse.setContent(internalComment.getContent());
        commentResponse.setCreated_at(internalComment.getCreated_at());
        return commentResponse;
    }

}

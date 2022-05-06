package com.valts.obforumdemov2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespuestaDTO {
    private Long id;
    private LocalDateTime updatedAt;
    private String answer;
    private boolean isPinned;
    private Long preguntaId;
    private Long totalPositiveVotes;
    private Long totalNegativeVotes;
    private UserDTO user;
    private Boolean userVote;

    public RespuestaDTO(Long id, LocalDateTime updatedAt, String answer, boolean isPinned, Long preguntaId, Long totalPositiveVotes, Long totalNegativeVotes, Long userId, String avatar, String username, Boolean userVote) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.answer = answer;
        this.isPinned = isPinned;
        this.preguntaId = preguntaId;
        this.totalPositiveVotes = totalPositiveVotes;
        this.totalNegativeVotes = totalNegativeVotes;
        this.user = new UserDTO(userId, avatar, username);
        this.userVote = userVote;
    }
}

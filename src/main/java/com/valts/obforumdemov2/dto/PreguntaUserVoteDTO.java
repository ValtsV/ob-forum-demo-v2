package com.valts.obforumdemov2.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PreguntaUserVoteDTO{
    private Long id;
    private LocalDateTime updatedAt;
    private String description;
    private boolean isPinned;
    private String title;
    private Long totalPositiveVotes;
    private Long totalNegativeVotes;
    private Long totalRespuestas;
    private UserDTO user;
    private Boolean userVote;

    public PreguntaUserVoteDTO(Long id, LocalDateTime updatedAt, String description, boolean isPinned, String title, Long totalPositiveVotes, Long totalNegativeVotes, Long totalRespuestas, Long userId, String avatar, String username, Boolean userVote) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.description = description;
        this.isPinned = isPinned;
        this.title = title;
        this.totalPositiveVotes = totalPositiveVotes;
        this.totalNegativeVotes = totalNegativeVotes;
        this.totalRespuestas = totalRespuestas;
        this.user = new UserDTO(userId, avatar, username);
        this.userVote = userVote;
    }
}

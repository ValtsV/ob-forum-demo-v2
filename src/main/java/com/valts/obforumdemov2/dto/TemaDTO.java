package com.valts.obforumdemov2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemaDTO {
    private Long id;
    private String description;
    private boolean isPinned;
    private String title;
    private Long cursoId;
    private Long moduloId;
    private Long preguntasCount;
}

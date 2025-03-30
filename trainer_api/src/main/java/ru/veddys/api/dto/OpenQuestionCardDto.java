package ru.veddys.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Задача в системе TM")
public class OpenQuestionCardDto {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "Название задачи", example = "Супер важная задача")
    private String title;


    public OpenQuestionCardDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

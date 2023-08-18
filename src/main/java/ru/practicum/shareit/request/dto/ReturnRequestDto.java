package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReturnRequestDto {
    @NotNull
    private Long id;
    @NotBlank
    private String description;
    private List<ItemRequestDto> items;
    @NotNull
    private LocalDateTime created;
}

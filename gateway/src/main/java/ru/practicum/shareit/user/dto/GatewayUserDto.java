package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.dto.GatewayCommentDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GatewayUserDto {
    private Long id;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    private List<GatewayCommentDto> comments;
}

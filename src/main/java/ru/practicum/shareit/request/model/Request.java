package ru.practicum.shareit.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @NotBlank
    @Column(name = "request_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private User requestor;

    @NotNull
    @Column(name = "request_created")
    private LocalDateTime created;
}

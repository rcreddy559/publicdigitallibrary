package com.library.dto;

import com.library.model.LibraryUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {

    private Flux<LibraryUser> users;
    private Flux<LibraryUser> rolesByRole;

}

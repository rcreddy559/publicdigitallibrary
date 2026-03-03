package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("library_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUser {

    @Id
    private Long id;
    @Column("full_name")
    private String fullName;
    private String email;
    private String phone;
    private Boolean active;
}

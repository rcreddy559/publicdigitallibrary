package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("dashboard")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {

    @Id
    private Long id;
    @Column("widget_name")
    private String widgetName;
    @Column("widget_type")
    private String widgetType;
    private String status;
    @Column("display_order")
    private Integer displayOrder;
}

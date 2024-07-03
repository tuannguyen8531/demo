package com.dev.server.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = true)
    private String link;

    @Column(name = "level", nullable = true)
    private int level;

    @Column(name = "menu_order", nullable = true)
    private int menuOrder;

    @Column(name = "group_id", nullable = true)
    private int groupId;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    public Menu(String title, String link, int level, int menuOrder, int groupId) {
        this.title = title;
        this.link = link;
        this.level = level;
        this.menuOrder = menuOrder;
        this.groupId = groupId;
    }
}

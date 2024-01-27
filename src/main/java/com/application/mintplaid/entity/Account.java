package com.application.mintplaid.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "account")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String plaidAccountId;

    private Double currentBalance;

    private Double availableBalance;

    private String name;

    private String type;

    private String subtype;

    @JoinColumn(referencedColumnName = "item_id")
    @Column(name = "item")
    private String item;
}

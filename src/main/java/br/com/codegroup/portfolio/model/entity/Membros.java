package br.com.codegroup.portfolio.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "membros")
public class Membros {

    @EmbeddedId
    private MembroId id;
}

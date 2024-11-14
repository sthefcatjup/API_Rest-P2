import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany
    @JoinTable(
        name = "colaborador_tarefa",
        joinColumns = @JoinColumn(name = "colaborador_id"),
        inverseJoinColumns = @JoinColumn(name = "tarefa_id")
    )
    private Set<Tarefa> tarefas = new HashSet<>();

    // Construtores, Getters e Setters
    public Colaborador() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Outros getters e setters omitidos para brevidade
}
 

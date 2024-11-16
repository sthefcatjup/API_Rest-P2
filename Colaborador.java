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

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}

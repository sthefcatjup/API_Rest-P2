import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    public List<Tarefa> getAllTarefas() {
        return tarefaRepository.findAll();
    }

    @PostMapping
    public Tarefa createTarefa(@RequestBody Tarefa tarefa) {
        tarefa.setDataCriacao(LocalDate.now());
        return tarefaRepository.save(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetails) {
        return tarefaRepository.findById(id)
            .map(tarefa -> {
                tarefa.setTitulo(tarefaDetails.getTitulo());
                tarefa.setDescricao(tarefaDetails.getDescricao());
                tarefa.setDataInicio(tarefaDetails.getDataInicio());
                tarefa.setDataConclusao(tarefaDetails.getDataConclusao());
                return ResponseEntity.ok(tarefaRepository.save(tarefa));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id)
            .map(tarefa -> {
                tarefaRepository.delete(tarefa);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

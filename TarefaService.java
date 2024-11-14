import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa atualizarDataConclusao(Long tarefaId, LocalDate dataConclusao) {
        return tarefaRepository.findById(tarefaId)
            .map(tarefa -> {
                tarefa.setDataConclusao(dataConclusao);
                return tarefaRepository.save(tarefa);
            })
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));
    }
}

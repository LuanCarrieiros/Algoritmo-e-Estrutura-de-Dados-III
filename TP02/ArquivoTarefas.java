import aed3.Arquivo;
import aed3.ArvoreBMais;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class ArquivoTarefas extends Arquivo<Tarefa> {

    private ArvoreBMais<ParIdId> indiceCategoriaTarefa;
    
    // Construtor que inicializa o arquivo de tarefas e o índice para o
    // relacionamento 1:N
    public ArquivoTarefas(Constructor<Tarefa> construtor, String nomeArquivo) throws Exception {
        super(construtor, nomeArquivo);
        indiceCategoriaTarefa = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5,
                "dados/arvoreTarefasPorCategoria.db");
    }

    // Método para criar uma nova tarefa no arquivo e no índice 1:N
    @Override
    public void create(Tarefa tarefa) throws Exception {
        super.create(tarefa);
        indiceCategoriaTarefa.create(new ParIdId(tarefa.getIdCategoria(), tarefa.getId()));
    }

    // Método para ler uma tarefa pelo ID
    @Override
    public Tarefa read(int id) throws Exception {
        return super.read(id);
    }

    // Método para atualizar uma tarefa existente
    @Override
    public boolean update(Tarefa tarefa) throws Exception {
        Tarefa antigaTarefa = read(tarefa.getId());
        if (antigaTarefa != null) {
            // Atualizar o índice 1:N caso o idCategoria tenha sido modificado
            if (antigaTarefa.getIdCategoria() != tarefa.getIdCategoria()) {
                indiceCategoriaTarefa.delete(new ParIdId(antigaTarefa.getIdCategoria(), antigaTarefa.getId()));
                indiceCategoriaTarefa.create(new ParIdId(tarefa.getIdCategoria(), tarefa.getId()));
            }
            return super.update(tarefa);
        }
        return false;
    }

    // Método para excluir uma tarefa pelo ID e atualizar o índice 1:N
    @Override
    public boolean delete(int id) throws Exception {
        Tarefa tarefa = read(id);
        if (tarefa != null) {
            indiceCategoriaTarefa.delete(new ParIdId(tarefa.getIdCategoria(), id));
            return super.delete(id);
        }
        return false;
    }

    // Método para buscar todas as tarefas por categoria
    public ArrayList<Tarefa> buscarPorCategoria(int idCategoria) throws Exception {
        ArrayList<Tarefa> tarefas = new ArrayList<>();
        ArrayList<ParIdId> pares = indiceCategoriaTarefa.read(new ParIdId(idCategoria, -1));
        for (ParIdId par : pares) {
            Tarefa tarefa = read(par.getId2());
            if (tarefa != null) {
                tarefas.add(tarefa);
            }
        }
        return tarefas;
    }

    public ArrayList<Tarefa> listarTodasTarefas() throws Exception {
        ArrayList<Tarefa> tarefas = new ArrayList<>();
        int id = 0;
        int contagemNulls = 0; // Contador de consecutivos nulos
        int limiteNulls = 10;  // Limite de nulls consecutivos antes de parar
    
        while (contagemNulls < limiteNulls) {
            Tarefa tarefa = read(id);
            if (tarefa != null) {
                tarefas.add(tarefa);
                contagemNulls = 0; // Resetar contador de nulls ao encontrar uma tarefa válida
            } else {
                contagemNulls++;
            }
            id++;
        }
    
        return tarefas;
    }
    
}

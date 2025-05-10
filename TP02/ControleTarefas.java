import java.util.ArrayList;

public class ControleTarefas {
    private ArquivoTarefas arquivoTarefas;
    private ArquivoCategorias arquivoCategorias;

    public ControleTarefas(ArquivoTarefas arquivoTarefas, ArquivoCategorias arquivoCategorias) {
        this.arquivoTarefas = arquivoTarefas;
        this.arquivoCategorias = arquivoCategorias;
    }

    // Método para adicionar uma nova tarefa
    public boolean adicionarTarefa(Tarefa tarefa) throws Exception {
        arquivoTarefas.create(tarefa);
        return true; // Assumindo que a criação é bem-sucedida
    }

    // Método para atualizar uma tarefa existente
    public boolean atualizarTarefa(Tarefa tarefa) throws Exception {
        return arquivoTarefas.update(tarefa);
    }

    // Método para excluir uma tarefa
    public boolean excluirTarefa(int idTarefa) throws Exception {
        return arquivoTarefas.delete(idTarefa);
    }

    // Método para listar todas as tarefas
    public ArrayList<Tarefa> listarTodasTarefas() throws Exception {
        return arquivoTarefas.listarTodasTarefas();
    }

    // Método para listar todas as categorias (para seleção na visão de tarefas)
    public ArrayList<Categoria> listarTodasCategorias() throws Exception {
        return arquivoCategorias.listarTodasCategorias();
    }
}

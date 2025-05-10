import java.util.ArrayList;

public class ControleCategorias {
    private ArquivoCategorias arquivoCategorias;
    private ArquivoTarefas arquivoTarefas;

    public ControleCategorias(ArquivoCategorias arquivoCategorias, ArquivoTarefas arquivoTarefas) {
        this.arquivoCategorias = arquivoCategorias;
        this.arquivoTarefas = arquivoTarefas;
    }

    // Método para adicionar uma nova categoria
    public boolean adicionarCategoria(Categoria categoria) throws Exception {
        // Verifica se já existe uma categoria com o mesmo nome usando o índice
        ArrayList<Categoria> categoriasExistentes = arquivoCategorias.buscarPorNome(categoria.getNome());
        if (!categoriasExistentes.isEmpty()) {
            System.out.println(
                    "Categoria com o nome '" + categoria.getNome() + "' já existe. Não é possível criar duplicatas.");
            return false; // Impede a criação da categoria
        }

        // Se não houver duplicata, cria a nova categoria
        arquivoCategorias.create(categoria);
        return true;
    }

    // Método para excluir uma categoria somente se não houver tarefas associadas
    public boolean excluirCategoria(int idCategoria) throws Exception {
        ArrayList<Tarefa> tarefas = arquivoTarefas.buscarPorCategoria(idCategoria);
        if (tarefas.isEmpty()) {
            return arquivoCategorias.delete(idCategoria);
        } else {
            System.out.println("Não é possível excluir: há tarefas vinculadas a esta categoria.");
            return false;
        }
    }
    

    // Método para gerar um relatório de tarefas por categoria
    public void gerarRelatorioTarefasPorCategoria() throws Exception {
        ArrayList<Categoria> categorias = arquivoCategorias.listarTodasCategorias();
        for (Categoria categoria : categorias) {
            System.out.println("\nCategoria: " + categoria.getNome() +"\n");
            ArrayList<Tarefa> tarefas = arquivoTarefas.buscarPorCategoria(categoria.getId());
            for (Tarefa tarefa : tarefas) {
                System.out.println("- Tarefa(s): \n" + tarefa.toString());
            }
        }
    }

    public ArrayList<Categoria> listarTodasCategorias() throws Exception {
        return arquivoCategorias.listarTodasCategorias();
    }

    public boolean atualizarCategoria(Categoria categoria) throws Exception {
        return arquivoCategorias.update(categoria);
    }

}

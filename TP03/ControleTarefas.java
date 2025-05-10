import java.util.ArrayList;

public class ControleTarefas {
    private ArquivoTarefas arquivoTarefas;
    private ArquivoCategorias arquivoCategorias;
    private ControleIndiceInvertido controleIndiceInvertido;
    private ArquivoRotulos arquivoRotulos;

    public ControleTarefas(ArquivoTarefas arquivoTarefas, ArquivoCategorias arquivoCategorias,
            ArquivoRotulos arquivoRotulos) throws Exception {
        this.arquivoTarefas = arquivoTarefas;
        this.arquivoCategorias = arquivoCategorias;
        this.arquivoRotulos = arquivoRotulos; // Inicializa corretamente o atributo
        this.controleIndiceInvertido = new ControleIndiceInvertido();
    }

    // ===================== MÉTODOS DA TAREFA =====================
    // Método para adicionar uma nova tarefa
    public boolean adicionarTarefa(Tarefa tarefa) throws Exception {
        arquivoTarefas.create(tarefa);
        controleIndiceInvertido.adicionarAoIndice(tarefa.getId(), tarefa.getNome());
        return true;
    }

    // Método para atualizar uma tarefa existente
    public boolean atualizarTarefa(Tarefa tarefa) throws Exception {
        // Recupera a tarefa antiga para obter sua descrição
        Tarefa tarefaAntiga = arquivoTarefas.read(tarefa.getId());

        // Atualiza a tarefa no arquivo
        boolean atualizado = arquivoTarefas.update(tarefa);
        if (atualizado) {
            // Atualiza o índice invertido
            controleIndiceInvertido.atualizarIndice(tarefa.getId(), tarefaAntiga.getNome(), tarefa.getNome());
        }

        return atualizado;
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

    // =================================== MÉTODOS QUE AUXILIAM O RÓTULO
    // ===================================
    // Método para excluir uma tarefa e remover todas as associações com rótulos
    public boolean excluirTarefaComAssociacoes(int idTarefa) throws Exception {
        // Recupera os rótulos associados à tarefa
        ArrayList<Rotulo> rotulosAssociados = arquivoRotulos.buscarRotulosPorTarefa(idTarefa);

        // Remove todas as associações entre a tarefa e os rótulos
        for (Rotulo rotulo : rotulosAssociados) {
            arquivoRotulos.desassociarRotuloDeTarefa(idTarefa, rotulo.getId());
        }

        // Remove a tarefa do índice invertido
        Tarefa tarefa = arquivoTarefas.read(idTarefa);
        controleIndiceInvertido.removerDoIndice(idTarefa, tarefa.getNome());

        // Exclui a tarefa do arquivo
        return arquivoTarefas.delete(idTarefa);
    }

    public ArrayList<Rotulo> listarRotulosPorTarefa(int idTarefa) throws Exception {
        return arquivoRotulos.buscarRotulosPorTarefa(idTarefa);
    }

    public void listarTarefasComRotulos() throws Exception {
        ArrayList<Tarefa> tarefas = arquivoTarefas.listarTodasTarefas();

        for (Tarefa tarefa : tarefas) {
            System.out.println("Tarefa:");
            System.out.println(tarefa);

            // Buscar os rótulos associados
            ArrayList<Rotulo> rotulos = arquivoRotulos.buscarRotulosPorTarefa(tarefa.getId());
            if (!rotulos.isEmpty()) {
                System.out.println("Rótulos associados:");
                for (Rotulo rotulo : rotulos) {
                    System.out.println("\t- " + rotulo.getRotulo());
                }
            } else {
                System.out.println("Nenhum rótulo associado.");
            }
        }
    }

    public boolean adicionarRotuloATarefa(int idTarefa, int idRotulo) throws Exception {
        Tarefa tarefa = arquivoTarefas.read(idTarefa); // Lê a tarefa do arquivo
        if (tarefa == null) {
            System.out.println("Erro: Tarefa com ID " + idTarefa + " não encontrada.");
            return false;
        }

        // Verifica se o rótulo já está associado
        ArrayList<Integer> idsRotulos = tarefa.getIDsRotulos();
        if (!idsRotulos.contains(idRotulo)) {
            idsRotulos.add(idRotulo);
            tarefa.setIDsRotulos(idsRotulos);
            arquivoTarefas.update(tarefa); // Atualiza a tarefa no arquivo

            // Atualiza as árvores B+ pelo ArquivoRotulos
            arquivoRotulos.associarRotuloATarefa(idTarefa, idRotulo);

            System.out.println("Rótulo " + idRotulo + " associado à tarefa " + idTarefa);
            return true;
        } else {
            System.out.println("Rótulo já está associado à tarefa.");
            return false;
        }
    }

    public boolean excluirRotuloDeTarefa(int idTarefa, int idRotulo) throws Exception {
        Tarefa tarefa = arquivoTarefas.read(idTarefa); // Lê a tarefa do arquivo
        if (tarefa == null) {
            System.out.println("Erro: Tarefa com ID " + idTarefa + " não encontrada.");
            return false;
        }

        ArrayList<Integer> idsRotulos = tarefa.getIDsRotulos();
        if (idsRotulos.contains(idRotulo)) {
            idsRotulos.remove((Integer) idRotulo); // Remove o rótulo da lista
            tarefa.setIDsRotulos(idsRotulos);
            arquivoTarefas.update(tarefa); // Atualiza a tarefa no arquivo

            // Atualiza as árvores B+ pelo ArquivoRotulos
            arquivoRotulos.desassociarRotuloDeTarefa(idTarefa, idRotulo);

            System.out.println("Rótulo " + idRotulo + " removido da tarefa " + idTarefa);
            return true;
        } else {
            System.out.println("Rótulo não está associado à tarefa.");
            return false;
        }
    }

    public ArrayList<Rotulo> listarRotulosDeTarefa(int idTarefa) throws Exception {
        Tarefa tarefa = arquivoTarefas.read(idTarefa); // Lê a tarefa do arquivo
        if (tarefa == null) {
            System.out.println("Erro: Tarefa com ID " + idTarefa + " não encontrada.");
            return new ArrayList<>();
        }

        ArrayList<Rotulo> rotulos = new ArrayList<>();
        for (int idRotulo : tarefa.getIDsRotulos()) {
            Rotulo rotulo = arquivoRotulos.read(idRotulo);
            if (rotulo != null) {
                rotulos.add(rotulo);
            }
        }
        return rotulos;
    }

    public boolean atualizarRotulosDeTarefa(int idTarefa, ArrayList<Integer> novosIdsRotulos) throws Exception {
        Tarefa tarefa = arquivoTarefas.read(idTarefa); // Lê a tarefa do arquivo
        if (tarefa == null) {
            System.out.println("Erro: Tarefa com ID " + idTarefa + " não encontrada.");
            return false;
        }

        // Remove associações antigas
        ArrayList<Integer> idsRotulosAtuais = tarefa.getIDsRotulos();
        for (int idRotulo : idsRotulosAtuais) {
            arquivoRotulos.desassociarRotuloDeTarefa(idTarefa, idRotulo);
        }

        // Adiciona as novas associações
        for (int idRotulo : novosIdsRotulos) {
            arquivoRotulos.associarRotuloATarefa(idTarefa, idRotulo);
        }

        // Atualiza os IDs de rótulos na tarefa
        tarefa.setIDsRotulos(novosIdsRotulos);
        return arquivoTarefas.update(tarefa);
    }

    // ===================== MÉTODOS PARA BUSCA DE TAREFAS POR NOME COM A LISTA
    // INVERTIDA =========================

    // Buscar tarefas por termo no índice invertido
    public ArrayList<Tarefa> buscarTarefasPorTermo(String termo) throws Exception {
        ArrayList<Integer> idsTarefas = controleIndiceInvertido.buscarPorTermo(termo);
        ArrayList<Tarefa> tarefasEncontradas = new ArrayList<>();

        for (int id : idsTarefas) {
            Tarefa tarefa = arquivoTarefas.read(id);
            if (tarefa != null) {
                tarefasEncontradas.add(tarefa);
            }
        }
        return tarefasEncontradas;
    }

    public ArrayList<Rotulo> buscarRotulosPorNome(String nomeRotulo) throws Exception {
        ArrayList<Rotulo> rotulosEncontrados = new ArrayList<>();
        ArrayList<Rotulo> todosRotulos = arquivoRotulos.list(); // Lista todos os rótulos no arquivo

        for (Rotulo rotulo : todosRotulos) {
            if (rotulo.getRotulo().equalsIgnoreCase(nomeRotulo)) {
                rotulosEncontrados.add(rotulo);
            }
        }

        return rotulosEncontrados;
    }

    public ArrayList<Tarefa> buscarTarefaPorRotulo(String nomeRotulo) throws Exception {
        ArrayList<Rotulo> rotulosEncontrados = buscarRotulosPorNome(nomeRotulo);
        ArrayList<Tarefa> tarefasEncontradas = new ArrayList<>();

        for (Rotulo rotulo : rotulosEncontrados) {
            ArrayList<Integer> idsTarefas = arquivoRotulos.buscarTarefasPorRotulo(rotulo.getId());
            for (int idTarefa : idsTarefas) {
                Tarefa tarefa = arquivoTarefas.read(idTarefa);
                if (tarefa != null) {
                    tarefasEncontradas.add(tarefa);
                }
            }
        }

        return tarefasEncontradas;
    }

}

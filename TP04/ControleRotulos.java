import java.util.ArrayList;

public class ControleRotulos {
    private ArquivoRotulos arquivoRotulos;
    private ArquivoTarefas arquivoTarefas;

    // Construtor
    public ControleRotulos(ArquivoRotulos arquivoRotulos, ArquivoTarefas arquivoTarefas) {
        this.arquivoRotulos = arquivoRotulos;
        this.arquivoTarefas = arquivoTarefas;
    }

    // Criar um novo rótulo, verificando se já existe
    public boolean criarRotulo(Rotulo rotulo) throws Exception {
        // Verificar se um rótulo com o mesmo nome já existe
        ArrayList<Rotulo> rotulosExistentes = arquivoRotulos.list();
        for (Rotulo r : rotulosExistentes) {
            if (r.getRotulo().equalsIgnoreCase(rotulo.getRotulo())) {
                System.out.println("Rótulo já existe: " + r.getRotulo());
                return false; // Não cria o rótulo duplicado
            }
        }

        // Cria o rótulo caso não exista
        arquivoRotulos.create(rotulo);
        return true;
    }

    // Atualizar um rótulo existente
    public boolean atualizarRotulo(Rotulo rotulo) throws Exception {
        // Verifica se o rótulo existe antes de atualizar
        Rotulo rotuloExistente = arquivoRotulos.read(rotulo.getId());
        if (rotuloExistente == null) {
            System.out.println("Rótulo não encontrado para atualização: ID " + rotulo.getId());
            return false;
        }

        // Atualiza o rótulo no arquivo
        return arquivoRotulos.update(rotulo);
    }

    // Excluir um rótulo apenas se não houver tarefas associadas
    public boolean excluirRotulo(int idRotulo) throws Exception {
        // Verifica se há tarefas associadas ao rótulo
        ArrayList<Integer> idsTarefasAssociadas = arquivoRotulos.buscarTarefasPorRotulo(idRotulo);

        if (!idsTarefasAssociadas.isEmpty()) {
            System.out.println("Rótulo está associado a tarefas e não pode ser excluído diretamente.");
            System.out.println("Tarefas associadas: " + idsTarefasAssociadas.size());
            return false;
        }

        // Remove o rótulo caso não esteja associado a nenhuma tarefa
        return arquivoRotulos.delete(idRotulo);
    }

    public ArrayList<Rotulo> listarTodosRotulos() throws Exception {
        return arquivoRotulos.list();
    }
    
    // Associar um rótulo a uma tarefa
    public void associarRotuloATarefa(int idTarefa, int idRotulo) throws Exception {
        // Verifica se a tarefa existe
        if (arquivoTarefas.read(idTarefa) == null) {
            throw new Exception("Tarefa não encontrada para associação: ID " + idTarefa);
        }

        // Verifica se o rótulo existe
        if (arquivoRotulos.read(idRotulo) == null) {
            throw new Exception("Rótulo não encontrado para associação: ID " + idRotulo);
        }

        // Realiza a associação
        arquivoRotulos.associarRotuloATarefa(idTarefa, idRotulo);
    }

    // Remover associação entre rótulo e tarefa
    public void desassociarRotuloDeTarefa(int idTarefa, int idRotulo) throws Exception {
        // Verifica se a associação existe antes de remover
        ArrayList<Rotulo> rotulosAssociados = buscarRotulosPorTarefa(idTarefa);
        boolean encontrado = false;

        for (Rotulo rotulo : rotulosAssociados) {
            if (rotulo.getId() == idRotulo) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new Exception("A associação entre a tarefa e o rótulo não existe.");
        }

        // Remove a associação
        arquivoRotulos.desassociarRotuloDeTarefa(idTarefa, idRotulo);
    }

    // Listar tarefas associadas a um rótulo
    public ArrayList<Tarefa> listarTarefasPorRotulo(int idRotulo) throws Exception {
        ArrayList<Integer> idsTarefas = arquivoRotulos.buscarTarefasPorRotulo(idRotulo);
        ArrayList<Tarefa> tarefas = new ArrayList<>();

        for (int idTarefa : idsTarefas) {
            Tarefa tarefa = arquivoTarefas.read(idTarefa);
            if (tarefa != null) {
                tarefas.add(tarefa);
            }
        }

        return tarefas;
    }

    public ArrayList<Rotulo> buscarRotulosPorTarefa(int idTarefa) throws Exception {
        return arquivoRotulos.buscarRotulosPorTarefa(idTarefa);
    }

    public Rotulo buscarRotuloPorId(int idRotulo) throws Exception {
        return arquivoRotulos.read(idRotulo);
    }
    
    
}

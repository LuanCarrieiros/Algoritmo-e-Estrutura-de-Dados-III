import aed3.Arquivo;
import aed3.ArvoreBMais;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class ArquivoRotulos extends Arquivo<Rotulo> {

    private ArvoreBMais<ParIdId> tarefaParaRotulo;
    private ArvoreBMais<ParIdId> rotuloParaTarefa;


    // Construtor
    public ArquivoRotulos(Constructor<Rotulo> construtor, String nomeArquivo) throws Exception {
        super(construtor, nomeArquivo);

        // Inicializa as Árvores B+ para gerenciar o relacionamento N:N
        tarefaParaRotulo = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "dados/arvoreTarefaParaRotulo.db");
        rotuloParaTarefa = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "dados/arvoreRotuloParaTarefa.db");
       
    }

    // Criar um novo rótulo
    @Override
    public void create(Rotulo rotulo) throws Exception {
        // Verifica duplicidade
        ArrayList<Rotulo> rotulosExistentes = this.list();
        for (Rotulo r : rotulosExistentes) {
            if (r.getRotulo().equalsIgnoreCase(rotulo.getRotulo())) {
                throw new Exception("Rótulo com o mesmo nome já existe.");
            }
        }

        // Salva o rótulo no arquivo principal
        super.create(rotulo);
    }

    // Excluir um rótulo pelo ID
    @Override
    public boolean delete(int id) throws Exception {
        // Verifica se o rótulo possui associações antes de excluir
        ArrayList<ParIdId> associacoes = rotuloParaTarefa.read(new ParIdId(id, -1));
        if (!associacoes.isEmpty()) {
            throw new Exception("Não é possível excluir o rótulo: ele está associado a tarefas.");
        }

        // Remove o rótulo da árvore e do arquivo principal
        return super.delete(id);
    }

    // Associar um rótulo a uma tarefa
    public void associarRotuloATarefa(int idTarefa, int idRotulo) throws Exception {
        tarefaParaRotulo.create(new ParIdId(idTarefa, idRotulo));
        rotuloParaTarefa.create(new ParIdId(idRotulo, idTarefa));
    }

    // Remover associação entre rótulo e tarefa
    public void desassociarRotuloDeTarefa(int idTarefa, int idRotulo) throws Exception {
        tarefaParaRotulo.delete(new ParIdId(idTarefa, idRotulo));
        rotuloParaTarefa.delete(new ParIdId(idRotulo, idTarefa));
    }

    
    @Override
    public ArrayList<Rotulo> list() throws Exception {
       return super.list();
    }

    public ArrayList<Rotulo> buscarRotulosPorTarefa(int idTarefa) throws Exception {
        ArrayList<Rotulo> rotulosAssociados = new ArrayList<>();
        ArrayList<ParIdId> pares = tarefaParaRotulo.read(new ParIdId(idTarefa, -1)); // Busca pares Tarefa -> Rótulo

        for (ParIdId par : pares) {
            Rotulo rotulo = this.read(par.getId2()); // Lê o rótulo pelo ID
            if (rotulo != null) {
                rotulosAssociados.add(rotulo);
            }
        }

        return rotulosAssociados;
    }

    // Buscar tarefas associadas a um rótulo
    public ArrayList<Integer> buscarTarefasPorRotulo(int idRotulo) throws Exception {
        ArrayList<Integer> idsTarefas = new ArrayList<>();
        ArrayList<ParIdId> pares = rotuloParaTarefa.read(new ParIdId(idRotulo, -1));

        for (ParIdId par : pares) {
            idsTarefas.add(par.getId2());
        }

        return idsTarefas;
    }

}

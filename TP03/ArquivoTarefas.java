import aed3.Arquivo;
import aed3.ArvoreBMais;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class ArquivoTarefas extends Arquivo<Tarefa> {

    private ArvoreBMais<ParIdId> indiceCategoriaTarefa; 
    private ArvoreBMais<ParIdId> indiceTarefaRotulo; 
    private ArvoreBMais<ParIdId> indiceRotuloTarefa; 

    public ArquivoTarefas(Constructor<Tarefa> construtor, String nomeArquivo) throws Exception {
        super(construtor, nomeArquivo);
        indiceCategoriaTarefa = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5,
                "dados/arvoreTarefasPorCategoria.db");

        indiceTarefaRotulo = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5,
                "dados/arvoreTarefaParaRotulo.db");

        indiceRotuloTarefa = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5,
                "dados/arvoreRotuloParaTarefa.db");
    }

    @Override
    public Tarefa read(int id) throws Exception {
        return super.read(id); 
    }

    @Override
    public void create(Tarefa tarefa) throws Exception {
        // Verifica duplicidade de nomes
        ArrayList<Tarefa> tarefasExistentes = this.list();
        for (Tarefa t : tarefasExistentes) {
            if (t.getNome().equalsIgnoreCase(tarefa.getNome())) {
                throw new Exception("Tarefa com o mesmo nome já existe.");
            }
        }

        // Cria a tarefa no arquivo principal
        super.create(tarefa);

        // Índice Categoria → Tarefa (1:N)
        indiceCategoriaTarefa.create(new ParIdId(tarefa.getIdCategoria(), tarefa.getId()));

        // Índices Tarefa ↔ Rótulo (N:N)
        // Para cada rótulo desta tarefa, adicionamos a relação nos dois índices
        for (int idRotulo : tarefa.getIDsRotulos()) {
            indiceTarefaRotulo.create(new ParIdId(tarefa.getId(), idRotulo));
            indiceRotuloTarefa.create(new ParIdId(idRotulo, tarefa.getId()));
        }
    }

    @Override
    public boolean update(Tarefa tarefa) throws Exception {
        Tarefa antigaTarefa = read(tarefa.getId());
        if (antigaTarefa != null) {
            // Caso a categoria tenha mudado, atualiza o índice Categoria → Tarefa
            if (antigaTarefa.getIdCategoria() != tarefa.getIdCategoria()) {
                indiceCategoriaTarefa.delete(new ParIdId(antigaTarefa.getIdCategoria(), antigaTarefa.getId()));
                indiceCategoriaTarefa.create(new ParIdId(tarefa.getIdCategoria(), tarefa.getId()));
            }

            // Atualiza os índices de rótulos
            ArrayList<Integer> antigosRotulos = antigaTarefa.getIDsRotulos();
            ArrayList<Integer> novosRotulos = tarefa.getIDsRotulos();

            // Remove as associações de rótulos que não existem mais
            for (int idRotAntigo : antigosRotulos) {
                if (!novosRotulos.contains(idRotAntigo)) {
                    indiceTarefaRotulo.delete(new ParIdId(tarefa.getId(), idRotAntigo));
                    indiceRotuloTarefa.delete(new ParIdId(idRotAntigo, tarefa.getId()));
                }
            }

            // Adiciona as novas associações de rótulos
            for (int idRotNovo : novosRotulos) {
                if (!antigosRotulos.contains(idRotNovo)) {
                    indiceTarefaRotulo.create(new ParIdId(tarefa.getId(), idRotNovo));
                    indiceRotuloTarefa.create(new ParIdId(idRotNovo, tarefa.getId()));
                }
            }

            return super.update(tarefa);
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Tarefa tarefa = super.read(id);
        if (tarefa == null) {
            System.out.println("Erro: Registro não encontrado no arquivo para exclusão.");
            return false;
        }

        long posicao = arquivo.getFilePointer() - (1 + 2 + tarefa.toByteArray().length); 
        arquivo.seek(posicao);
        arquivo.writeByte('*'); // Marca como excluído

        // Remove do índice direto (definido na superclasse Arquivo)
        indiceDireto.delete(id);

        // Remove as associações de rótulos (N:N)
        for (int idRotulo : tarefa.getIDsRotulos()) {
            indiceTarefaRotulo.delete(new ParIdId(tarefa.getId(), idRotulo));
            indiceRotuloTarefa.delete(new ParIdId(idRotulo, tarefa.getId()));
        }

        // Remove do índice Categoria → Tarefa (1:N)
        indiceCategoriaTarefa.delete(new ParIdId(tarefa.getIdCategoria(), tarefa.getId()));

        System.out.println("Tarefa removida do índice e marcada como excluída.");
        return true;
    }

    public ArrayList<Tarefa> buscarPorCategoria(int idCategoria) throws Exception {
        ArrayList<Tarefa> tarefasAssociadas = new ArrayList<>();
        ArrayList<Tarefa> todasTarefas = list(); 
        for (Tarefa tarefa : todasTarefas) {
            if (tarefa.getIdCategoria() == idCategoria) {
                tarefasAssociadas.add(tarefa);
            }
        }
        return tarefasAssociadas;
    }

    public ArrayList<Tarefa> listarTodasTarefas() throws Exception {
        return super.list();
    }

    
    public ArrayList<Integer> getRotulosDaTarefa(int idTarefa) throws Exception {
        // Agora usamos read em vez de readAll, e definimos id2 = -1 para pegar todos com mesmo id1
        ArrayList<ParIdId> resultados = indiceTarefaRotulo.read(new ParIdId(idTarefa, -1));
        ArrayList<Integer> rotulos = new ArrayList<>();
        for (ParIdId p : resultados) {
            if (p.getId1() == idTarefa) {
                rotulos.add(p.getId2());
            }
        }
        return rotulos;
    }
    
    public ArrayList<Integer> getTarefasDoRotulo(int idRotulo) throws Exception {
        ArrayList<ParIdId> resultados = indiceRotuloTarefa.read(new ParIdId(idRotulo, -1));
        ArrayList<Integer> tarefas = new ArrayList<>();
        for (ParIdId p : resultados) {
            if (p.getId1() == idRotulo) {
                tarefas.add(p.getId2());
            }
        }
        return tarefas;
    }
    
}

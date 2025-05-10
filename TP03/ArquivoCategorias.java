import aed3.Arquivo;
import aed3.ArvoreBMais;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.reflect.Field;

public class ArquivoCategorias extends Arquivo<Categoria> {

    private ArvoreBMais<ParNomeId> indiceNomeCategoria;

    // Construtor que inicializa o arquivo de categorias e o índice por nome
    public ArquivoCategorias(Constructor<Categoria> construtor, String nomeArquivo) throws Exception {
        super(construtor, nomeArquivo);
        // Inicializa a árvore B+ para o índice indireto por nome
        indiceNomeCategoria = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5,
                "dados/arvoreCategoriasPorNome.db");
    }

    // Método para criar uma nova categoria no arquivo e no índice por nome
    @Override
    public void create(Categoria categoria) throws Exception {
        ArrayList<Categoria> categoriasExistentes = this.list();
        for (Categoria c : categoriasExistentes) {
            if (c.getNome().equalsIgnoreCase(categoria.getNome())) {
                throw new Exception("Categoria com o mesmo nome já existe.");
            }
        }
        super.create(categoria);
        // Adiciona ao índice por nome com o par nome-ID
        indiceNomeCategoria.create(new ParNomeId(categoria.getNome(), categoria.getId()));
    }

    // // Método para ler uma categoria pelo ID
    // @Override
    // public Categoria read(int id) throws Exception {
    // return super.read(id);
    // }

    // Método para atualizar uma categoria existente
    @Override
    public boolean update(Categoria categoria) throws Exception {
        Categoria antigaCategoria = read(categoria.getId());
        if (antigaCategoria != null) {
            // Atualizar o índice por nome caso o nome tenha sido modificado
            if (!antigaCategoria.getNome().equals(categoria.getNome())) {
                indiceNomeCategoria.delete(new ParNomeId(antigaCategoria.getNome(), antigaCategoria.getId()));
                indiceNomeCategoria.create(new ParNomeId(categoria.getNome(), categoria.getId()));
            }
            return super.update(categoria);
        }
        return false;
    }

    // Método para excluir uma categoria pelo ID e atualizar o índice por nome
    @Override
    public boolean delete(int id) throws Exception {
        Categoria categoria = read(id);
        if (categoria != null) {
      
            // Remover do índice por nome e exclusão física
            indiceNomeCategoria.delete(new ParNomeId(categoria.getNome(), id));
            return super.delete(id); // Exclusão física no armazenamento
        }
        return false;
    }

    // Método para buscar uma categoria pelo nome
    public ArrayList<Categoria> buscarPorNome(String nome) throws Exception {
        ArrayList<Categoria> categorias = new ArrayList<>();
        ArrayList<ParNomeId> pares = indiceNomeCategoria.read(new ParNomeId(nome, -1));

        // Obtém o campo `id` de ParNomeId usando reflexão
        Field idField = ParNomeId.class.getDeclaredField("id");
        idField.setAccessible(true); // Permite acesso ao campo privado

        for (ParNomeId par : pares) {
            // Acessa o valor do campo `id` diretamente
            int idCategoria = (int) idField.get(par);
            Categoria categoria = read(idCategoria);
            if (categoria != null) {
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    // Método para listar todas as categorias usando a árvore B+
    public ArrayList<Categoria> listarTodasCategorias() throws Exception {
        ArrayList<Categoria> categorias = new ArrayList<>();

        // Busca todas as entradas na árvore B+
        ArrayList<ParNomeId> pares = indiceNomeCategoria.read(null);

        // Itera pelos pares para recuperar as categorias correspondentes
        for (ParNomeId par : pares) {
            Categoria categoria = read(par.getId());
            if (categoria != null) { // Ignora categorias excluídas
                categoria.setNome(formatarNome(categoria.getNome()));
                categorias.add(categoria);
            }
        }

        // Ordena as categorias por nome (não é estritamente necessário, pois a B+ já mantém a ordem)
        categorias.sort(Comparator.comparing(Categoria::getNome));
        return categorias;
    }

    // Método auxiliar para formatar o nome com a primeira letra maiúscula e o restante minúsculo
    private String formatarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return nome;
        }
        return nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    }
}

import java.io.*;
import java.text.Normalizer;
import java.util.*;
import aed3.ListaInvertida;
import aed3.ElementoLista;

public class ControleIndiceInvertido {
    private ListaInvertida listaInvertida;
    private Set<String> stopwords;

    public ControleIndiceInvertido() throws Exception {
        // Inicializa a lista invertida
        listaInvertida = new ListaInvertida(4, "dados/dicionario.listainv.db", "dados/blocos.listainv.db");
        // Carrega as stopwords
        stopwords = carregarStopWords("stopwords.txt");
    }

    // Carregar stop words de um arquivo
    private Set<String> carregarStopWords(String caminho) throws IOException {
        Set<String> stopwords = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                stopwords.add(linha.trim().toLowerCase());
            }
        }
        return stopwords;
    }

    // Processar descrição da tarefa
    private List<String> processarDescricao(String descricao) {
        List<String> palavrasProcessadas = new ArrayList<>();
        String[] palavras = descricao.split("\\s+");

        for (String palavra : palavras) {
            // Remove acentos e converte para minúsculas
            String normalizada = Normalizer.normalize(palavra, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .toLowerCase();

            // Ignora stopwords
            if (!stopwords.contains(normalizada)) {
                palavrasProcessadas.add(normalizada);
            }
        }

        return palavrasProcessadas;
    }

    // Adicionar termos de uma descrição ao índice invertido
    public void adicionarAoIndice(int idTarefa, String descricao) throws Exception {
        List<String> palavras = processarDescricao(descricao);
        Map<String, Integer> frequencias = new HashMap<>();

        // Calcula a frequência de cada termo
        for (String palavra : palavras) {
            frequencias.put(palavra, frequencias.getOrDefault(palavra, 0) + 1);
        }

        // Atualiza a lista invertida
        for (Map.Entry<String, Integer> entrada : frequencias.entrySet()) {
            String termo = entrada.getKey();
            float tf = (float) entrada.getValue() / palavras.size();
            listaInvertida.create(termo, new ElementoLista(idTarefa, tf));
        }
    }

    // Remover uma tarefa do índice invertido
    public void removerDoIndice(int idTarefa, String descricao) throws Exception {
        List<String> palavras = processarDescricao(descricao);
        for (String termo : palavras) {
            listaInvertida.delete(termo, idTarefa);
        }
    }

    // Atualizar o índice ao modificar uma tarefa
    public void atualizarIndice(int idTarefa, String descricaoAntiga, String descricaoNova) throws Exception {
        removerDoIndice(idTarefa, descricaoAntiga);
        adicionarAoIndice(idTarefa, descricaoNova);
    }

    // Buscar IDs de tarefas no índice invertido por termo
    public ArrayList<Integer> buscarPorTermo(String termo) throws Exception {
        ArrayList<Integer> idsTarefas = new ArrayList<>();
        ElementoLista[] elementos = listaInvertida.read(termo.toLowerCase());

        for (ElementoLista elemento : elementos) {
            idsTarefas.add(elemento.getId());
        }
        return idsTarefas;
    }

}

# AEDS III - TP03

## Alunos integrantes da equipe

* Diego Moreira Rocha
* Luan Barbosa Rosa Carrieiros
* Iago Fereguetti Ribeiro 

Neste trabalho prático foi:
  - Implementado o índice invertido
  - Criado a entidade de rótulos.
  - Implementado o arquivo de rótulos
  - Alterado o arquivo de tarefas para fazer relacionamento com rótulos
  - Adcionado a busca de tarefas por rótulo

# Classes e Métodos criados

## Classe ArquivoRotulos

* ### Atributos:

- ArvoreBMAIS<ParIdId> tarefaParaRotulo: associa IDs da tarefa a IDs de rótulos.

- ArvoreBMAIS<ParIdId> roruloParaTarefa: associa IDs da rótulos a IDs de tarefa.

* ### Construtores:

- ArquivoRotulos(Contructor<Rotulo> construtor, String nomeArquivo): Inicializa as Árvores B+ para gerenciar o relacionamento N:N.

* ### Métodos:

- Create

- Delete
  
- associarRotuloATarefa(int idTarefa, int idRotulo): Cria uma associação bidirecional entre tarefa e rótulo nas árvores B+.
  
- desassociarRotuloDeTarefa(int idTarefa, int idRotulo): Remove a associação bidirecional entre tarefa e rótulo nas árvores B+.

- buscarRotulosPorTarefa(int idTarefa): Lê os IDs de rótulos associados à tarefa e os converte em objetos Rotulo.

- buscarTarefasPorRotulos(int idRotulo): Lê os pares associados na árvore rotuloParaTarefa e retorna os IDs das tarefas.
  

## Classe ControleIndiceInvertido

* ### Atributos:

- ListaIvertida: Gerencia a estrutura do índice invertido.

- stopwords: Conjunto de palavras ignoradas durante o processamento das descrições.

* ### Construtores:

- ControleIndiceInvertido(): Inicializa o índice invertido com a instância da classe ListaInvertida.

* ### Métodos:

- carregarStopwords(String caminho): Lê as stopwords do arquivo e as armazena em um conjunto.

- processarDescrição(String descrição): Formata a string removendo acentos e caracteres não-ASCII, converte para letras minusculas e ignora as stopwords.

- adcionarAoIndice(int idTarefa, String descrição): Calcula a frequência dos termos e adiciona cada termo ao índice invertido, associando-o ao ID da tarefa.

- removerDoIndice(int idTarefa, String descrição): Processa a descrição e remove os termos associados à tarefa no índice invertido.

- atualizarIndice(int idTarefa, String descricaoAntiga, String descriçãoNova): Adiciona os termos da nova descrição ao índice.

- buscarPorTermo(String termo): Lê os elementos associados ao termo no índice invertido e retorna os IDs das tarefas.

## Classe ControleRotulos

* ### Atributos:

- ArquivoRotulos: gerencia os dados dos rótulos.

- arquivoTarefas: gerencia os dados das tarefas.

* ### Construtores:

- ControleRotulos(ArquivoRotulos arquivoRotulos, ArquivoTarefas arquivoTarefas): Inicializa os atributos arquivoRotulos e arquivoTarefas com as instâncias fornecidas.

* ### Métodos:

- criarRotulo(Rotulo rotulo): Cria o rótulo apenas se não houver duplicatas.

- atualizarRotulo(Rotulo rotulo): Verifica se o rótulo existe no sistema antes de atualizá-lo.

- excluirRotulo(int idRotulo): Não permite exclusão se existirem associações.

- listarTodosRotulos(): Retorna todos os rótulos registrados.

- associarRotuloATarefa(int idTarefa, int idRotulo): Cria uma associação entre o rótulo e a tarefa se ambos existirem.

- desassociarRotuloDeTarefa(int idTarefa, int idRotulo): Remove a associação caso ela exista.

- listarTarefasPorRotulo(int idRotulo): Busca todas as tarefas associadas a um rótulo específico.

- buscarRotulosPorTarefa(int idTarefa): Busca todos os rótulos associados a uma tarefa específica.

- buscarRotuloPorId(int idRotulo): Busca um rótulo pelo seu ID.

## Classe Rotulo


* ### Atributos:

- int id.

- String rotulo.

- ArrayList<Integer> idsTarefas.

* ### Construtores:

- Rotulo(): Inicializa um rótulo vazio com ID -1, nome vazio e uma lista de tarefas vazia.

- Rotulo(int id, String rotulo): Inicializa um rótulo com os valores fornecidos e uma lista de tarefas vazia.

- Rotulo(String rotulo): Inicializa um rótulo com o nome fornecido, ID -1 e uma lista de tarefas vazia.

- getId(): Retorna o ID do rótulo.

- setId(int id): Define o ID do rótulo.

- getRotulo(): Retorna o nome ou descrição do rótulo.

- setRotulo(String rotulo): Define o nome ou descrição do rótulo.

- getIdsTarefas(): Retorna a lista de IDs das tarefas associadas ao rótulo.

- setIdsTarefas(ArrayList<Integer> idsTarefas): Define a lista de IDs das tarefas associadas.

* ### Métodos:

- adicionarTarefa(int idTarefa): Adiciona um ID de tarefa à lista de IDs de tarefas, caso ainda não esteja presente.

- removerTarefa(int idTarefa): Remove um ID de tarefa da lista de IDs de tarefas associadas.

- toByteArray(): Serializa o objeto Rotulo (ID e nome) em um formato de array de bytes.

- fromByteArray(byte[] ba): Reconstrói um objeto Rotulo a partir de um array de bytes.

- toString(): Formata as informações do rótulo, incluindo ID, nome e lista de tarefas associadas.

- compareTo(Object o): Compara o objeto atual com outro Rotulo com base em seus IDs.

## Experiência

Tivemos problema quando tentavamos deletar uma categoria, que ocasionava em erro. No geral tinhamos que tomar cuidado toda vez que iamos alterar algo no codigo pra não dar erro em outro arquivo.
  
## Perguntas

- O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida? sim
- O CRUD de rótulos foi implementado? sim 
- No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+? sim
- É possível buscar tarefas por palavras usando o índice invertido? sim
- É possível buscar tarefas por rótulos usando uma árvore B+? sim
- O trabalho está completo? sim
- O trabalho é original e não a cópia de um trabalho de um colega? sim

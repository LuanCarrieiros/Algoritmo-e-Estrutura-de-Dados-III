# TP3: Índice Invertido e Relacionamento N\:N

## 📋 Descrição

Implementação de um sistema de **índice invertido** em Java, combinado com relacionamento N\:N entre **tarefas** e **rótulos** usando Árvores B+ para gerenciar as associações. Permite buscas por termos na descrição das tarefas e por rótulos associados.

## ⚙️ Funcionalidades

* **Gerenciar rótulos**: CRUD completo de rótulos sem duplicações e com verificação de associações.
* **Associação bidirecional**: vincula e desvincula múltiplos rótulos a tarefas através de Árvores B+.
* **Índice invertido**: indexa termos das descrições de tarefas para busca rápida.
* **Busca por termo**: pesquisa tarefas que contenham uma palavra-chave.
* **Busca por rótulo**: lista tarefas associadas a um rótulo específico.

## 🏗️ Estrutura de Classes

### ArquivoRotulos

* **Atributos**:

  * `ArvoreBMAIS<ParIdId> tarefaParaRotulo`
  * `ArvoreBMAIS<ParIdId> rotuloParaTarefa`
* **Construtor**:

  * `ArquivoRotulos(Constructor<Rotulo> construtor, String nomeArquivo)`
* **Métodos**:

  * `void associarRotuloATarefa(int idTarefa, int idRotulo)`
  * `void desassociarRotuloDeTarefa(int idTarefa, int idRotulo)`
  * `List<Rotulo> buscarRotulosPorTarefa(int idTarefa)`
  * `List<Integer> buscarTarefasPorRotulos(int idRotulo)`

### ControleIndiceInvertido

* **Atributos**:

  * `ListaInvertida listaInvertida`
  * `Set<String> stopwords`
* **Construtor**:

  * `ControleIndiceInvertido()`
* **Métodos**:

  * `void carregarStopwords(String caminho)`
  * `String processarDescricao(String descricao)`
  * `void adicionarAoIndice(int idTarefa, String descricao)`
  * `void removerDoIndice(int idTarefa, String descricao)`
  * `void atualizarIndice(int idTarefa, String descricaoAntiga, String descricaoNova)`
  * `List<Integer> buscarPorTermo(String termo)`

### ControleRotulos

* **Atributos**:

  * `ArquivoRotulos arquivoRotulos`
  * `ArquivoTarefas arquivoTarefas`
* **Construtor**:

  * `ControleRotulos(ArquivoRotulos arquivoRotulos, ArquivoTarefas arquivoTarefas)`
* **Métodos**:

  * `Rotulo criarRotulo(Rotulo rotulo)`
  * `Rotulo atualizarRotulo(Rotulo rotulo)`
  * `void excluirRotulo(int idRotulo)`
  * `List<Rotulo> listarTodosRotulos()`
  * `void associarRotuloATarefa(int idTarefa, int idRotulo)`
  * `void desassociarRotuloDeTarefa(int idTarefa, int idRotulo)`
  * `List<Integer> listarTarefasPorRotulo(int idRotulo)`
  * `List<Rotulo> buscarRotulosPorTarefa(int idTarefa)`
  * `Rotulo buscarRotuloPorId(int idRotulo)`

### Rotulo

* **Atributos**:

  * `int id`
  * `String rotulo`
  * `List<Integer> idsTarefas`
* **Construtores**:

  * `Rotulo()`
  * `Rotulo(int id, String rotulo)`
  * `Rotulo(String rotulo)`
* **Métodos**:

  * `void adicionarTarefa(int idTarefa)`
  * `void removerTarefa(int idTarefa)`
  * `byte[] toByteArray()`
  * `static Rotulo fromByteArray(byte[] ba)`
  * `String toString()`
  * `int compareTo(Rotulo o)`

## 🛠️ Como Executar

```bash
# Clone o repositório
git clone https://github.com/LuanCarrieiros/Algoritmo-e-Estrutura-de-Dados-III.git
cd Algoritmo-e-Estrutura-de-Dados-III/TP3

```

## ✅ Resultado

* Nota obtida: **5/5**
* Trabalho concluído de forma independente, atendendo a todos os requisitos.

## ❓ Perguntas Frequentes

* **Índice invertido criado com ListaInvertida?** Sim
* **CRUD de rótulos implementado?** Sim
* **Associações gerenciadas em Árvores B+?** Sim
* **Busca por termo e por rótulo funcionando?** Sim
* **Trabalho original?** Sim

# TP1: CRUD em Arquivos Sequenciais

## 📋 Descrição

Implementação de operações CRUD (Create, Read, Update, Delete) em Java usando **índice direto** (tabela hash extensível) para acesso rápido a registros armazenados em um arquivo sequencial.

## ⚙️ Funcionalidades

* **Create**: insere uma nova tarefa no fim do arquivo `dados/`, atualiza o índice com o ID gerado.
* **Read**: busca e exibe um registro pelo ID usando o índice direto.
* **Update**: altera campos de um registro existente, gerenciando corretamente as variações de tamanho.
* **Delete**: marca um registro como excluído no arquivo e remove sua entrada no índice.

## 🏗️ Estrutura de Classes

### Tarefa

Classe que representa o registro de tarefa e implementa a interface `Registro` para serialização em bytes.

* **Atributos**:

  * `int id`
  * `String nome`
  * `LocalDate dataCriacao`
  * `LocalDate dataConclusao`
  * `byte status` (0-Pendente, 1-Em Progresso, 2-Concluída)
  * `byte prioridade` (0-Baixa, 1-Média, 2-Alta)
* **Construtores**:

  * `Tarefa()`
  * `Tarefa(String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status, byte prioridade)`
  * `Tarefa(int id, String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status, byte prioridade)`
* **Métodos**:

  * `void setId(int)` / `int getId()`
  * `void setNome(String)` / `String getNome()`
  * `void setDataCriacao(LocalDate)` / `LocalDate getDataCriacao()`
  * `void setDataConclusao(LocalDate)` / `LocalDate getDataConclusao()`
  * `void setStatus(byte)` / `byte getStatus()`
  * `void setPrioridade(byte)` / `byte getPrioridade()`
  * `byte[] toByteArray()` / `static Tarefa fromByteArray(byte[] b)`
  * `String toString()`
  * `int compareTo(Tarefa o)`

### ControleTarefas

Classe responsável pelas operações CRUD e manutenção do índice hash.

* **Atributos**:

  * `HashDiretoIndice indice` — gerencia mapeamento ID → posição em arquivo.
  * `String arquivoDados` — caminho do arquivo de registros.
* **Métodos**:

  * `int criar(Tarefa t)`
  * `Tarefa ler(int id)`
  * `boolean atualizar(Tarefa t)`
  * `boolean deletar(int id)`
  * `List<Tarefa> listarTodos()`

## 🔍 Experiência

Durante o desenvolvimento encontramos um efeito de "lápide" ao excluir registros, mas ajustamos a reorganização para garantir que IDs válidos não fossem ocultados.

## 🚀 Como Executar

```bash
# Clone e acesse o TP1
git clone https://github.com/LuanCarrieiros/Algoritmo-e-Estrutura-de-Dados-III.git
cd Algoritmo-e-Estrutura-de-Dados-III/TP1

# Compile
autocmd mvn clean package  # ou gradle build

# Execute
autocmd java -jar target/tp1-crud.jar
```

## ✅ Resultado

* Nota obtida: **5/5**
* Implementação completa de CRUD com índice direto funcionando corretamente.

## ❓ Perguntas Frequentes

* **Índice direto implementado com hash extensível?** Sim
* **Inclusão retorna novo ID e atualiza índice?** Sim
* **Busca por ID eficiente via índice?** Sim
* **Alteração gerencia variações de tamanho de registro?** Sim
* **Exclusão marca e remove do índice?** Sim
* **Trabalho original e totalmente funcional?** Sim

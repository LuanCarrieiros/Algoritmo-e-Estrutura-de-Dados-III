# TP2: Relacionamento 1:N entre Tarefas e Categorias

## 📋 Descrição
Implementação em Java de relacionamento **1:N** entre **tarefas** e **categorias** usando **Árvores B+** para índice e um índice indireto baseado em nome de categoria.

## ⚙️ Funcionalidades
- Adiciona atributo `idCategoria` em cada `Tarefa`.  
- CRUD de `Categoria` com exclusão lógica.  
- Relacionamento 1:N: associa múltiplas tarefas a uma categoria via B+.  
- Índice indireto de categorias por nome (chave de busca).

## 🏗️ Estrutura de Classes

### Categoria  
- **Atributos**  
  - `int id`  
  - `String nome`  
  - `boolean excluido`  
- **Construtores**  
  - `Categoria()`  
  - `Categoria(int id, String nome)`  
- **Métodos**  
  - `int getId()` / `void setId(int)`  
  - `String getNome()` / `void setNome(String)`  
  - `String getChaveIndice()` — retorna `nome.toLowerCase()`  
  - `boolean isExcluido()` / `void setExcluido(boolean)`  
  - `byte[] toByteArray()` / `void fromByteArray(byte[] b)`  
  - `int compareTo(Categoria o)`  
  - `String toString()`

### ControleCategorias  
- **Atributos**  
  - `ArquivoCategorias arquivoCategorias`  
  - `ArquivoTarefas arquivoTarefas`  
- **Construtor**  
  - `ControleCategorias(ArquivoCategorias ac, ArquivoTarefas at)`  
- **Métodos**  
  - `boolean adicionarCategoria(Categoria c)`  
  - `boolean excluirCategoria(int idCategoria)`  
  - `void gerarRelatorioTarefasPorCategoria()`  
  - `List<Categoria> listarTodasCategorias()`

### ControleTarefas  
- **Atributos**  
  - `ArquivoTarefas arquivoTarefas`  
  - `ArquivoCategorias arquivoCategorias`  
- **Construtor**  
  - `ControleTarefas(ArquivoTarefas at, ArquivoCategorias ac)`  
- **Métodos**  
  - `boolean adicionarTarefa(Tarefa t)`  
  - `boolean excluirTarefa(int idTarefa)`  
  - `List<Tarefa> listarTodasTarefas()`  
  - `List<Categoria> listarTodasCategorias()`

### VisaoCategorias  
Interface de usuário para gerenciamento de categorias.  
- `void menu()`  
- `private void adicionarCategoria()`  
- `private void excluirCategoria()`  
- `private void listarCategorias()`  
- `private void gerarRelatorioTarefasPorCategoria()`

### VisaoTarefas  
Interface de usuário para gerenciamento de tarefas.  
- `void menu()`  
- `private void adicionarTarefa()`  
- `private void excluirTarefa()`  
- `private void listarTarefas()`

### ArquivoTarefas  
Armazena `Tarefa` em arquivo sequencial + B+ index para `idCategoria`.  
- **Atributos**: índice B+ para 1:N.  
- **Métodos**: `create`, `read`, `update`, `delete`, `buscarPorCategoria`, `listarTodas()`.

### ArquivoCategorias  
Armazena `Categoria` em arquivo sequencial + índice indireto por nome.  
- **Métodos**: `create`, `read`, `update`, `delete`, `buscarPorChave`, `listarTodas()`.

## 🚀 Como Executar

```bash
# Clone o repositório
git clone https://github.com/LuanCarrieiros/Algoritmo-e-Estrutura-de-Dados-III.git
cd Algoritmo-e-Estrutura-de-Dados-III/TP2


```

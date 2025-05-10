# AEDS III - TP02

## Alunos integrantes da equipe

* Diego Moreira Rocha
* Luan Barbosa Rosa Carrieiros
* Iago Fereguetti Ribeiro 

Neste trabalho prático foi:
  - Acrescentado o atributo de idCategoria na entidade tarefa.
  - Criado a entidade categoria.
  - Extendida a classe Arquivo para ter uma classe arquivo de tarefas, no qual foi implementado o relacionamento 1:n usando a arvore B+.
  - Extendida a classe Arquivo para ter uma classe arquivo categoria, usando de um id indireto com nome.

## Classes e Métodos criados

### Classe Categoria

A classe Categoria representa uma categoria com um identificador único e um nome. Ela implementa a interface Registro, que define métodos para serialização e desserialização de objetos.

* ## Atributos

- int id: Identificador único da categoria.
- String nome: Nome da categoria.
- boolean excluido: Campo para exclusão lógica, indicando se a categoria foi marcada para exclusão.

* ## Construtores

- Categoria(): Construtor padrão que inicializa o id como -1 e o nome como uma string vazia.
- Categoria(int id, String nome): Construtor que recebe um id e um nome como parâmetros para inicializar uma nova categoria.

* ## Métodos

- int getId() / void setId(int id): Métodos para acessar e modificar o identificador da categoria.
- String getNome() / void setNome(String nome): Métodos para acessar e modificar o nome da categoria.
- String getChaveIndice(): Retorna o nome da categoria em letras minúsculas, para ser utilizado como chave no índice indireto.
- boolean isExcluido() / void setExcluido(boolean excluido): Métodos para acessar e definir o valor do campo excluido, indicando se a categoria está marcada para exclusão.
- byte[] toByteArray() throws IOException: Serializa o objeto Categoria em um array de bytes, armazenando id e nome em um DataOutputStream.
- void fromByteArray(byte[] bytes) throws IOException: Desserializa o objeto Categoria a partir de um array de bytes, reconstruindo id e nome a partir de um DataInputStream.
- int compareTo(Object c): Compara o objeto atual com outro objeto do tipo Categoria, utilizando o id como critério.
- String toString(): Retorna uma representação em string da categoria, mostrando os valores de id e nome.


### Classe ControleCategorias

A classe ControleCategorias gerencia operações relacionadas às categorias e ao relacionamento entre categorias e tarefas.

* ## Atributos

- ArquivoCategorias arquivoCategorias: Responsável pelo armazenamento e manipulação das categorias.
- ArquivoTarefas arquivoTarefas: Responsável pelo armazenamento e manipulação das tarefas associadas a uma categoria.

* ## Construtor

- ControleCategorias(ArquivoCategorias arquivoCategorias, ArquivoTarefas arquivoTarefas): Inicializa o controle de categorias e tarefas, recebendo os arquivos de categorias e tarefas como parâmetros.

* ## Métodos

- boolean adicionarCategoria(Categoria categoria): Adiciona uma nova categoria, verificando se já existe uma categoria com o mesmo nome para evitar duplicatas. Retorna true se a categoria for adicionada com sucesso, e false caso já exista uma categoria com o mesmo nome.

- boolean excluirCategoria(int idCategoria): Exclui uma categoria se não houver tarefas associadas a ela. Retorna true se a categoria for excluída com sucesso, e false caso existam tarefas vinculadas.

- void gerarRelatorioTarefasPorCategoria(): Gera um relatório de tarefas organizadas por categoria, listando as tarefas vinculadas a cada categoria.

- ArrayList<Categoria> listarTodasCategorias(): Retorna uma lista de todas as categorias existentes.


### Classe ControleTarefas

A classe ControleTarefas gerencia operações relacionadas às tarefas e ao relacionamento entre tarefas e categorias.

#### Atributos

- ArquivoTarefas arquivoTarefas: Responsável pelo armazenamento e manipulação das tarefas.
- ArquivoCategorias arquivoCategorias: Responsável pelo armazenamento e manipulação das categorias.

#### Construtor

- ControleTarefas(ArquivoTarefas arquivoTarefas, ArquivoCategorias arquivoCategorias): Inicializa o controle de tarefas e categorias, recebendo os arquivos de tarefas e categorias como parâmetros.

#### Métodos

- boolean adicionarTarefa(Tarefa tarefa): Adiciona uma nova tarefa ao arquivo de tarefas. Retorna true assumindo que a criação é bem-sucedida.

- boolean excluirTarefa(int idTarefa): Exclui uma tarefa do arquivo de tarefas pelo seu identificador. Retorna true se a exclusão for bem-sucedida.

- ArrayList<Tarefa> listarTodasTarefas(): Retorna uma lista de todas as tarefas armazenadas.

- ArrayList<Categoria> listarTodasCategorias(): Retorna uma lista de todas as categorias existentes, útil para seleção na visão de tarefas.


### Classe VisaoCategorias

A classe VisaoCategorias é responsável pela interface de interação do usuário para gerenciar categorias. Ela permite que o usuário adicione, exclua e liste categorias, além de gerar relatórios de tarefas associadas.

#### Atributos

- ControleCategorias controleCategorias: Controlador responsável pela manipulação de categorias.
- ControleTarefas controleTarefas: Controlador responsável pela manipulação de tarefas (não utilizado diretamente na classe).
- Scanner scanner: Objeto para capturar a entrada do usuário.

#### Construtor

- VisaoCategorias(ControleCategorias controleCategorias, ControleTarefas controleTarefas): Inicializa a visão de categorias, recebendo os controladores de categorias e tarefas como parâmetros.

#### Métodos

- void menu(): Exibe o menu principal para gerenciar categorias, permitindo que o usuário escolha entre incluir, excluir, listar categorias ou gerar relatórios.

- private void adicionarCategoria(): Captura o nome de uma nova categoria e a adiciona utilizando o controlador de categorias.

- private void excluirCategoria(): Exclui uma categoria selecionada pelo usuário, verificando se há categorias disponíveis.

- private void listarCategorias(): Lista todas as categorias armazenadas, exibindo seus IDs e nomes.

- private void gerarRelatorioTarefasPorCategoria(): Gera um relatório de tarefas associadas a cada categoria utilizando o controlador de categorias.


### Classe VisaoTarefas

A classe VisaoTarefas é responsável pela interface de interação do usuário para gerenciar tarefas. Ela permite que o usuário adicione, exclua e liste tarefas, associando-as a categorias existentes.

#### Atributos

- ControleTarefas controleTarefas: Controlador responsável pela manipulação de tarefas.
- ControleCategorias controleCategorias: Controlador responsável pela manipulação de categorias.
- Scanner scanner: Objeto para capturar a entrada do usuário.

#### Construtor

- VisaoTarefas(ControleTarefas controleTarefas, ControleCategorias controleCategorias): Inicializa a visão de tarefas, recebendo os controladores de tarefas e categorias como parâmetros.

#### Métodos

- void menu(): Exibe o menu principal para gerenciar tarefas, permitindo que o usuário escolha entre incluir, excluir ou listar tarefas.

- private void adicionarTarefa(): Captura o nome de uma nova tarefa e a categoria associada, adicionando a tarefa utilizando o controlador de tarefas.

- private void excluirTarefa(): Exclui uma tarefa selecionada pelo usuário, verificando se há tarefas disponíveis.

- private void listarTarefas(): Lista todas as tarefas armazenadas, exibindo seus IDs e nomes.


### Classe Arquivo Tarefa

A classe tem como seu objetivo armazenar as tarefas 

* ## Atributos:

  - IndiceCategoriaTarefa: indece para a arvore

* ## Construtores:
   
  - ArquivoTarefas: Construtor que inicializa o arquivo de tarefas e o índice para o
    
* ## Métodos:

   - create: Método para criar uma nova tarefa no arquivo e no índice 1:N
   - read: Método para ler uma categoria pelo ID
   - updade: Método para atualizar uma categoria existente
   - delete: Método para excluir uma categoria pelo ID e atualizar o índice 1:N
   - buscarPorNome: Método para buscar uma categoria pelo nome
   - ListarTodasCategorias: Método para listar todas as categorias

### Classe Arquivo Categoria

  A classe tem como seu objetivo armazenar as categorias

* ## Atributos:

  - IndiceCategoriaTarefa: indece para a arvore

* ## Construtores:
   
  - ArquivoCategorias: Construtor que inicializa o arquivo de tarefas e o índice para o
  - Create: Método para criar uma nova tarefa no arquivo e no índice 1:N
    
* ## Métodos:

   - create: Método para criar uma nova tarefa no arquivo e no índice 1:N
   - read: Método para ler uma tarefa pelo ID
   - updade: Método para atualizar uma tarefa existente
   - delete: Método para excluir uma tarefa pelo ID e atualizar o índice 1:N
   - buscarPorCategoria: Método para buscar todas as tarefas por categoria
   - listarTodasCategorias: Método para listar todas as categorias - percorre sequencialmente
   - formatarNome: Método auxiliar para formatar o nome com a primeira letra maiúscula e o restante minúsculo

## Experiência

  Inicialmente tivemos dificuldade para juntar o que tinhamos com os novos 
  
## Perguntas

  - O CRUD (com índice direto) de categorias foi implementado? Sim
  - Há um índice indireto de nomes para as categorias? Sim
  - O atributo de ID de categoria, como chave estrangeira, foi criado na classe Tarefa? Sim
  - Há uma árvore B+ que registre o relacionamento 1:N entre tarefas e categorias? Sim
  - É possível listar as tarefas de uma categoria? Sim
  - A remoção de categorias checa se há alguma tarefa vinculada a ela? Sim
  - A inclusão da categoria em uma tarefa se limita às categorias existentes? Sim
  - O trabalho está funcionando corretamente? Sim
  - O trabalho está completo? Sim
  - O trabalho é original e não a cópia de um trabalho de outro grupo? Sim

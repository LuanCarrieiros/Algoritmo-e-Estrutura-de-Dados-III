# TP4: Backup Compactado

## 🧑‍💻 Autor

* Luan Barbosa Rosa Carrieiros

## 📋 Descrição

Implementação de um sistema de **backup compactado** em Java utilizando o algoritmo **LZW**. Arquivos de dados são lidos como vetores de bytes, codificados e armazenados em backups com timestamp. Inclui recuperação seletiva de versões e rotinas de manutenção de backups.

## ⚙️ Funcionalidades

* **compacta()**: lê arquivos da pasta `dados/`, aplica LZW e gera backup em `backups/backup_<timestamp>.lzw`
* **descompacta(String versao)**: decodifica o backup especificado e extrai arquivos na pasta `dados_recuperados/`
* Listagem de backups disponíveis e escolha de versão para restauração

## 🏗️ Estrutura de Classes

### Compactador

* `void compacta()`
* `void descompacta(String versao)`

### LZW

* `static final int BITS_POR_INDICE`
* `byte[] codifica(byte[] msgBytes)`
* `byte[] decodifica(byte[] msgCodificada)`

### VetorDeBits

* Atributo: `BitSet vetor`
* Construtores: `VetorDeBits()`, `VetorDeBits(int n)`, `VetorDeBits(byte[] v)`
* Principais métodos: `toByteArray()`, `set(int)`, `clear(int)`, `get(int)`, `length()`, `size()`, `toString()`

### VisaoBackups

* `void menu()`
* `void fazerBackup()`
* `void carregarBackup()`

## 📊 Taxa de Compactação

* Compressão alcançada: **51,91%**

## 🚀 Como Executar

```bash
# Clone o repositório
git clone https://github.com/LuanCarrieiros/Algoritmo-e-Estrutura-de-Dados-III.git
cd Algoritmo-e-Estrutura-de-Dados-III/TP4

```

## ✅ Resultado

* Nota obtida: **5/5**
* Trabalho concluído de forma independente, atendendo a todos os requisitos.

## ❓ Perguntas Frequentes

* **Rotina de compactação com LZW?** Sim
* **Rotina de descompactação para recuperação?** Sim
* **Usuário pode escolher versão de backup?** Sim
* **Trabalho original?** Sim

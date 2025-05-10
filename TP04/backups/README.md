# Pasta `backups`

Esta pasta armazena os arquivos de **backup compactado** gerados pelo TP4.

## Estrutura de Arquivos

* Os arquivos de backup têm o formato binário `.lzw` e são gerados com o padrão de nome:

  ```
  backup_<YYYY-MM-DD>.db
  ```

  Exemplo: `backup_2024-12-08.db`
* Se um backup para a mesma data já existir, ele é **sobrescrito** ao executar a operação novamente naquele dia.
* Em dias diferentes, novos arquivos de backup são criados automaticamente com a data correspondente.

## Como Restaurar

1. Abra a aplicação Java do TP4.
2. No menu principal, escolha **Carregar Backup**.
3. Selecione um dos arquivos listados nesta pasta para restaurar.
4. Os dados serão restaurados na pasta `dados/`, sobrescrevendo os arquivos existentes.

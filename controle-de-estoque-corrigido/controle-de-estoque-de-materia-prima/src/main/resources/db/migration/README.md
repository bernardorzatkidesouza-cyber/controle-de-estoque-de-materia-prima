# Migrations do banco

As migrations do Flyway ficam neste diretório e são executadas em ordem de versão na inicialização. `V1__criar_tabelas_estoque.sql` cria as tabelas de fornecedores, materiais e movimentações, incluindo chaves estrangeiras e restrições para quantidades e tipos válidos.

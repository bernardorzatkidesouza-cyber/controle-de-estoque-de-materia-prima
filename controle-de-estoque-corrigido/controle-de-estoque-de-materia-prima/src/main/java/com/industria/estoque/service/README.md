# Services

Os services implementam as operações e regras de negócio. `MaterialService` gerencia materiais, `FornecedorService` gerencia fornecedores e `MovimentacaoService` valida e registra entradas/saídas, atualizando o saldo do material. Exceções de recurso inexistente e de regra de negócio são tratadas pelo `ApiExceptionHandler`.

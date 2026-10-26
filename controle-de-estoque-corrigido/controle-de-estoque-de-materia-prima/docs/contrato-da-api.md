# Contrato da API

Este documento descreve as rotas declaradas nos controllers do projeto. A confirmação de execução deve ser feita com a aplicação iniciada pelo Swagger UI.

## Convenções

| Item | Valor |
|---|---|
| URL base local | `http://localhost:8080` |
| Formato | JSON |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| Erros | Estrutura `ErroDTO` definida pelo tratador global |

## Materiais

| Método | Rota | Finalidade | Sucesso esperado | Erros principais |
|---|---|---|---|---|
| `POST` | `/materiais` | Cadastrar material | `200` conforme retorno atual do controller | `400` validação |
| `GET` | `/materiais` | Listar materiais | `200` | — |
| `GET` | `/materiais/{id}` | Buscar por ID | `200` | `404` ID inexistente |
| `PUT` | `/materiais/{id}` | Atualizar material | `200` | `400`, `404` |
| `GET` | `/materiais/estoque-baixo` | Listar materiais com quantidade menor que o estoque mínimo | `200` | — |

### Exemplo de entrada — `POST /materiais`

```json
{
  "nome": "Resina plástica",
  "quantidade": 100.00,
  "estoqueMinimo": 20.00
}
```

A resposta de material contém `id`, `nome`, `quantidade` e `estoqueMinimo`. Os valores numéricos são representados como números JSON.

## Fornecedores

| Método | Rota | Finalidade | Sucesso esperado | Erros principais |
|---|---|---|---|---|
| `POST` | `/fornecedores` | Cadastrar fornecedor | `200` conforme retorno atual do controller | `400` validação |
| `GET` | `/fornecedores` | Listar fornecedores | `200` | — |
| `GET` | `/fornecedores/{id}` | Buscar por ID | `200` | `404` ID inexistente |
| `PUT` | `/fornecedores/{id}` | Atualizar fornecedor | `200` | `400`, `404` |

### Exemplo de entrada — `POST /fornecedores`

```json
{
  "nome": "Distribuidora Industrial Exemplo",
  "contato": "contato@exemplo.com"
}
```

A resposta contém `id`, `nome` e `contato`. O contato é opcional.

## Movimentações

| Método | Rota | Finalidade | Sucesso esperado | Erros principais |
|---|---|---|---|---|
| `POST` | `/movimentacoes` | Registrar entrada ou saída e atualizar o saldo do material | `200` conforme retorno atual do controller | `400` regra/validação; `404` material ou fornecedor inexistente |
| `GET` | `/movimentacoes` | Listar movimentações | `200` | — |

### Exemplo de entrada — `POST /movimentacoes`

```json
{
  "materialId": 1,
  "fornecedorId": 1,
  "tipo": "entrada",
  "quantidade": 25.00
}
```

`fornecedorId` pode ser omitido ou enviado como `null`. `tipo` aceita `entrada` ou `saida` (sem diferenciar maiúsculas e minúsculas na camada de serviço). A resposta contém identificadores e nomes do material e, quando informado, do fornecedor, além do tipo, quantidade e data.

### Exemplo de saída

```json
{
  "materialId": 1,
  "fornecedorId": null,
  "tipo": "saida",
  "quantidade": 5.00
}
```

## DTOs e validações

| DTO | Campos | Validações observadas no código |
|---|---|---|
| `MaterialEntradaDTO` | `nome`, `quantidade`, `estoqueMinimo` | Nome não vazio; quantidade e estoque mínimo obrigatórios e não negativos |
| `MaterialRespostaDTO` | `id`, `nome`, `quantidade`, `estoqueMinimo` | Resposta da API |
| `FornecedorEntradaDTO` | `nome`, `contato` | Nome não vazio; contato opcional |
| `FornecedorRespostaDTO` | `id`, `nome`, `contato` | Resposta da API |
| `MovimentacaoEntradaDTO` | `materialId`, `fornecedorId`, `tipo`, `quantidade` | Material obrigatório; IDs positivos quando informados; tipo entrada/saída; quantidade maior que zero |
| `MovimentacaoRespostaDTO` | `id`, `materialId`, `materialNome`, `fornecedorId`, `fornecedorNome`, `tipo`, `quantidade`, `data` | Resposta da API |

## Regras de negócio

1. Quantidade de movimentação precisa ser maior que zero.
2. O tipo da movimentação precisa ser `entrada` ou `saida`.
3. A saída não pode ultrapassar o saldo disponível; caso contrário, a API lança uma exceção de regra de negócio.
4. Entrada soma a quantidade ao saldo; saída subtrai.
5. A movimentação precisa referenciar um material existente. Se um fornecedor for informado, ele também precisa existir.
6. Estoque baixo significa `quantidade < estoqueMinimo` (não inclui saldo exatamente igual ao mínimo).

## Respostas de erro

O `ApiExceptionHandler` centraliza os erros conhecidos e devolve um objeto `ErroDTO`, que contém status, descrição do erro, mensagem, caminho, timestamp e, para erros de validação, detalhes dos campos.

| HTTP | Situação |
|---|---|
| `400 Bad Request` | Validação de DTO, corpo JSON ilegível ou regra de negócio violada |
| `404 Not Found` | Material ou fornecedor solicitado não existe |

**Nota de implementação:** os controllers atuais retornam diretamente DTOs, sem configurar explicitamente `201 Created`; por isso, os endpoints `POST` respondem `200 OK` na configuração atual. Se a disciplina exigir `201`, isso precisa ser alterado no código e depois refletido neste documento.

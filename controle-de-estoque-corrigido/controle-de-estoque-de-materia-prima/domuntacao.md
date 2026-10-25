# Controle de Estoque de Matéria-Prima

API REST para controlar o estoque de matérias-primas utilizadas em indústrias, permitindo cadastrar materiais e fornecedores, registrar entradas e saídas, acompanhar quantidades disponíveis e identificar materiais abaixo do estoque mínimo.

Projeto Final da disciplina de Backend — Engenharia de Software, SATC. **Etapa 1: arquitetura do backend.**

## Índice

- [Integrantes](#integrantes)
- [O problema](#o-problema)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Entidades](#entidades)
- [Rotas](#rotas)
- [Como executar](#como-executar)
- [Situação do projeto](#situação-do-projeto)
- [Documentação](#documentação)

## Integrantes

| Nome | GitHub |
|---|---|
| Bernardo Rzatki da Silva de Souza | [@bernardorzatkidesouza-cyber](https://github.com/bernardorzatkidesouza-cyber) |
| Miguel Machado Gomes | [@gomesisworking](https://github.com/gomesisworking) |
| Jenifer da Silva Vieira | [@JeniferSilvaVieira](https://github.com/JeniferSilvaVieira) |

## O problema

**Tema sorteado:** Indústria e manufatura.

Muitas indústrias enfrentam dificuldades para controlar o estoque de matéria-prima, causando falta de materiais durante a produção ou acúmulo desnecessário de produtos. A ausência de um controle adequado dificulta o acompanhamento das entradas e saídas dos materiais. Dessa forma, a API de controle de estoque permite registrar movimentações, consultar quantidades disponíveis e identificar quando os materiais estão abaixo do estoque mínimo.

### Quem usa o sistema

| Quem | O que faz no sistema |
|---|---|
| Administrador | Cadastra e atualiza matérias-primas e fornecedores. |
| Funcionário do estoque | Registra as entradas e saídas de matérias-primas. |
| Gerente de produção | Consulta quantidades disponíveis e verifica materiais abaixo do estoque mínimo. |

### Regras de negócio

| # | Regra | Onde está no código | Rota que usa |
|---|---|---|---|
| RN01 — Controle de saída | O sistema não permite retirar uma quantidade de matéria-prima maior do que a disponível no estoque. | `MovimentacaoService.java` | `POST /movimentacoes` |
| RN02 — Estoque mínimo | O sistema identifica os materiais cuja quantidade disponível está abaixo do estoque mínimo definido. | `MaterialRepository.java` | `GET /materiais/estoque-baixo` |
| RN03 — Registro de movimentações | Toda entrada ou saída registrada deve atualizar a quantidade disponível e gerar um registro da movimentação. | `MovimentacaoService.java` | `POST /movimentacoes` |

### Fora do escopo

O projeto está limitado aos requisitos definidos para a disciplina de Backend. Até o momento, o foco está no desenvolvimento da API REST, na persistência dos dados, nas validações, nas regras de negócio e na documentação dos endpoints.

Funcionalidades como interface gráfica, autenticação e controle de permissões de usuários não fazem parte do escopo implementado nesta etapa.

## Tecnologias

| O quê | Qual |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 3.5 |
| API REST | Spring Web |
| Validação | Jakarta Bean Validation |
| Persistência | Spring Data JPA |
| Banco de dados | PostgreSQL 16 |
| Ambiente do banco | Docker Compose |
| Migrations | Flyway |
| Documentação da API | Springdoc OpenAPI / Swagger UI |
| Gerenciamento de dependências | Maven |
| Controle de versão | Git e GitHub |

## Arquitetura

A API é organizada em camadas para separar as responsabilidades:

- **Controller:** recebe as requisições HTTP, valida os dados de entrada e encaminha as operações.
- **Service:** concentra as regras de negócio e coordena as operações.
- **Repository:** acessa o banco de dados por meio do Spring Data JPA.
- **Model:** representa as entidades persistidas no banco.
- **DTO:** define os dados recebidos e devolvidos pela API.
- **Mapper:** converte DTOs em entidades e entidades em DTOs.
- **Exception Handler:** centraliza o tratamento de erros e padroniza as respostas.

### Mapa da estrutura

A tabela abaixo representa a estrutura verificada no código disponibilizado. Os serviços de materiais e fornecedores e os endpoints adicionais devem ser marcados como implementados após sua integração e verificação no projeto.

| Entidade | Migration | Model | Repository | DTOs | Mapper | Service | Controller |
|---|---|---|---|---|---|---|---|
| Material | Pronta: `V1__criar_tabelas_estoque.sql` | `Material`, implementada | `MaterialRepository`, implementado | `MaterialEntradaDTO` e `MaterialRespostaDTO`, implementados | `MaterialMapper`, implementado | `MaterialService`, em desenvolvimento | `MaterialController`, implementado parcialmente |
| Fornecedor | Pronta: `V1__criar_tabelas_estoque.sql` | `Fornecedor`, implementada | `FornecedorRepository`, implementado | `FornecedorEntradaDTO` e `FornecedorRespostaDTO`, implementados | `FornecedorMapper`, implementado | `FornecedorService`, em desenvolvimento | `FornecedorController`, implementado parcialmente |
| Movimentação | Pronta: `V1__criar_tabelas_estoque.sql` | `Movimentacao`, implementada | `MovimentacaoRepository`, implementado | `MovimentacaoEntradaDTO` e `MovimentacaoRespostaDTO`, implementados | `MovimentacaoMapper`, criado | `MovimentacaoService`, implementado | `MovimentacaoController`, implementado parcialmente |

## Entidades

| Entidade | O que representa | Como se relaciona |
|---|---|---|
| Material | Uma matéria-prima armazenada no estoque, com quantidade disponível e estoque mínimo. | Pode aparecer em várias movimentações. |
| Fornecedor | Empresa ou pessoa que fornece matérias-primas. | Pode estar associado a várias movimentações. |
| Movimentação | Registro de entrada ou saída de uma matéria-prima. | Está associada a um material e pode estar associada a um fornecedor. |

Os atributos, tipos, relacionamentos e o diagrama das tabelas estão documentados em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## Rotas

As rotas abaixo refletem o código disponibilizado no projeto. A situação deve ser atualizada conforme os novos endpoints forem integrados e testados.

### As que já estão implementadas no código

| Verbo | Caminho | O que faz | Regra | Sucesso esperado | Erros previstos |
|---|---|---|---|---|---|
| `GET` | `/materiais` | Lista os materiais cadastrados. | — | `200` | — |
| `POST` | `/materiais` | Cadastra um material. | Validação dos dados de entrada. | `200` no código atual | `400` |
| `GET` | `/materiais/estoque-baixo` | Lista materiais abaixo do estoque mínimo. | RN02 | `200` | — |
| `PUT` | `/materiais/{id}` | Atualiza um material existente. | O material precisa existir. | `200` | `400`, `404` |
| `GET` | `/fornecedores` | Lista os fornecedores cadastrados. | — | `200` | — |
| `POST` | `/fornecedores` | Cadastra um fornecedor. | Validação dos dados de entrada. | `200` no código atual | `400` |
| `GET` | `/movimentacoes` | Lista as movimentações registradas. | — | `200` | — |
| `POST` | `/movimentacoes` | Registra uma entrada ou saída e atualiza o estoque. | RN01 e RN03 | `200` no código atual | `400`, `404` |

> Observação: os controllers atuais não definem explicitamente `201 Created` nos cadastros e no registro de movimentações. Como retornam objetos sem configurar o status HTTP, o Spring responde `200 OK`. O status poderá ser ajustado posteriormente com `@ResponseStatus(HttpStatus.CREATED)` ou `ResponseEntity`.

### Rotas planejadas

| Verbo | Caminho | O que fará |
|---|---|---|
| `GET` | `/materiais/{id}` | Consulta um material específico pelo ID. |
| `GET` | `/fornecedores/{id}` | Consulta um fornecedor específico pelo ID. |
| `PUT` | `/fornecedores/{id}` | Atualiza os dados de um fornecedor existente. |
| `DELETE` | `/materiais/{id}` | Exclui um material, respeitando as regras de integridade do histórico. |
| `DELETE` | `/fornecedores/{id}` | Exclui um fornecedor, respeitando as regras de integridade do histórico. |
| `GET` | `/movimentacoes?materialId={id}` | Filtra movimentações por material. |
| `GET` | `/movimentacoes?dataInicio={data}&dataFim={data}` | Filtra movimentações por período. |

As rotas planejadas só devem ser marcadas como funcionais após serem implementadas, integradas e testadas.

### Exemplo de requisição

Cadastrar um material:

```http
POST /materiais
Content-Type: application/json

{
  "nome": "Aço carbono",
  "quantidade": 100.00,
  "estoqueMinimo": 20.00
}
```

Exemplo de resposta com status `200 OK`, conforme o controller atual:

```json
{
  "id": 1,
  "nome": "Aço carbono",
  "quantidade": 100.00,
  "estoqueMinimo": 20.00
}
```

Registrar uma saída de matéria-prima:

```http
POST /movimentacoes
Content-Type: application/json

{
  "materialId": 1,
  "fornecedorId": null,
  "tipo": "saida",
  "quantidade": 10.00
}
```

O sistema verifica se há estoque suficiente, registra a movimentação e atualiza a quantidade disponível.

### Erros

Os erros são padronizados pelo `ApiExceptionHandler` e devolvidos no formato `ErroDTO`.

Exemplo de resposta `404 Not Found`:

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Material não encontrado: 999",
  "caminho": "/materiais/999",
  "timestamp": "2026-10-25T12:30:00",
  "campos": []
}
```

O horário é ilustrativo.

Um campo inválido gera uma resposta `400 Bad Request`, com os detalhes na lista `campos`.

| Status | Quando acontece |
|---|---|
| `
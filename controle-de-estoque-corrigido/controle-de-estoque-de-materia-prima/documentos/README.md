# Controle de Estoque de Matéria-Prima

API REST desenvolvida para a disciplina de Backend do curso de Engenharia de Software da SATC. O sistema permite cadastrar matérias-primas e fornecedores, registrar entradas e saídas de estoque, consultar materiais abaixo do estoque mínimo e calcular o valor do estoque em reais a partir da cotação atual do dólar.

## Checklist da documentação

- [x] Descrição do projeto e integrantes do grupo.
- [x] Descrição do problema e aplicação prática.
- [x] Tecnologias utilizadas.
- [x] Limitações conhecidas do projeto.
- [x] Descrição de cada entidade.
- [x] Descrição de cada rota.
- [x] Exemplos de requisição e resposta.
- [x] Exemplos de erros HTTP.
- [x] Instruções para executar o projeto localmente via Docker Compose.
- [x] Identificação da carta-desafio sorteada e descrição da implementação.

---

## 1. Descrição do projeto

O projeto Controle de Estoque de Matéria-Prima consiste no desenvolvimento de uma API REST para auxiliar no gerenciamento de matérias-primas utilizadas em processos industriais.

A aplicação permite cadastrar materiais e fornecedores, registrar movimentações de entrada e saída, consultar quantidades disponíveis, identificar materiais que estejam abaixo do estoque mínimo estabelecido e consultar o valor do estoque de um material convertido de dólar para real.

O sistema foi desenvolvido utilizando Java e Spring Boot, com persistência de dados em PostgreSQL e integração com a API pública de câmbio Frankfurter por meio da carta-desafio C1 — Elo Externo.

### Integrantes

| Integrante | GitHub |
|---|---|
| Bernardo Rzatki da Silva de Souza | [@bernardorzatkidesouza-cyber](https://github.com/bernardorzatkidesouza-cyber) |
| Miguel Machado Gomes | [@gomesisworking](https://github.com/gomesisworking) |
| Jenifer da Silva Vieira | [@JeniferSilvaVieira](https://github.com/JeniferSilvaVieira) |

## 2. Problema e aplicação prática

O domínio escolhido para o projeto é o controle de estoque de matérias-primas no setor industrial.

A falta de controle adequado sobre entradas e saídas de materiais pode ocasionar problemas como falta de insumos durante a produção, compras desnecessárias, divergências nas quantidades disponíveis e dificuldades no acompanhamento do estoque. Além disso, muitas matérias-primas são cotadas em dólar, o que dificulta saber quanto o estoque vale em reais em determinado dia.

A aplicação busca solucionar esses problemas por meio de um sistema centralizado que permite registrar e consultar as movimentações de materiais e acompanhar o valor do estoque com base na cotação atual.

O público-alvo são funcionários responsáveis pelo controle de estoque, profissionais que realizam o recebimento e a retirada de matérias-primas e gestores que precisam acompanhar a disponibilidade e o valor dos insumos.

Na prática, uma indústria pode utilizar o sistema para registrar o recebimento de materiais de fornecedores, controlar sua utilização na produção, identificar antecipadamente quando determinado material precisa ser reposto e consultar quanto o estoque de um material vale em reais.

## 3. Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 25 | Linguagem principal de desenvolvimento. |
| Spring Boot 3.5.16 | Estrutura principal da aplicação. |
| Spring Web | Desenvolvimento dos endpoints REST. |
| Spring Data JPA | Persistência e acesso aos dados. |
| Hibernate | Mapeamento objeto-relacional. |
| PostgreSQL 16 | Banco de dados relacional. |
| Docker | Containerização do banco de dados. |
| Docker Compose | Gerenciamento do container do PostgreSQL. |
| Flyway | Versionamento e gerenciamento das migrations do banco. |
| Bean Validation | Validação dos dados recebidos pela API. |
| Springdoc OpenAPI 2.9.1 / Swagger UI | Documentação interativa dos endpoints. |
| Maven Wrapper | Gerenciamento de dependências e execução do projeto. |
| RestClient | Consumo da API externa de câmbio, conforme a carta-desafio C1. |
| Frankfurter API | API pública usada para obter a cotação USD → BRL. |

## 4. Arquitetura e organização do código

O projeto utiliza uma arquitetura organizada em camadas, separando as responsabilidades de cada parte da aplicação.

| Camada | Responsabilidade |
|---|---|
| Controller | Recebe as requisições HTTP e disponibiliza as rotas da API. |
| Service | Implementa as regras de negócio e coordena as operações (inclui o `CotacaoService`, que consome a API externa). |
| Repository | Realiza consultas e operações de persistência com Spring Data JPA. |
| Model | Representa as entidades persistidas no banco de dados. |
| DTO | Define os dados recebidos e retornados pela API. |
| Mapper | Converte objetos DTO em entidades e vice-versa. |
| Config | Armazena configurações da aplicação, do `RestClient` e da documentação OpenAPI. |

Estrutura principal:

```text
src/main/java/com/industria/estoque/
├── config/          (OpenApiConfig, RestClientConfig)
├── controller/      (Material, Fornecedor, Movimentacao, ApiExceptionHandler)
├── dto/
├── mapper/
├── model/
├── repository/
└── service/         (Material, Fornecedor, Movimentacao, Cotacao e exceções)

src/main/resources/
├── application.properties
└── db/migration/    (V1 e V2)
```

## 5. Entidades e relacionamentos

O sistema possui três entidades principais.

### 5.1. Material

Representa as matérias-primas armazenadas e controladas pela indústria.

Principais atributos:

- ID: identificador único do material.
- Nome: nome da matéria-prima.
- Quantidade: quantidade atualmente disponível no estoque.
- Estoque mínimo: quantidade mínima estabelecida para o material.
- Preço unitário em dólar (`precoUnitarioDolar`): preço de uma unidade do material em US$, usado na conversão para reais.

A entidade permite acompanhar a disponibilidade dos materiais, identificar aqueles que precisam de reposição e calcular o valor do estoque em reais.

### 5.2. Fornecedor

Representa as empresas ou pessoas responsáveis pelo fornecimento das matérias-primas.

Principais atributos:

- ID: identificador único do fornecedor.
- Nome: nome do fornecedor.
- Contato: informação de contato, quando disponível.

Um fornecedor pode estar associado a várias movimentações de estoque.

### 5.3. Movimentação

Representa as operações de entrada e saída de matérias-primas no estoque.

Principais atributos:

- ID: identificador único da movimentação.
- Material: material relacionado à movimentação.
- Fornecedor: fornecedor relacionado, quando informado.
- Tipo: identifica se a movimentação é uma entrada ou saída.
- Quantidade: quantidade movimentada.
- Data e hora: momento em que a movimentação foi registrada.

### 5.4. Relacionamentos

- Um material pode possuir várias movimentações.
- Cada movimentação deve estar vinculada a um material.
- Um fornecedor pode estar associado a várias movimentações.
- O vínculo entre fornecedor e movimentação é opcional.

### 5.5. Banco de dados e migrations

O schema é criado e versionado pelo Flyway (o Hibernate apenas valida, com `ddl-auto=validate`):

| Migration | Descrição |
|---|---|
| `V1__criar_tabelas_estoque.sql` | Cria as tabelas `fornecedores`, `materiais` e `movimentacoes`, com restrições `CHECK` (quantidades não negativas, tipo `entrada`/`saida` e quantidade da movimentação maior que zero). |
| `V2__adicionar_preco_material.sql` | Adiciona a coluna `preco_unitario_dolar` à tabela `materiais` (não negativa, padrão `0.00`). |

O detalhamento das entidades e dos relacionamentos está disponível em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## 6. Regras de negócio

O sistema implementa as seguintes regras:

1. A quantidade de uma movimentação deve ser maior que zero.
2. Uma saída não pode ser maior que a quantidade disponível no estoque (**RN01**).
3. Uma entrada aumenta o saldo do material.
4. Uma saída diminui o saldo do material.
5. Toda movimentação é registrada no banco de dados e atualiza o saldo do material na mesma transação (**RN03**).
6. O tipo da movimentação deve ser `entrada` ou `saida` (a comparação ignora maiúsculas e minúsculas).
7. A consulta de estoque baixo retorna materiais cuja quantidade atual é **inferior** ao estoque mínimo cadastrado (**RN02**).
8. O material referenciado em uma movimentação precisa existir.
9. O fornecedor, quando informado, também precisa existir.
10. Quantidade, estoque mínimo e preço unitário em dólar de um material não podem ser negativos.

## 7. Endpoints da API

A URL base da aplicação é:

```text
http://localhost:8080
```

> **Observação:** as rotas de cadastro (`POST`) retornam **200 OK** com o objeto criado, conforme o retorno atual dos controllers.

### 7.1. Materiais

| Método | Rota | Descrição |
|---|---|---|
| POST | `/materiais` | Cadastrar material. |
| GET | `/materiais` | Listar materiais. |
| GET | `/materiais/{id}` | Consultar material por ID. |
| PUT | `/materiais/{id}` | Atualizar material. |
| GET | `/materiais/estoque-baixo` | Consultar materiais abaixo do estoque mínimo. |
| GET | `/materiais/{id}/cotacao` | Consultar o valor do estoque do material em dólar e em reais (integração externa, carta C1). |

**Exemplo de cadastro de material**

Requisição:

```http
POST /materiais
Content-Type: application/json
```

```json
{
  "nome": "Aço",
  "quantidade": 100.00,
  "estoqueMinimo": 20.00,
  "precoUnitarioDolar": 10.00
}
```

Todos os campos são obrigatórios. Exemplo de resposta:

```json
{
  "id": 1,
  "nome": "Aço",
  "quantidade": 100.00,
  "estoqueMinimo": 20.00,
  "precoUnitarioDolar": 10.00
}
```

**Exemplo de consulta por ID**

```http
GET /materiais/1
```

Resposta: mesmo formato do cadastro.

**Exemplo de consulta de cotação**

```http
GET /materiais/1/cotacao
```

Veja a resposta completa na [seção 11.3](#113-exemplo-de-resposta).

### 7.2. Fornecedores

| Método | Rota | Descrição |
|---|---|---|
| POST | `/fornecedores` | Cadastrar fornecedor. |
| GET | `/fornecedores` | Listar fornecedores. |
| GET | `/fornecedores/{id}` | Consultar fornecedor por ID. |
| PUT | `/fornecedores/{id}` | Atualizar fornecedor. |

**Exemplo de cadastro de fornecedor**

Requisição:

```http
POST /fornecedores
Content-Type: application/json
```

```json
{
  "nome": "Fornecedor Industrial",
  "contato": "contato@fornecedor.com"
}
```

O campo `nome` é obrigatório; `contato` é opcional. Exemplo de resposta:

```json
{
  "id": 1,
  "nome": "Fornecedor Industrial",
  "contato": "contato@fornecedor.com"
}
```

### 7.3. Movimentações

| Método | Rota | Descrição |
|---|---|---|
| POST | `/movimentacoes` | Registrar entrada ou saída. |
| GET | `/movimentacoes` | Listar movimentações. |

**Exemplo de registro de entrada**

Requisição:

```http
POST /movimentacoes
Content-Type: application/json
```

```json
{
  "materialId": 1,
  "fornecedorId": 1,
  "tipo": "entrada",
  "quantidade": 50.00
}
```

A operação acrescenta 50 unidades ao estoque do material informado. Exemplo de resposta:

```json
{
  "id": 1,
  "materialId": 1,
  "materialNome": "Aço",
  "fornecedorId": 1,
  "fornecedorNome": "Fornecedor Industrial",
  "tipo": "entrada",
  "quantidade": 50.00,
  "data": "2026-11-24T10:30:00.123456"
}
```

**Exemplo de registro de saída**

```json
{
  "materialId": 1,
  "tipo": "saida",
  "quantidade": 10.00
}
```

A operação reduz 10 unidades do estoque, desde que exista saldo disponível. O `fornecedorId` é opcional (os campos `fornecedorId` e `fornecedorNome` retornam `null` quando não informado).

## 8. Tratamento de erros HTTP

A aplicação utiliza tratamento global de exceções (`ApiExceptionHandler`) para retornar todas as respostas de erro 400 e 404 no mesmo formato, definido pelo `ErroDTO`:

| Campo | Descrição |
|---|---|
| `status` | Código HTTP, repetido no corpo. |
| `erro` | Nome padrão do status (`Bad Request`, `Not Found`). |
| `mensagem` | Descrição do problema, em português. |
| `caminho` | URL chamada. |
| `timestamp` | Momento do erro (horário do servidor). |
| `campos` | Lista de `{campo, mensagem}` com os erros de validação (vazia quando não se aplica). |

Os principais códigos HTTP relacionados à aplicação são:

| Código | Descrição | Situação |
|---|---|---|
| 400 Bad Request | Requisição inválida. | Falha de validação dos campos, JSON ilegível ou violação de regra de negócio. |
| 404 Not Found | Recurso não encontrado. | Material ou fornecedor inexistente. |
| 500 Internal Server Error | Erro interno do servidor. | Falha inesperada durante o processamento (resposta padrão do Spring). |

### Exemplo de erro 400 — Regra de negócio

Ocorre ao registrar uma saída maior que o saldo disponível (`POST /movimentacoes`):

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "RN01: estoque insuficiente. Disponível: 23.00, solicitado: 100.00",
  "caminho": "/movimentacoes",
  "timestamp": "2026-11-24T10:30:00",
  "campos": []
}
```

### Exemplo de erro 400 — Validação de campos

Ocorre quando o corpo da requisição não passa na validação (`POST /materiais` com `nome` vazio e `quantidade` negativa):

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "Dados inválidos: confira a lista de campos",
  "caminho": "/materiais",
  "timestamp": "2026-11-24T10:30:00",
  "campos": [
    { "campo": "nome", "mensagem": "O nome do material é obrigatório." },
    { "campo": "quantidade", "mensagem": "A quantidade não pode ser negativa." }
  ]
}
```

### Exemplo de erro 404 — Not Found

Ocorre quando é realizada uma consulta de um material inexistente (`GET /materiais/999`):

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Material não encontrado: 999",
  "caminho": "/materiais/999",
  "timestamp": "2026-11-24T10:30:00",
  "campos": []
}
```

Um JSON malformado também retorna 400, com a mensagem "Corpo da requisição inválido: confira o JSON e o formato das datas (yyyy-MM-dd)".

## 9. Como executar o projeto localmente

### 9.1. Requisitos

Para executar a aplicação, é necessário possuir:

- JDK 25 instalado e configurado no PATH.
- Docker Desktop com Docker Compose, ou uma instância PostgreSQL configurada com os mesmos dados de conexão definidos em `src/main/resources/application.properties`.
- Git (para clonar o repositório).
- Acesso à internet, para que a rota de cotação consiga consultar a API Frankfurter (sem internet a rota continua funcionando, mas usa o fallback descrito na seção 11.4).

### 9.2. Clonar o repositório

```bash
git clone https://github.com/bernardorzatkidesouza-cyber/controle-de-estoque-de-materia-prima.git
```

Entre na pasta do projeto (a mesma que contém `pom.xml`, `docker-compose.yml` e `mvnw`):

```bash
cd controle-de-estoque-de-materia-prima/controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima
```

### 9.3. Iniciar o banco de dados

Na pasta do projeto, execute:

```bash
docker compose up -d postgres
```

Aguarde o PostgreSQL ficar saudável antes de iniciar a aplicação. As migrations do Flyway são executadas automaticamente na inicialização da API.

Configurações locais:

| Configuração | Valor |
|---|---|
| Banco de dados | PostgreSQL 16 |
| Porta | 5437 |
| Nome do banco | projeto |
| Usuário | projeto |
| Senha | projeto |

Essas credenciais são destinadas exclusivamente ao ambiente de desenvolvimento.

### 9.4. Executar a aplicação

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

### 9.5. Acessar o Swagger

Após a inicialização da aplicação, acesse:

- Swagger UI: http://localhost:8080/swagger-ui.html
- Contrato OpenAPI (JSON): http://localhost:8080/v3/api-docs

A interface Swagger permite visualizar os endpoints disponíveis e realizar requisições diretamente pelo navegador.

### 9.6. Encerrar a aplicação

Para encerrar os containers:

```bash
docker compose down
```

Para apagar também os dados persistidos no banco:

```bash
docker compose down -v
```

**Atenção:** o segundo comando remove os dados locais armazenados no volume do banco de dados.

## 10. Limitações conhecidas e melhorias futuras

Apesar de implementar as funcionalidades principais de controle de estoque, o projeto possui algumas limitações que podem ser trabalhadas em versões futuras:

- Não há testes automatizados para serviços, controllers e regras de negócio.
- Não há autenticação nem autorização.
- Não há paginação nem filtros nas listagens (`GET /materiais`, `GET /fornecedores`, `GET /movimentacoes`).
- As movimentações são transacionais (`@Transactional`), mas não há bloqueio otimista ou pessimista; movimentações simultâneas no mesmo material podem gerar condição de corrida.
- A rota `PUT /materiais/{id}` sobrescreve a quantidade diretamente, sem gerar uma movimentação no histórico.
- Não há rotas de exclusão (`DELETE`) para materiais, fornecedores e movimentações.
- As rotas de cadastro retornam 200 em vez de 201 Created.
- A cotação é consultada na API externa a cada requisição, sem cache.
- As credenciais do banco estão fixas no `application.properties`; para ambientes externos ao desenvolvimento local, devem ser movidas para variáveis de ambiente.
- Não há relatórios gerenciais além da consulta de estoque baixo e da cotação por material.

## 11. Carta-desafio sorteada — C1: Elo Externo

**Origem:** Aula 12 — Integração de Serviços.

**Desafio:** consumir um serviço externo real para agregar valor à aplicação, sem reconstruir funcionalidades que já existem em outros serviços.

**Requisito:** consumir uma API pública real por meio de `RestClient`, `RestTemplate` ou `WebClient`.

### 11.1. Implementação

A carta C1 — Elo Externo foi implementada na rota `GET /materiais/{id}/cotacao`, que converte o valor do estoque de um material de dólar para real usando a API pública [Frankfurter](https://frankfurter.dev), que fornece taxas de câmbio.

| Item | Valor |
|---|---|
| API externa | Frankfurter (`https://api.frankfurter.dev`) |
| Requisição | `GET /v1/latest?base=USD&symbols=BRL` |
| Cliente HTTP | `RestClient` (configurado em `RestClientConfig`) |
| Classe de serviço | `CotacaoService` |
| Timeouts | 3 segundos de conexão e 3 segundos de leitura |

### 11.2. Funcionamento

Ao consultar `GET /materiais/{id}/cotacao`, a aplicação:

1. Busca os dados do material no PostgreSQL (retorna 404 se não existir).
2. Consulta a API Frankfurter para obter a cotação atual do dólar em reais.
3. Valida a resposta (cotação presente e maior que zero).
4. Calcula o valor total do estoque em dólar e em reais.
5. Retorna os dados do material juntamente com a cotação utilizada, a data da cotação e a fonte.

Os cálculos utilizados são:

```text
valorTotalDolar = quantidade * precoUnitarioDolar
valorTotalReais = valorTotalDolar * cotacaoDolar   (arredondado para 2 casas, HALF_UP)
```

### 11.3. Exemplo de resposta

Para um material com 100 unidades em estoque, preço unitário de US$ 10,00 e uma cotação hipotética de R$ 5,50:

```json
{
  "id": 1,
  "nome": "Aço",
  "quantidade": 100.00,
  "precoUnitarioDolar": 10.00,
  "valorTotalDolar": 1000.0000,
  "cotacaoDolar": 5.50,
  "valorTotalReais": 5500.00,
  "dataCotacao": "2026-11-24",
  "cotacaoDisponivel": true,
  "fonte": "Frankfurter API"
}
```

A cotação mostrada é apenas ilustrativa; o valor real depende do dia da consulta.

### 11.4. Tratamento de falhas externas

A integração trata situações em que o serviço externo esteja indisponível, sem interromper o funcionamento geral da API. Quando ocorre timeout, falha de comunicação com a API (`RestClientException`) ou resposta inválida/incompleta (corpo vazio ou cotação ausente ou menor/igual a zero), a rota usa um **fallback**: retorna 200 com os dados do material e o valor em dólar, deixando os campos da cotação nulos e sinalizando a indisponibilidade:

```json
{
  "id": 1,
  "nome": "Aço",
  "quantidade": 100.00,
  "precoUnitarioDolar": 10.00,
  "valorTotalDolar": 1000.00,
  "cotacaoDolar": null,
  "valorTotalReais": null,
  "dataCotacao": null,
  "cotacaoDisponivel": false,
  "fonte": "Cotação externa indisponível"
}
```

O campo `cotacaoDisponivel` permite que o cliente saiba se o valor em reais está disponível.

### 11.5. Valor agregado ao sistema

A integração permite que o controle de estoque também ofereça informações relacionadas à cotação de moedas, possibilitando que uma indústria acompanhe o valor em reais de matérias-primas cotadas em dólar, sem precisar implementar um serviço próprio de câmbio.

Dessa forma, a aplicação combina dados próprios, armazenados no PostgreSQL, com informações obtidas de um serviço externo real, atendendo à proposta da carta-desafio C1 — Elo Externo.

## 12. Documentação complementar

Para consultar informações mais detalhadas sobre a estrutura e o funcionamento do projeto:

- [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md) — entidades e relacionamentos.
- [docs/contrato-da-api.md](docs/contrato-da-api.md) — rotas, DTOs, exemplos e respostas de erro.
- [docs/autoavaliacao.md](docs/autoavaliacao.md) — checklist de revisão do projeto.
- [documentos/documentacao.md](documentos/documentacao.md) — documentação descritiva do trabalho.

---

**Projeto desenvolvido para a disciplina de Backend do curso de Engenharia de Software da SATC.**

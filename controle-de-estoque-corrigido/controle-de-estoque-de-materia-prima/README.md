# Controle de Estoque de Matéria-Prima

API REST desenvolvida para a disciplina de Backend do curso de Engenharia de Software da SATC. O sistema permite cadastrar matérias-primas e fornecedores, registrar entradas e saídas e consultar materiais abaixo do estoque mínimo.

## Objetivo

Ajudar no acompanhamento do estoque de uma indústria, mantendo o saldo atualizado a partir das movimentações e evitando saídas maiores que a quantidade disponível.

## Tecnologias

- Java 25
- Spring Boot 3.5.16
- Spring Web (API REST)
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Flyway (versionamento do esquema do banco)
- Bean Validation
- Springdoc OpenAPI / Swagger UI
- Maven Wrapper

## Requisitos

- JDK 25 instalado e configurado no `PATH`.
- Docker Desktop com Docker Compose, ou uma instância PostgreSQL configurada com os mesmos dados de conexão do `src/main/resources/application.properties`.

## Como executar

1. Abra um terminal na pasta que contém este `README.md`.
2. Inicie o banco de dados com `docker compose up -d postgres`.
3. Aguarde o PostgreSQL ficar saudável. A configuração local usa a porta `5437`, banco `projeto`, usuário `projeto` e senha `projeto`. Esses dados são apenas para desenvolvimento local.
4. Inicie a API no Windows com `mvnw.cmd spring-boot:run` ou no Linux/macOS com `./mvnw spring-boot:run`.
5. Quando a aplicação iniciar, abra o Swagger UI em <http://localhost:8080/swagger-ui.html>.

Para encerrar o banco: `docker compose down`. Para apagar também os dados persistidos, use `docker compose down -v` (isso remove os dados locais do banco).

## Endpoints principais

| Método | Rota | Finalidade |
|---|---|---|
| `POST` | `/materiais` | Cadastrar material |
| `GET` | `/materiais` | Listar materiais |
| `GET` | `/materiais/{id}` | Consultar material por ID |
| `PUT` | `/materiais/{id}` | Atualizar material |
| `GET` | `/materiais/estoque-baixo` | Listar materiais com quantidade abaixo do mínimo |
| `POST` | `/fornecedores` | Cadastrar fornecedor |
| `GET` | `/fornecedores` | Listar fornecedores |
| `GET` | `/fornecedores/{id}` | Consultar fornecedor por ID |
| `PUT` | `/fornecedores/{id}` | Atualizar fornecedor |
| `POST` | `/movimentacoes` | Registrar entrada ou saída |
| `GET` | `/movimentacoes` | Listar movimentações |

## Regras de negócio implementadas

1. A quantidade de uma movimentação deve ser maior que zero.
2. Uma saída não pode ser maior que a quantidade disponível no estoque.
3. Uma entrada aumenta o saldo do material e uma saída diminui o saldo; a movimentação é registrada no banco.
4. O tipo deve ser `entrada` ou `saida`.
5. A consulta de estoque baixo retorna materiais cuja quantidade atual é menor que o estoque mínimo cadastrado.
6. Material e fornecedor referenciados precisam existir; o fornecedor é opcional na movimentação.

## Organização do código

```text
src/main/java/com/industria/estoque/
├── config/       # Configurações da API/OpenAPI
├── controller/   # Endpoints HTTP
├── dto/          # Objetos de entrada e resposta
├── mapper/       # Conversão entre DTOs e entidades
├── model/        # Entidades persistidas
├── repository/   # Acesso ao banco com Spring Data JPA
└── service/      # Regras de negócio

src/main/resources/
└── db/migration/ # Scripts versionados do Flyway
```

## Banco de dados

O Flyway executa a migration `V1__criar_tabelas_estoque.sql`, que cria `fornecedores`, `materiais` e `movimentacoes`. A estrutura é validada pelo Hibernate na inicialização.

## Documentação complementar

- [`docs/modelo-de-dominio.md`](docs/modelo-de-dominio.md): entidades e relacionamentos.
- [`docs/contrato-da-api.md`](docs/contrato-da-api.md): rotas, DTOs, exemplos e respostas de erro.
- [`docs/autoavaliacao.md`](docs/autoavaliacao.md): checklist de revisão do projeto.
- [`documentacao.md`](documentacao.md): documentação descritiva do trabalho.

## Limitações conhecidas e melhorias futuras

- Criar testes automatizados para serviços, controllers e regras de negócio.
- Adicionar autenticação e autorização, se forem requisitos do sistema.
- Considerar paginação e filtros para listas maiores.
- Avaliar o uso de transações e concorrência em movimentações simultâneas de estoque.
- Separar credenciais de banco em variáveis de ambiente em ambientes fora do desenvolvimento local.

## Integrantes

| Integrante | GitHub |
|---|---|
| Bernardo Rzatki da Silva de Souza | [@bernardorzatkidesouza-cyber](https://github.com/bernardorzatkidesouza-cyber) |
| Miguel Machado Gomes | [@gomesisworking](https://github.com/gomesisworking) |
| Jenifer da Silva Vieira | [@JeniferSilvaVieira](https://github.com/JeniferSilvaVieira) |

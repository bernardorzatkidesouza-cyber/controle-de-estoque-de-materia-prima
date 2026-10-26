# Documentação do projeto — Controle de Estoque de Matéria-Prima

**Disciplina:** Backend — Engenharia de Software, SATC  
**Tipo:** API REST para controle de matérias-primas industriais

## 1. Visão geral

O projeto tem como objetivo apoiar o controle de estoque de matéria-prima em uma indústria. A API permite cadastrar materiais e fornecedores, registrar entradas e saídas, consultar saldos e identificar materiais abaixo do estoque mínimo.

## 2. Problema e público-alvo

A falta de controle das entradas e saídas pode causar falta de insumos durante a produção ou compras desnecessárias. O sistema atende principalmente funcionários do estoque, responsáveis pelo cadastro e movimentação, e gestores que precisam consultar os saldos.

## 3. Tecnologias

- **Java e Spring Boot:** implementação da aplicação e dos endpoints REST.
- **Spring Web:** recebimento e resposta de requisições HTTP.
- **Spring Data JPA/Hibernate:** persistência das entidades.
- **PostgreSQL:** armazenamento relacional.
- **Flyway:** versionamento da estrutura do banco.
- **Bean Validation:** validação dos dados de entrada.
- **Springdoc OpenAPI/Swagger UI:** documentação interativa dos endpoints.
- **Maven Wrapper:** execução e gerenciamento das dependências do projeto.

## 4. Arquitetura

O código é organizado em camadas para separar responsabilidades:

| Camada | Responsabilidade |
|---|---|
| `controller` | Define as rotas HTTP e recebe os DTOs. |
| `service` | Executa as regras de negócio e coordena as operações. |
| `repository` | Consulta e persiste dados com Spring Data JPA. |
| `model` | Representa as entidades persistidas. |
| `dto` | Define os dados aceitos e devolvidos pela API. |
| `mapper` | Converte DTOs em entidades e vice-versa. |
| `config` | Contém configurações, incluindo OpenAPI. |

## 5. Entidades e relacionamentos

- **Material:** nome, quantidade atual e estoque mínimo.
- **Fornecedor:** nome e contato opcional.
- **Movimentacao:** material associado, fornecedor opcional, tipo, quantidade e data/hora.
- Um material pode ter várias movimentações; cada movimentação deve referenciar um material.
- Um fornecedor pode estar associado a várias movimentações, mas o vínculo é opcional.

O diagrama e os atributos detalhados estão em [`docs/modelo-de-dominio.md`](docs/modelo-de-dominio.md).

## 6. Regras de negócio

1. A quantidade da movimentação deve ser maior que zero.
2. O tipo deve ser `entrada` ou `saida`.
3. Uma entrada soma a quantidade ao estoque do material.
4. Uma saída subtrai a quantidade, desde que não ultrapasse o saldo disponível.
5. O material precisa existir; o fornecedor, quando informado, também precisa existir.
6. A consulta de estoque baixo retorna materiais com quantidade menor que o estoque mínimo.

## 7. Endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | `/materiais` | Cadastrar material |
| GET | `/materiais` | Listar materiais |
| GET | `/materiais/{id}` | Consultar material |
| PUT | `/materiais/{id}` | Atualizar material |
| GET | `/materiais/estoque-baixo` | Consultar estoque abaixo do mínimo |
| POST | `/fornecedores` | Cadastrar fornecedor |
| GET | `/fornecedores` | Listar fornecedores |
| GET | `/fornecedores/{id}` | Consultar fornecedor |
| PUT | `/fornecedores/{id}` | Atualizar fornecedor |
| POST | `/movimentacoes` | Registrar entrada ou saída |
| GET | `/movimentacoes` | Listar movimentações |

O contrato completo e os exemplos JSON estão em [`docs/contrato-da-api.md`](docs/contrato-da-api.md).

## 8. Como executar localmente

1. Instale JDK 25 e Docker Desktop.
2. Abra o terminal na raiz do projeto.
3. Execute `docker compose up -d postgres`.
4. Execute `mvnw.cmd spring-boot:run` no Windows ou `./mvnw spring-boot:run` no Linux/macOS.
5. Acesse `http://localhost:8080/swagger-ui.html`.

A configuração local utiliza PostgreSQL na porta `5437`, banco `projeto`, usuário `projeto` e senha `projeto`. Não use essas credenciais de desenvolvimento em ambientes de produção.

## 9. Tratamento de erros

O tratador global `ApiExceptionHandler` padroniza erros conhecidos. Dados inválidos e regras de negócio violadas retornam `400 Bad Request`; recursos não encontrados retornam `404 Not Found`. A estrutura da resposta é definida por `ErroDTO`.

## 10. Limitações e próximos passos

O repositório não contém testes automatizados identificáveis nesta versão. Recomenda-se criá-los, padronizar os status HTTP dos cadastros, avaliar um endpoint de consulta de movimentação por ID e revisar o controle de concorrência para movimentações simultâneas.

## 11. Integrantes

| Nome | GitHub |
|---|---|
| Bernardo Rzatki da Silva de Souza | [@bernardorzatkidesouza-cyber](https://github.com/bernardorzatkidesouza-cyber) |
| Miguel Machado Gomes | [@gomesisworking](https://github.com/gomesisworking) |
| Jenifer da Silva Vieira | [@JeniferSilvaVieira](https://github.com/JeniferSilvaVieira) |

# Autoavaliação do projeto

Checklist de revisão com evidências no repositório. As marcações abaixo são uma avaliação documental preliminar baseada na leitura do código; a execução local e a confirmação dos critérios exatos da disciplina ainda devem ser verificadas pelo grupo.

Legenda: ✅ atende pelo código/documentação; ◐ atende parcialmente ou exige confirmação; ❌ não atende.

| Critério | Avaliação preliminar | Evidência / observação |
|---|---|---|
| P1 — A API sobe seguindo o README | ◐ | O README atualizado descreve a execução, mas o grupo precisa testar no computador de apresentação. |
| P2 — Classes organizadas por camada | ✅ | `src/main/java/com/industria/estoque/` contém `controller`, `service`, `repository`, `model`, `dto`, `mapper` e `config`. |
| P3 — Migrations e entidades correspondentes | ✅ | `V1__criar_tabelas_estoque.sql` e as entidades `Material`, `Fornecedor` e `Movimentacao`. Confirmar a inicialização com banco limpo. |
| P4 — DTOs de entrada e saída | ✅ | `MaterialEntradaDTO`, `MaterialRespostaDTO`, `FornecedorEntradaDTO`, `FornecedorRespostaDTO`, `MovimentacaoEntradaDTO` e `MovimentacaoRespostaDTO`. |
| P5 — Repository, Mapper, Service e Controller para todas as entidades, comentários exigidos e mapa da estrutura | ◐ | As camadas principais existem para materiais e fornecedores; movimentações usam controller, service e repository, mas não têm mapper dedicado. Confirmar se o professor exige mapper e comentários em todas as entidades. |
| P6 — API simples com POST, GET, GET por ID e erros 400/404 | ◐ | Materiais e fornecedores possuem POST, GET e GET por ID; movimentações têm POST e GET de lista, mas não GET por ID. Confirmar a interpretação do critério para cada entidade. |
| P7 — Pelo menos três regras de negócio com erro 400 | ✅ | Quantidade positiva; tipo válido; impedir saída maior que o saldo; material/fornecedor precisam existir quando referenciados. |
| P8 — Regras relacionadas ao tema | ✅ | As regras de movimentação e consulta de estoque baixo correspondem ao controle de matéria-prima. |
| P9 — Código coerente com os documentos de domínio e API | ◐ | Os documentos foram atualizados para refletir o código. Fazer uma última comparação após qualquer mudança de implementação. |
| P10 — README completo e claro | ◐ | O README inclui objetivo, tecnologias, execução, endpoints e arquitetura; validar os comandos no ambiente de apresentação. |

## Verificações antes da entrega

- [ ] Subir o PostgreSQL com `docker compose up -d postgres`.
- [ ] Iniciar a API com `mvnw.cmd spring-boot:run` no Windows.
- [ ] Confirmar Swagger em `http://localhost:8080/swagger-ui.html`.
- [ ] Testar cadastro e consulta de material e fornecedor.
- [ ] Testar entrada, saída e tentativa de saída maior que o saldo.
- [ ] Confirmar a consulta de estoque baixo.
- [ ] Conferir se o professor exige `201 Created`, `GET /{id}` para movimentações, Mapper dedicado ou comentários padronizados.
- [ ] Não declarar testes automatizados como existentes sem que tenham sido criados e executados.

## Pontos fortes

- Separação de responsabilidades em camadas.
- Uso de DTOs e validação de entradas.
- Regras de negócio no serviço de movimentações.
- Respostas de erro centralizadas.
- Uso de migrations para versionar a estrutura do banco.

## Melhorias futuras

- Criar testes automatizados para serviços e endpoints.
- Padronizar status HTTP, por exemplo `201 Created` nos cadastros.
- Avaliar adicionar `GET /movimentacoes/{id}` se for requisito.
- Considerar concorrência em movimentações simultâneas para evitar inconsistências de saldo.

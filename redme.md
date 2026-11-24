

POST /fornecedores
{
  "nome": "Aço Brasil Ltda",
  "contato": "vendas@acobrasil.com.br"
}
POST /materiais
{
  "nome": "Chapa de aço 2mm",
  "quantidade": 0,
  "estoqueMinimo": 10
}
POST /movimentacoes — entrada de 5. Depois, GET /materiais/estoque-baixo deve listar o material.
{
  "materialId": 1,
  "fornecedorId": 1,
  "tipo": "entrada",
  "quantidade": 5
}
POST /movimentacoes — entrada de 20. O material sai da lista de estoque baixo.
{
  "materialId": 1,
  "fornecedorId": 1,
  "tipo": "entrada",
  "quantidade": 20
}
POST /movimentacoes — saída válida de 2. Saldo vai para 23.
{
  "materialId": 1,
  "tipo": "saida",
  "quantidade": 2
}
POST /movimentacoes — saída maior que o saldo. Esperado: 400, "RN01: estoque insuficiente".
{
  "materialId": 1,
  "tipo": "saida",
  "quantidade": 100
}
GET /materiais/{id} com 999. Sem JSON. Esperado: 404.
POST /materiais — dados inválidos. Esperado: 400 com a lista de campos.
 {
  "nome": "",
  "quantidade": -1,
  "estoqueMinimo": 10
}
GET /movimentacoes. Sem JSON. Mostra o histórico.






set SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/projeto
mvnw.cmd spring-boot:run


set SPRING_DATASOURCE_USERNAME=postgres
set SPRING_DATASOURCE_PASSWORD=sua_senha_do_postgres

cd controle-de-estoque-corrigido\controle-de-estoque-de-materia-prima
dir mvnw.cmd
mvnw.cmd spring-boot:run
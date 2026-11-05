# Expense Management API

## Despesas por usuário

As operações em `/api/expenses` usam o usuário da sessão autenticada. O backend
define o proprietário ao criar uma despesa e limita listagem, leitura, edição e
exclusão às despesas desse usuário. Não envie `userId` no corpo da requisição.

Na atualização do schema, despesas preexistentes recebem `user_id` nulo e ficam
ocultas da API até serem atribuídas manualmente a um usuário.

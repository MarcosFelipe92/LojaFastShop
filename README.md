# FastShop

Este projeto é uma aplicação de e-commerce desenvolvida com as seguintes tecnologias:

- **Frontend**: 
  - **Next.js** com **React**
  - **TypeScript**
  - **Validações de formulários**: react-hook-form e zod

- **Backend**: 
  - **Spring Boot** com **Java**
  - **Spring Security** com autenticação JWT
  - **Banco de dados**: PostgreSQL
  - **Testes**: JUnit e H2 Database
  - **Encriptação de senhas**: BCrypt
  - **Validação de requisições**: Spring Validation
  - **DTOs** para tráfego de dados
  - **Exception Handlers** customizados para tratamento de erros

## Funcionalidades Implementadas

### Usuário
- **CRUD completo de usuários**: Os usuários podem ser criados, lidos, atualizados e deletados. 
- **Autenticação e Autorização**:
  - **Login** utilizando JWT, com codificação de senhas usando BCrypt.
  - Validação de permissões baseada em roles, garantindo que apenas usuários autorizados possam acessar ou modificar seus próprios dados.
  - **Autorização granular** para impedir que um usuário acesse ou modifique dados de outro usuário.
- **Criação de Contas Associadas**: Cada usuário tem uma conta associada que é criada automaticamente ao se registrar, incluindo um carrinho de compras.
- **Gerenciamento de Endereços e Telefones**: Usuários podem adicionar múltiplos endereços e números de telefone, com validação de permissões.

### Produto
- **CRUD completo de produtos**: Os produtos podem ser gerenciados (criados, lidos, atualizados e deletados) por usuários com permissões adequadas.
- **Acesso Público a Produtos**: A página inicial exibe todos os produtos, que podem ser visualizados por qualquer usuário, logado ou não.

### Carrinho de Compras
- **Gerenciamento de Carrinho**: Cada usuário tem um carrinho de compras associado à sua conta, onde pode adicionar e remover produtos.

### Pedidos e Pagamentos  
- **Criação de Pedidos**: Endpoint para gerar pedidos a partir do carrinho de compras.  
- **Integração com Mercado Pago**: O backend processa pagamentos automaticamente via API do Mercado Pago.  
- **Observação**: Atualmente, essa funcionalidade está implementada apenas no backend.  

### Roles e Permissões
- **Gerenciamento de Roles**: As permissões de usuário são gerenciadas por meio de roles (e.g., admin, user).
- **Validação de Permissões**: Implementação robusta de validações de permissões, garantindo que as operações sejam permitidas apenas para os usuários certos.

### Segurança
- **Spring Security**: Implementação de segurança com JWT para autenticação, e validação de permissões.
- **Criptografia**: Senhas são criptografadas com BCrypt antes de serem armazenadas no banco de dados.

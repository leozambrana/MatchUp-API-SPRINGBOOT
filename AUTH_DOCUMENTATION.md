# Documentação - Sistema de Autenticação MatchUp

## 📋 Resumo

Sistema completo de autenticação com JWT implementado para o MatchUp. Inclui login, registro e validação de tokens.

---

## 🚀 Endpoints

### 1. Registrar novo usuário
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123"
}
```

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "joao@example.com",
  "name": "João Silva",
  "message": "Usuário cadastrado com sucesso"
}
```

---

### 2. Fazer Login
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "email": "joao@example.com",
  "password": "senha123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "joao@example.com",
  "name": "João Silva",
  "message": "Login realizado com sucesso"
}
```

---

## 🔐 Como usar o Token

Adicione o token no header de suas requisições autenticadas:

```
Authorization: Bearer <seu_token_aqui>
```

**Exemplo com cURL:**
```bash
curl -X GET http://localhost:8080/api/games \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## 📁 Estrutura de Arquivos Criados

```
src/main/java/com/leozambrana/MatchUp/
├── controller/
│   └── AuthController.java          # Endpoints de autenticação
├── dto/
│   ├── LoginRequest.java            # DTO para login
│   ├── RegisterRequest.java         # DTO para registro
│   └── AuthResponse.java            # DTO para resposta de autenticação
├── repository/
│   └── UserRepository.java          # Acesso ao BD de usuários
├── security/
│   ├── JwtTokenProvider.java        # Geração e validação de JWT
│   └── JwtAuthenticationFilter.java # Filtro de autenticação
└── config/
    └── SecurityConfig.java          # Configuração de segurança
```

---

## ⚙️ Configurações

**application.properties:**
```properties
# JWT Configuration
jwt.secret=a-very-long-secret-key-that-needs-to-be-at-least-256-bits-for-HS256-algorithm-security
jwt.expiration=86400000  # 24 horas em milissegundos
```

**⚠️ IMPORTANTE:** Mude o `jwt.secret` em produção para um valor seguro!

---

## 🔍 Validações

### Registro
- ✅ Nome: 3-100 caracteres
- ✅ Email: deve ser válido e único
- ✅ Senha: mínimo 6 caracteres
- ✅ Criptografia: BCrypt

### Login
- ✅ Email deve estar registrado
- ✅ Senha deve estar correta
- ✅ Token JWT válido por 24h

---

## 🛡️ Segurança

- CSRF disabled (API stateless)
- CORS configurado
- Sessões stateless (JWT)
- Senhas criptografadas com BCrypt
- Tokens JWT com assinatura HS512

---

## ⚡ Próximos Passos (Opcionais)

Se necessário depois, você pode adicionar:
- [ ] Refresh tokens
- [ ] Logout com token blacklist
- [ ] 2FA (Two-Factor Authentication)
- [ ] Roles e Permissions
- [ ] Rate limiting
- [ ] Email verification

---

## 🐛 Tratamento de Erros

**Email não encontrado (Login):**
```json
{
  "message": "Usuário não encontrado"
}
```
Status: `401 Unauthorized`

**Senha incorreta:**
```json
{
  "message": "Senha incorreta"
}
```
Status: `401 Unauthorized`

**Email já cadastrado (Registro):**
```json
{
  "message": "Email já cadastrado"
}
```
Status: `400 Bad Request`

---

## 📚 Dependências Adicionadas

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

---

Pronto para testar! 🎉


package br.com.kaique.techchallenge.config;

public final class SwaggerExamples {

    public static final String CADASTRO_CLIENTE = """
            {
              "nome": "Joao Silva",
              "endereco": "Rua das Flores, 123",
              "email": "joao.silva@email.com",
              "login": "joao.silva",
              "senha": "senha123",
              "tipoUsuario": "CLIENTE"
            }
            """;

    public static final String LISTA_USUARIOS = """
            [
              {
                "id": 1,
                "nome": "Joao Silva",
                "endereco": "Rua das Flores, 123",
                "email": "joao.silva@email.com",
                "login": "joao.silva",
                "senha": "senha123",
                "tipoUsuario": "CLIENTE",
                "dataUltimaAlteracao": "2026-05-05T18:00:00"
              }
            ]
            """;

    public static final String USUARIO_ATUALIZADO = """
            {
              "id": 1,
              "nome": "Joao Silva",
              "endereco": "Rua das Flores, 456",
              "email": "joao.silva@email.com",
              "login": "joao.silva",
              "senha": "senha123",
              "tipoUsuario": "DONO",
              "dataUltimaAlteracao": "2026-05-05T18:30:00"
            }
            """;

    public static final String ATUALIZACAO_USUARIO = """
            {
              "nome": "Joao Silva",
              "endereco": "Rua das Flores, 456",
              "email": "joao.silva@email.com",
              "login": "joao.silva",
              "tipoUsuario": "DONO"
            }
            """;

    public static final String ATUALIZACAO_SENHA = """
            {
              "senha": "novaSenha123"
            }
            """;

    public static final String LOGIN = """
            {
              "login": "joao.silva",
              "senha": "senha123"
            }
            """;

    public static final String LOGIN_VALIDO = "true";

    public static final String ERRO_VALIDACAO_CADASTRO = """
            {
              "type": "/errors/validation",
              "title": "Dados invalidos",
              "status": 400,
              "detail": "Existem campos invalidos na requisicao",
              "errors": [
                "nome: must not be blank",
                "email: must not be blank"
              ]
            }
            """;

    public static final String ERRO_VALIDACAO_LOGIN = """
            {
              "type": "/errors/validation",
              "title": "Dados invalidos",
              "status": 400,
              "detail": "Existem campos invalidos na requisicao",
              "errors": [
                "login: must not be blank",
                "senha: must not be blank"
              ]
            }
            """;

    public static final String ERRO_VALIDACAO_LOGIN_EM_BRANCO = """
            {
              "type": "/errors/validation",
              "title": "Dados invalidos",
              "status": 400,
              "detail": "Existem campos invalidos na requisicao",
              "errors": [
                "login: must not be blank"
              ]
            }
            """;

    public static final String ERRO_VALIDACAO_SENHA = """
            {
              "type": "/errors/validation",
              "title": "Dados invalidos",
              "status": 400,
              "detail": "Existem campos invalidos na requisicao",
              "errors": [
                "senha: must not be blank"
              ]
            }
            """;

    public static final String ERRO_EMAIL_DUPLICADO = """
            {
              "type": "/errors/conflict",
              "title": "Conflito de dados",
              "status": 409,
              "detail": "Email ja existe"
            }
            """;

    public static final String ERRO_LOGIN_DUPLICADO = """
            {
              "type": "/errors/conflict",
              "title": "Conflito de dados",
              "status": 409,
              "detail": "Login ja existe"
            }
            """;

    public static final String ERRO_USUARIO_NAO_ENCONTRADO = """
            {
              "type": "/errors/not-found",
              "title": "Recurso nao encontrado",
              "status": 404,
              "detail": "Usuario nao encontrado com o ID:  1"
            }
            """;

    public static final String ERRO_CREDENCIAIS_INVALIDAS = """
            {
              "type": "/errors/invalid-credentials",
              "title": "Credenciais invalidas",
              "status": 401,
              "detail": "Login ou senha incorreto"
            }
            """;

    private SwaggerExamples() {
    }
}

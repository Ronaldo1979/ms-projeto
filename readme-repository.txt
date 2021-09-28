********************************************
Execução inicial dos microsserviços
********************************************
Para a utilização das APIs dos microsserviços de Secretaria, Orçamento e Projeto é necessário ter um usuário cadastrado para autenticação,
a senha do usuário deve está no formato Bcrypt Ex.: teste = $2a$12$Uwsf5E2H6PNhUC7woW7GIeVDPYhzbv2cAhXjEw7thrzMdmmhcTKRi
Autenticação:
{
"email":"ronaldo.fjv@gmail.com",
"senha":"teste"
}

********************************************
Testes Unitários
********************************************

Para realização dos testes unitários a autenticação do Spring Security deverá ser ajustada para 
permitir acesso a todas as requisições sem autenticação. Para isso, deverá ser efetuado os seguintes ajustes antes de executar os testes
Na classe WebSecurityConfig substituir no metodo configure
.antMatchers("/resource/*").authenticated() por .antMatchers("/resource/*").permitAll()


********************************************
Documentação Swagger
********************************************

MS-SECRETARIA --> http://localhost:8085/swagger-ui.html#/secretaria-api
MS-ORÇAMENTO  --> http://localhost:8086/swagger-ui.html#/orcamento-api
MS-PROJETO    --> http://localhost:8087/swagger-ui.html#/projeto-api


********************************************
Documentação Postman
********************************************

https://documenter.getpostman.com/view/9703312/UUxzASvL

















































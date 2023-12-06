# Projeto Token - Login utilizando token e OpenSSL

Trabalho prático da disciplina Segurança em Sistemas Computacionais. O intuito do projeto é criar uma aplicação simples para simular o login utilizando token, como uma camada extra de segurança. 
Cada usuário que possui direito de acesso possui um par de chaves assimétricas (pública e privada) gerados através do [OpenSSL](https://www.openssl.org/), desta forma, basta inserir o token USB e identificar-se com seu nome de usuário e senha da chave privada que o sistema fará a verificação de autenticidade, ou seja, se a chave pública é realmente derivada da chave privada e se a senha está correta

Referências:

  - Documentação do OpenSSL: https://www.openssl.org/docs/

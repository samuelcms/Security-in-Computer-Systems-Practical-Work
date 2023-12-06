# Projeto Token - Login utilizando token e OpenSSL

Trabalho prático da disciplina Segurança em Sistemas Computacionais. O intuito do projeto é criar uma aplicação simples para simular o login utilizando token, como uma camada extra de segurança. 
Cada usuário que possui direito de acesso possui um par de chaves assimétricas (pública e privada) gerados através do [OpenSSL](https://www.openssl.org/), desta forma, basta inserir o token USB e identificar-se com seu nome de usuário e senha da chave privada que o sistema fará a verificação de autenticidade, ou seja, se a chave pública é realmente derivada da chave privada e se a senha está correta

# Referências

  - Site OpenSSL: https://www.openssl.org/docs/
  - Site API Bouncy Castle: https://www.bouncycastle.org/java.html

# Imagens

Abaixo há a interação de login bem sucedida:

![image](https://github.com/samuelcms/Security-in-Computer-Systems-Practcical-Work/assets/44274148/8c0e585c-069b-4e0b-97a6-ee19269a1585)
  O usuário insere o token contendo sua chave privada.


![image](https://github.com/samuelcms/Security-in-Computer-Systems-Practcical-Work/assets/44274148/0c4d0363-c601-4c69-afc4-e80297c45c2a)
_Logo após, fornece seu nome de usuário e a senha da chave privada._


![image](https://github.com/samuelcms/Security-in-Computer-Systems-Practcical-Work/assets/44274148/9e6b00b6-b366-4f7f-94ee-b771e15d1673)
_O sistema verifica a autenticidade e correspondência das chaves e aprova o login._




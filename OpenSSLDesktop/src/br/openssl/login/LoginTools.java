package br.openssl.login;

import static br.openssl.key.KeyTools.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import static br.openssl.constants.Constants.*;
import static br.openssl.filesystem.FileTools.decryptFile;
import static br.openssl.filesystem.FileTools.encryptFile;
import static br.openssl.gui.GraphicUserInterface.showCustomDialog;

public class LoginTools 
{
	public static boolean approveLogin(String privKey, String username, String password) 
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try 
		{
			// Lendo a chave privada do arquivo (protegida por senha).
			String privateKeyPath =   privKey; 
			String privateKeyPassword = password;

			if (!Files.exists(Paths.get(privateKeyPath))) 
			{
				showCustomDialog("Chave Privada", "Chave privada não encontrada.", ERROR_MESSAGE);
				//System.out.println("Chave privada não encontrada");
				//System.exit(1);
			}

			// Importando a chave privada para o programa.
			PrivateKey privateKey = readEncryptedPrivateKey(privateKeyPath, privateKeyPassword);
			
			String pubKeyName = generateKeyName(username, false);
			
			// Lendo a chave pública do arquivo.
			String publicKeyPath = String.format("%s%s", PUB_KEY_PATH, pubKeyName); 

			if (!Files.exists(Paths.get(publicKeyPath))) 
			{
				showCustomDialog("Chave Pública", "Chave pública não encontrada.", ERROR_MESSAGE);
				//System.out.println("Chave pública não encontrada");
				// System.exit(1);
			}

			// Importando a chave pública para o programa.
			PublicKey publicKey = readPublicKey(publicKeyPath);

			if (privateKey != null && publicKey != null) 
			{
				//System.out.println("Chaves privada e pública lidas com sucesso!");

				// Criando o arquivo que será criptografado com a chave privada.
				String unencryptedFile = "Files/arquivo_descriptografado.txt";
				Files.writeString(Path.of(unencryptedFile), "Conteúdo do arquivo a ser criptografado");

				// Criando o arquivo criptografado.
				String encryptedFile = "Files/arquivo_criptografado.dat";
				encryptFile(unencryptedFile, encryptedFile, privateKey);
				System.out.println("Arquivo criptografado com sucesso");

				// Descriptografando o arquivo com a chave pública.
				String decryptedFile = "Files/arquivo_descriptografado.txt";
				decryptFile(encryptedFile, decryptedFile, publicKey);
				System.out.println("Arquivo descriptografado com sucesso");

				// Excluindo os arquivos ao final do teste de autenticidade.
				Files.deleteIfExists(Path.of(unencryptedFile));
				Files.deleteIfExists(Path.of(encryptedFile));
				Files.deleteIfExists(Path.of(decryptedFile));

				return true;
			}

			else System.out.println("Falha ao ler chaves privada e/ou pública. Verifique os logs para mais detalhes.");

		} 
		catch (Exception e) 
		{
			// e.printStackTrace();
			
			/*
			 * Se as chaves não estiverem disponíveis ou estiverem corrompidas, o programa gerará uma exceção para notificar 
			 * que o logon não está aprovado. O mesmo acontece quando o usuário fornece a senha errada para sua chave privada. 
			 */
			//System.out.println("Login não aprovado.");
			return false;
		}

		return false;
	}
	
} // class LoginTools

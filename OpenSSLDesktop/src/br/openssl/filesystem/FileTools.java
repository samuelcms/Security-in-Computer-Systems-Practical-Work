package br.openssl.filesystem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class FileTools 
{
	public static boolean encryptFile(String inputPath, String outputPath, PrivateKey privateKey) throws Exception 
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			byte[] inputBytes = Files.readAllBytes(Path.of(inputPath));
			byte[] encryptedBytes = cipher.doFinal(inputBytes);

			Files.write(Path.of(outputPath), encryptedBytes);
			// System.out.println("Arquivo criptografado com sucesso: " + outputPath);
			return true;
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	public static boolean decryptFile(String inputPath, String outputPath, PublicKey publicKey) throws Exception 
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			byte[] encryptedBytes = Files.readAllBytes(Path.of(inputPath));
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			Files.write(Path.of(outputPath), decryptedBytes);
			// System.out.println("Arquivo descriptografado com sucesso: " + outputPath);
			return true;
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
		
} // class FileTools

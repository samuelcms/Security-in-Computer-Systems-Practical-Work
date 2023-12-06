package br.openssl.filesystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PathTools 
{
	public static boolean tokenExists(String token) 
	{
		Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();

		for (FileStore fileStore : fileStores) 
		{
			String mountedPath = fileStore.toString();

			// Verifica se o dispositivo de armazenamento está montado e possui o nome especificado.
			if (mountedPath.contains(token)) 
				return true;
		}

		return false;
	}

	public static String tokenPath(Path directory, String token) 
	{    	
		try 
		{
			Thread.sleep(1000);

			// Lista todos as entradas do diretório.
			for (var entry : directory.toFile().listFiles()) 
			{
				// Verifica se o nome do token está presente.
				if (entry.getName().contains(token)) 
					return entry.getAbsolutePath();
			}
		} 
		catch (Exception e) 
		{
			// e.printStackTrace();
		}

		return null;
	}
	
	public static int countDevices(Path directoryPath) 
	{
		// Construindo o comando para listar o conteúdo do diretório.
		String[] command = {"ls", directoryPath.toString()};
		ProcessBuilder processBuilder = new ProcessBuilder(command);

		try 
		{
			Process process = processBuilder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			int lineCount = 0;            
			while ((br.readLine()) != null) lineCount++;
			return lineCount;
		} 
		catch (Exception e) 
		{
			// e.printStackTrace();
			return 0;
		}
	}
	
	public static String getSystemUserName() 
	{
		return System.getProperty("user.name");
	}

} // class PathTools

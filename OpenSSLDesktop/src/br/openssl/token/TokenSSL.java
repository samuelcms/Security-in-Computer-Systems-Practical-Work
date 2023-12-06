package br.openssl.token;

import static br.openssl.constants.Constants.DEVICE_DIR;
import static br.openssl.constants.Constants.MEDIA_PATH;
import static br.openssl.constants.Constants.TOKEN_NAME;
import static br.openssl.filesystem.PathTools.countDevices;
import static br.openssl.filesystem.PathTools.tokenPath;
import static br.openssl.key.KeyTools.generateKeyName;
import static br.openssl.key.KeyTools.userExists;
import static br.openssl.login.LoginTools.approveLogin;

import static br.openssl.gui.GraphicUserInterface.*;
import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Scanner;

public class TokenSSL 
{
	private static boolean TOKEN_PRESENTE = false;
	private static int numberDevices = 0;
	
	// TEMP
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException 
	{
        // Criando um objeto WatchService para representar o sistema de arquivos.
		WatchService watchService = FileSystems.getDefault().newWatchService();

        // Guardando o caminho do diretório que contém links simbólicos para os dispositivos de armazenamento identificados pelo SO.
		Path directory = Path.of(DEVICE_DIR);
		directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

	    // Inicializa o número de registros com a quantidade atual de registeros do diretório de discos.
		numberDevices = countDevices(directory);
		
        // Verifica se o token já está inserido.
		if (tokenExists(TOKEN_NAME)) 
		{		
			// TEMP
			// System.out.println("- Token já inserido.");
			
			showTokenStatusDialog(true);
			
			List<String> loginFields = showLoginDialog();
			
			if(loginFields != null)
			{			
				String username = loginFields.get(LOGIN_INDEX);
				String password = loginFields.get(PASSWD_INDEX);
				
				TOKEN_PRESENTE = true;
						
				if(userExists(username))
				{
					String privKeyName = generateKeyName(username, true);
					String privKey = String.format("%s/%s", tokenPath(Path.of(MEDIA_PATH), TOKEN_NAME), privKeyName);
					
					if(approveLogin(privKey, username, password)) 
						showCustomDialog("Status do Login", "Login aprovado!", INFORMATION_MESSAGE); 
						// System.out.println("Login aprovado");
					
					else
						showCustomDialog("Status do Login", "Não foi possível realizar o login.\nInsira novamente seu token e credenciais.", ERROR_MESSAGE);
						//System.out.println("Login inválido. Insira novamente seu token e credenciais.");
				}
				
				else showCustomDialog("Status do Login", "Usuário não encontrado!\nInsira novamente seu token e credenciais.", ERROR_MESSAGE);	
			}
		}

		// Aguarda os eventos gerados pela inserção e remoção do token de acesso.
		while (true) 
		{
			WatchKey watchKey = null;

			try 
			{
				watchKey = watchService.take();

				// Trata o evento e atualiza o status do token.
				for (var event : watchKey.pollEvents()) 
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE || event.kind() == StandardWatchEventKinds.ENTRY_DELETE) 
						tokenEventHandler(directory);

				if (!watchKey.reset()) break;

			} 
			catch (InterruptedException e) 
			{
				// e.printStackTrace();
			}
		}
		
		scanner.close();
	}

	private static boolean tokenEventHandler(Path systemDiskPath) 
	{
		// Verifica o número de registros do diretório de discos.
		int newNumberDevices = countDevices(systemDiskPath);

		// Verifica se algum disco foi inserido ou removido.
		if (newNumberDevices != numberDevices) 
		{
			// Verifica se o token estava presente e foi removido.
			if (newNumberDevices > numberDevices) 
			{
				if (!TOKEN_PRESENTE) 
				{					
					// TEMP
					//System.out.println("Token inserido. Caminho: " + tokenPath(Path.of(MEDIA_PATH), TOKEN_NAME));
					showTokenStatusDialog(true);
					
					List<String> loginFields = showLoginDialog();
					
					if(loginFields != null)
					{			
						String username = loginFields.get(LOGIN_INDEX);
						String password = loginFields.get(PASSWD_INDEX);
										
						if(userExists(username))
						{
							String privKeyName = generateKeyName(username, true);
							String privKey = String.format("%s/%s", tokenPath(Path.of(MEDIA_PATH), TOKEN_NAME), privKeyName);
													
							if(approveLogin(privKey, username, password)) 
								showCustomDialog("Status do Login", "Login aprovado!", INFORMATION_MESSAGE); 
								// System.out.println("Login aprovado");
							
							else
								showCustomDialog("Status do Login", "Não foi possível realizar o login.\nInsira novamente seu token e credenciais.", ERROR_MESSAGE);
								//System.out.println("Login inválido. Insira novamente seu token e credenciais.");
							
							TOKEN_PRESENTE = true;
							return true;
						}
						else
						{
							// TEMP
							//System.out.println("Usuário não encontrado. Insira novamente seu token e credenciais.");
							showCustomDialog("Status do Login", "Não foi possível realizar o login.\nInsira novamente seu token e credenciais.", ERROR_MESSAGE);
							return false;
						}
					}
					else
						showCustomDialog("Status do Login", "Login cancelado!", ERROR_MESSAGE);	
						
				}
			} 
			else 
			{
				if (TOKEN_PRESENTE) 
				{
					// TEMP
					//System.out.println("Token removido.");
					showTokenStatusDialog(false);
					TOKEN_PRESENTE = false;
					return false;
				}
			}

			// Atualiza o número de discos.
			numberDevices = newNumberDevices;
		}
		
		return tokenExists(TOKEN_NAME);
	}

	private static boolean tokenExists(String token) 
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
}

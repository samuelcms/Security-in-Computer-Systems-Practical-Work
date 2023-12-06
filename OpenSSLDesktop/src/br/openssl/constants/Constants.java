package br.openssl.constants;

import static java.lang.String.format;
import static br.openssl.filesystem.PathTools.getSystemUserName;

public interface Constants 
{
	static final String MEDIA_PATH = 		format("/media/%s", getSystemUserName()),  // Obtém o caminho do diretório '/media', padrão de dispositivos de mídia removíveis. 
						DEVICE_DIR = 		"/dev/disk/by-path",			     // Contém links simbólicos para os dispositivos de armazenamento do SO.		
						TOKEN_NAME = 		"TOKEN", 						     // Nome do dispositivo que armazena a chave privada do usuário.
						PUB_KEY_PATH = 		"Keys/",						     // Diretório padrão que armazena as chaves públicas de cada usuário.
						PRIV_KEY_PREFIX = 	"priv-",						     // Prefixo de identificação padrão para chaves privadas.  
						PUB_KEY_PREFIX = 	"pub-",								 // Prefixo de identificação padrão para chaves públicas.
						KEY_EXTENSION = 	".pem";							     // Extensão das chaves públicas/privadas.
	
}

package br.openssl.gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GraphicUserInterface {

    private static final String TOKEN_INSERTED_MESSAGE = "Token inserido!";
    private static final String TOKEN_REMOVED_MESSAGE = "Token removido!";
    private static final String DIALOG_TITLE = "Status do Token";
    
    public static int LOGIN_INDEX = 0, PASSWD_INDEX = 1;

    public static List<String> showLoginDialog() 
    {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Aumentando o tamanho da fonte
        Font defaultFont = UIManager.getFont("TextField.font");

        if (defaultFont != null) 
        {
            Font newFont = defaultFont.deriveFont(14f);
            UIManager.put("OptionPane.messageFont", newFont);
            UIManager.put("TextField.font", newFont);
            UIManager.put("PasswordField.font", newFont);
        }

        // Definindo um tamanho padrão para os campos
        usernameField.setColumns(30);
        passwordField.setColumns(30);

        Object[] message = {"Usuário:", usernameField, "Senha:", passwordField};

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) 
        {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            
            return new ArrayList<String>(Arrays.asList(new String[]{username, password})) ; 
        } 
 
        return null;
    }

    public static void showTokenStatusDialog(boolean tokenInserted) 
    {
        String message = tokenInserted ? TOKEN_INSERTED_MESSAGE : TOKEN_REMOVED_MESSAGE;
        int messageType = tokenInserted ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;

        JOptionPane.showMessageDialog
        (
                null,
                message,
                DIALOG_TITLE,
                messageType
        );
    }

    public static void showCustomDialog(String title, String message, int iconType) 
    {
        JOptionPane.showMessageDialog
        (
                null,
                message,
                title,
                iconType
        );
    }
}
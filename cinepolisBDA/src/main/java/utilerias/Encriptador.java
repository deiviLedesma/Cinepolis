/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author SDavidLedesma
 */
public class Encriptador {
    
    //Genera un hash de la contraseña utilizando bcrypt
    public static String hashPassword(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
    
    //compara la contraseña ingresada con el hash almacenado
    public static boolean checkPassword(String pass, String hashedPassWord){
        return BCrypt.checkpw(pass, hashedPassWord);
    }
    
}

package model;
    

import java.awt.Toolkit;
import javax.swing.JFrame;
/**
 *
 * @author Lucas
 */
public class Utilitarios {
    
   public void InserirIcone(JFrame frm){
       try{
         frm.setIconImage(Toolkit.getDefaultToolkit().getImage("src/imagens/icon.png"));
       }catch(Exception ex){
           System.out.println(ex.toString());
       }
           
       
   }
    
    
}

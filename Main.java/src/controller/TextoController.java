
package controller;

public class TextoController {
    
    //Verificação do campo de texto CPF, se é possível digitar ele retorna true, caso contrário retorna false
    public boolean validacaoCPF(java.awt.event.KeyEvent evt, String cpf){
        return (limiteCPF(cpf) && apenasNumero(evt));
    }
    
    //Verifica se o que esta sendo digitado é um numero
    public boolean apenasNumero(java.awt.event.KeyEvent evt){
        String caracteresPermitidos = "0123456789";
        
        return caracteresPermitidos.contains(evt.getKeyChar() + ""); 
        
    }
    
    //Define limite para campo do CPF
    public boolean limiteCPF(String cpf){
        int limite = 13;
        
        return cpf.length()<=limite;
     
    }
    
    //Cria uma espécie de mascara para o cpf
    public String mascaraCPF(String cpf){
        int tamanho = cpf.length();
        
        if(tamanho == 3 || tamanho == 7){
            cpf = cpf + ".";
        }
        if(tamanho == 11){
            cpf = cpf + "-";
        }
        return cpf;
     }
    
    
    
}

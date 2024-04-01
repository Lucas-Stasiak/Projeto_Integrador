/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.AdmView;

/**
 *
 * @author luizf
 */
public class MenuAdmController {
    
    private AdmView view;

    
    public MenuAdmController(AdmView view) {
        this.view = view;
    }

    public void menuPrincipal(){
        view.getMenuPrincipal().setVisible(true);
        view.getUsuarioPane().setVisible(false);
        view.getProdutoPane().setVisible(false);
    }
    
    public void usuarioPane(){
        view.getUsuarioPane().setVisible(true);
        view.getMenuPrincipal().setVisible(false);
        view.getProdutoPane().setVisible(false);
    }
    
    public void produtoPane(){
        view.getProdutoPane().setVisible(true);
        view.getUsuarioPane().setVisible(false);
        view.getMenuPrincipal().setVisible(false);
        
    }
        
}
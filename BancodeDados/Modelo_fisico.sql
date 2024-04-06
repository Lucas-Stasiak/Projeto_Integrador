/* Modelo_Logico: */

CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY,
    cpf VARCHAR(14),
    nome VARCHAR(50),
    senha VARCHAR(50),
    telefone VARCHAR(20),
    admin BOOLEAN,
    fk_id_endereco SERIAL
);

CREATE TABLE Endereco (
    id_endereco SERIAL PRIMARY KEY,
    rua VARCHAR(50),
    bairro VARCHAR(50),
    numero INT,
    municipio  VARCHAR(20),
    sigla_estado VARCHAR(2),
    complemento VARCHAR(50),
    cep VARCHAR(10)
);

CREATE TABLE Produtos (
    id_produto INT PRIMARY KEY,
    nome VARCHAR(50),
    descricao VARCHAR(50),
    preco MONEY,
    unidade VARCHAR(20),
    quantidade INT,
    fk_nome_categoria VARCHAR(20)
);

CREATE TABLE Categoria (
    nome_categoria VARCHAR(20) PRIMARY KEY
);

CREATE TABLE Cliente (
    id_cliente INT PRIMARY KEY,
    nome VARCHAR(50),
    cpf VARCHAR(14),
    telefone VARCHAR(20),
    rg VARCHAR(20),
    fk_id_endereco SERIAL
);

CREATE TABLE Compra (
    id_Compra INT PRIMARY KEY,
    preco_total MONEY,
    fk_id_cliente INT,
    fk_id_usuario INT
);

CREATE TABLE Historico (
    id_historico INT PRIMARY KEY,
    data DATE,
    tempo TIME WITH TIME ZONE,
    fk_id_Compra INT,
    fk_id_cliente INT,
    fk_id_usuario INT
);

CREATE TABLE item_compra (
    id_item INT PRIMARY KEY,
    preco_unitario MONEY,
    quantidade INT,
    fk_id_Compra INT,
    fk_id_produto INT
);
 
ALTER TABLE Usuario ADD CONSTRAINT FK_Usuario_2
    FOREIGN KEY (fk_id_endereco)
    REFERENCES Endereco (id_endereco)
    ON DELETE RESTRICT;
 
ALTER TABLE Produtos ADD CONSTRAINT FK_Produtos_2
    FOREIGN KEY (fk_nome_categoria)
    REFERENCES Categoria (nome_categoria)
    ON DELETE RESTRICT;
 
ALTER TABLE Cliente ADD CONSTRAINT FK_Cliente_2
    FOREIGN KEY (fk_id_endereco)
    REFERENCES Endereco (id_endereco)
    ON DELETE RESTRICT;
 
ALTER TABLE Compra ADD CONSTRAINT FK_Compra_2
    FOREIGN KEY (fk_id_cliente)
    REFERENCES Cliente (id_cliente)
    ON DELETE RESTRICT;
 
ALTER TABLE Compra ADD CONSTRAINT FK_Compra_3
    FOREIGN KEY (fk_id_usuario)
    REFERENCES Usuario (id_usuario)
    ON DELETE CASCADE;
 
ALTER TABLE Historico ADD CONSTRAINT FK_Historico_2
    FOREIGN KEY (fk_id_Compra)
    REFERENCES Compra (id_Compra)
    ON DELETE CASCADE;
 
ALTER TABLE Historico ADD CONSTRAINT FK_Historico_3
    FOREIGN KEY (fk_id_cliente)
    REFERENCES Cliente (id_cliente)
    ON DELETE RESTRICT;
 
ALTER TABLE Historico ADD CONSTRAINT FK_Historico_4
    FOREIGN KEY (fk_id_usuario)
    REFERENCES Usuario (id_usuario)
    ON DELETE RESTRICT;
 
ALTER TABLE item_compra ADD CONSTRAINT FK_item_compra_2
    FOREIGN KEY (fk_id_produto)
    REFERENCES Produtos (id_produto)
    ON DELETE RESTRICT;
 
ALTER TABLE item_compra ADD CONSTRAINT FK_item_compra_3
    FOREIGN KEY (fk_id_Compra)
    REFERENCES Compra (id_Compra)
    ON DELETE SET NULL;
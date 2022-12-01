package br.com.fiap.produtos;

import br.com.fiap.produtos.model.Categoria;
import br.com.fiap.produtos.model.Produto;
import br.com.fiap.produtos.repository.CategoriaCollectionRepository;
import br.com.fiap.produtos.repository.ProdutoCollectionRepository;
import br.com.fiap.produtos.view.CategoriaView;
import br.com.fiap.produtos.view.Opcao;
import br.com.fiap.produtos.view.OpcaoView;
import br.com.fiap.produtos.view.ProdutoView;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Opcao opcao = null;

        do {
            opcao = OpcaoView.select();
            switch (opcao) {
                case CADASTRAR_CATEGORIA -> cadastrarCategoria();
                case CADASTRAR_PRODUTO -> cadastrarProduto();
                case ALTERAR_PRODUTO -> alterarProduto();
                case CONSULTAR_PRODUTO_POR_ID -> consultarProdutoPorId();
                case CONSULTAR_PRODUTO_POR_CATEGORIA -> consultarProdutoPorCategoria();
                case ENCERRAR_SISTEMA -> encerrarOSistema();
            }


        } while (opcao != Opcao.ENCERRAR_SISTEMA);


    }

    private static void alterarProduto() {
        Produto produto = ProdutoView.select(null);
        ProdutoView.update(produto);
    }

    private static void encerrarOSistema() {
        System.exit(0);
    }

    private static void consultarProdutoPorCategoria() {
        Categoria categoria = CategoriaView.select(null);

        List<Produto> produtos = ProdutoCollectionRepository.findByCategoria(categoria);

        if(produtos.isEmpty()){
            JOptionPane.showMessageDialog(null, "Não encontramos produtos com a categoria: " + categoria.getNome());
        }else{
            produtos.forEach(ProdutoView::show);
            produtos.forEach(System.out::println);
        }
    }

    private static void consultarProdutoPorId() {

        Long id = -1l;

        do {
            try {
                id = Long.valueOf(JOptionPane.showInputDialog(null, "Informe o ID do Produto"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Número inválido!");
            }
        } while (id <= 0);

        Produto produto = ProdutoCollectionRepository.findById(id);

        if (produto != null) {
            ProdutoView.show(produto);
        } else {
            JOptionPane.showMessageDialog(null, "Produto não foi encontrado");
        }
    }

    private static void cadastrarProduto() {
        Produto produto = ProdutoView.form(new Produto());
        ProdutoCollectionRepository.save(produto);
        ProdutoView.sucesso(produto);
    }

    private static void cadastrarCategoria() {
        CategoriaView view = new CategoriaView();
        Categoria categoria = view.form(new Categoria());
        CategoriaCollectionRepository.save(categoria);
        view.sucesso(categoria);
    }
}
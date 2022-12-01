package br.com.fiap.produtos.view;

import br.com.fiap.produtos.model.Categoria;
import br.com.fiap.produtos.model.Produto;
import br.com.fiap.produtos.repository.ProdutoCollectionRepository;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoView {
    public static Produto form(Produto produto) {

        Categoria categoria = null;

        do {
            categoria = CategoriaView.select(produto.getCategoria());
        } while (categoria == null);

        String nome = "";

        do {
            nome = JOptionPane.showInputDialog(null, "Informe o nome do produto", produto.getNome());
        } while (nome.equals(""));


        String descricao = "";

        do {
            descricao = JOptionPane.showInputDialog(null, "Informe a descrição do produto", produto.getDescricao());
        } while (descricao.equals(""));

        double preco = 0;

        do {
            try {
                preco = Double.parseDouble(JOptionPane.showInputDialog(null, "Informe o preço do produto", produto.getPreco()));
            } catch (Exception e) {
                preco = 0;
            }
        } while (preco <= 0);


        Produto ret = produto;

        ret.setCategoria(categoria)
                .setNome(nome)
                .setDescricao(descricao)
                .setDataDeCadastro(LocalDateTime.now())
                .setPreco(BigDecimal.valueOf(preco));

        return ret;
    }

    public void sucesso() {
        JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");
    }

    public static void sucesso(Produto produto) {
        JOptionPane.showMessageDialog(null, "Produto " + produto.getNome() + " salvo com sucesso!");
    }


    public static Produto select(Produto produto) {
        // @formatter:off
        Produto ret = (Produto) JOptionPane.showInputDialog(
                null, // componente pai. Como não temos será null
                "Selecione um produto",
                "Menu",
                JOptionPane.QUESTION_MESSAGE,
                null, // icone
                ProdutoCollectionRepository.findAll().toArray(), // Número da opção
                produto == null ? 1 : produto);
        return ret;
        // @formatter:on
    }


    public static void update(Produto produto) {
        form(produto);
        sucesso(produto);
        show(produto);
    }

    public static void show(Produto p) {
        System.out.println(p);
        String textoFormatado = String.format("PRODUTO: " + p.getNome() + System.lineSeparator() + "DESCRIÇÃO: " + p.getDescricao() + System.lineSeparator() + "CATEGORIA: " + p.getCategoria().toString() + System.lineSeparator() + "PREÇO: %,.2f", p.getPreco());
        JOptionPane.showMessageDialog(null, textoFormatado);
    }

}

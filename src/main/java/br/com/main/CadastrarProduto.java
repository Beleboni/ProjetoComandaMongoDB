package br.com.main;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.conexao.MongoDB;
import br.com.model.Produto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarProduto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jtfDescricaoProduto;
	private JTextField jtfValorProduto;
	private JLabel lblDescricao;
	private JLabel lblValor;
	private JButton btnCadastrar;
	private JSeparator separator;
	private JLabel lblCadastrarNovoProduto;

	// INVOCA OU CRIA UMA COLEÇÃO NO BANCO
	private static final String collectionProdutos = "produtos";

	public CadastrarProduto() {
		setTitle("Comanda >> Cadastrar produto\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCadastrarNovoProduto = new JLabel("CADASTRAR NOVO PRODUTO:");
		lblCadastrarNovoProduto
				.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblCadastrarNovoProduto.setBounds(132, 11, 164, 26);
		contentPane.add(lblCadastrarNovoProduto);

		lblDescricao = new JLabel("Descri\u00E7\u00E3o:");
		lblDescricao.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblDescricao.setBounds(23, 65, 85, 26);
		contentPane.add(lblDescricao);

		lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblValor.setBounds(23, 118, 77, 26);
		contentPane.add(lblValor);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtfDescricaoProduto.getText().equals("")
						|| jtfValorProduto.getText().equals("")) {
					JOptionPane.showMessageDialog(btnCadastrar,
							"Por favor, preencha todos os campos !!!");
				} else {
					Produto produto = new Produto();
					// NOVO PRODUTO
					produto.setDescricao(jtfDescricaoProduto.getText());
					produto.setValor(Double.parseDouble(jtfValorProduto
							.getText()));
					// CADASTRA PRODUTO NO BANCO
					MongoDB.getCollection(CadastrarProduto.collectionProdutos)
							.insert(produto);
					//MENSAGEM DE SUCESSO
					JOptionPane.showMessageDialog(btnCadastrar, "Produto cadastrado com sucesso !!!");
					//LIMPANDO CAMPOS
					jtfDescricaoProduto.setText("");
					jtfValorProduto.setText("");
				}
			}
		});
		btnCadastrar.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		btnCadastrar.setBounds(107, 183, 147, 30);
		contentPane.add(btnCadastrar);

		jtfDescricaoProduto = new JTextField();
		jtfDescricaoProduto.setBounds(107, 64, 243, 30);
		contentPane.add(jtfDescricaoProduto);
		jtfDescricaoProduto.setColumns(10);

		jtfValorProduto = new JTextField();
		jtfValorProduto.setBounds(107, 117, 107, 30);
		contentPane.add(jtfValorProduto);
		jtfValorProduto.setColumns(10);

		separator = new JSeparator();
		separator.setBounds(23, 48, 385, 2);
		contentPane.add(separator);

		setVisible(true);
	}

}

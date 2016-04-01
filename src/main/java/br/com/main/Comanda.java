package br.com.main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bson.types.ObjectId;

import br.com.conexao.MongoDB;
import br.com.model.Cliente;
import br.com.model.Contato;
import br.com.model.Entrega;
import br.com.model.Pedido;
import br.com.model.Produto;

public class Comanda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jcpGeral;
	private JTextField tjfTotalPedido;
	private JTextField jtfEnderecoCliente;
	private JTextField jtfBairroCliente;
	private JTextField jtfComplementoCliente;
	private JTextField jtfNomeCliente;
	private JTextField jtfTelefoneCliente;
	private DefaultTableModel dtmListaEscolhida;
	private JTable jtTabelaEscolhida;
	private JScrollPane jspRolagemEscolhida;
	private JLabel lblItensEscolhidos;
	private JLabel lblTotal;
	private JLabel lblGerarPedido;
	private JSeparator reguaUm;
	private JLabel lblDadosDoCliente;
	private JLabel lblNome;
	private JLabel lblEscolherProdutos;
	private JSeparator reguaDois;
	private DefaultTableModel dtmLista;
	private JTable jtTabela;
	private JScrollPane jspRolagem;
	private JButton btnGravarItens;
	private JSeparator reguaTres;
	private JSeparator reguaQuatro;
	private JLabel lblLocalizao;
	private JLabel lblEndereo;
	private JLabel lblBairro;
	private JLabel lblComplemento;
	private JLabel lblContatos;
	private JLabel lblTelefone;
	private JButton btnGerarPedido;

	// INVOCA OU CRIA UMA COLEÇÃO NO BANCO
	private static final String collectionProdutos = "produtos";
	private static final String collectionClientes = "clientes";
	private static final String collectionsPedidos = "pedidos";
	private static final String collectionsEntregas = "entregas";
	private static final String collectionsTelefones = "telefones";

	public Comanda() {

		setTitle("Comanda >> Gerar pedido\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1060, 550);
		setLocationRelativeTo(null);
		setResizable(false);

		jcpGeral = new JPanel();
		jcpGeral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jcpGeral);
		jcpGeral.setLayout(null);

		lblGerarPedido = new JLabel("GERAR PEDIDO");
		lblGerarPedido.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblGerarPedido.setBounds(460, 11, 100, 26);
		jcpGeral.add(lblGerarPedido);

		reguaUm = new JSeparator();
		reguaUm.setBounds(10, 39, 1020, 2);
		jcpGeral.add(reguaUm);

		lblDadosDoCliente = new JLabel("DADOS DO CLIENTE:");
		lblDadosDoCliente.setBounds(10, 39, 147, 26);
		jcpGeral.add(lblDadosDoCliente);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 62, 46, 26);
		jcpGeral.add(lblNome);

		lblEscolherProdutos = new JLabel("ESCOLHER PRODUTOS:");
		lblEscolherProdutos.setBounds(10, 142, 176, 26);
		jcpGeral.add(lblEscolherProdutos);

		reguaDois = new JSeparator();
		reguaDois.setBounds(10, 129, 475, 2);
		jcpGeral.add(reguaDois);

		dtmLista = new DefaultTableModel();
		dtmLista.addColumn("CÓDIGO");
		dtmLista.addColumn("DESCRIÇÃO");
		dtmLista.addColumn("PREÇO");

		// POPULANDO A TABELA
		MongoDB.getCollection(Comanda.collectionProdutos)
				.find()
				.as(Produto.class)
				.forEach(
				// FOREACH RESPONSAVEL POR POPULAR A TABELA
						f -> dtmLista.addRow(new String[] {
								f.get_id().toString(),
								f.getDescricao().toString(),
								f.getValor().toString() }));

		jtTabela = new JTable(dtmLista);
		jtTabela.getColumnModel().getColumn(0).setMaxWidth(350);
		jtTabela.getColumnModel().getColumn(1).setMaxWidth(450);
		jtTabela.getColumnModel().getColumn(2).setMaxWidth(100);

		jspRolagem = new JScrollPane(jtTabela);
		jspRolagem.setBounds(10, 166, 475, 104);
		jcpGeral.add(jspRolagem);
		
		btnGravarItens = new JButton("Adicionar item");
		btnGravarItens.setBounds(350, 281, 135, 23);
		jcpGeral.add(btnGravarItens);

		reguaTres = new JSeparator();
		reguaTres.setBounds(10, 315, 475, 2);
		jcpGeral.add(reguaTres);

		lblItensEscolhidos = new JLabel("ITENS ESCOLHIDOS:");
		lblItensEscolhidos.setBounds(10, 328, 161, 26);
		jcpGeral.add(lblItensEscolhidos);

		dtmListaEscolhida = new DefaultTableModel();
		dtmListaEscolhida.addColumn("CÓDIGO");
		dtmListaEscolhida.addColumn("DESCRIÇÃO");
		dtmListaEscolhida.addColumn("PREÇO");

		jtTabelaEscolhida = new JTable(dtmListaEscolhida);
		jtTabelaEscolhida.getColumnModel().getColumn(0).setMaxWidth(350);
		jtTabelaEscolhida.getColumnModel().getColumn(1).setMaxWidth(450);
		jtTabelaEscolhida.getColumnModel().getColumn(2).setMaxWidth(100);

		jspRolagemEscolhida = new JScrollPane(jtTabelaEscolhida);
		jspRolagemEscolhida.setBounds(10, 350, 475, 104);
		jcpGeral.add(jspRolagemEscolhida);

		lblTotal = new JLabel("Total:");
		lblTotal.setBounds(350, 472, 46, 26);
		jcpGeral.add(lblTotal);

		tjfTotalPedido = new JTextField("0.00");
		tjfTotalPedido.setEnabled(false);
		tjfTotalPedido.setBounds(399, 470, 86, 30);
		jcpGeral.add(tjfTotalPedido);

		reguaQuatro = new JSeparator();
		reguaQuatro.setBounds(511, 261, 520, 2);
		jcpGeral.add(reguaQuatro);

		lblLocalizao = new JLabel("LOCALIZA\u00C7\u00C3O:");
		lblLocalizao.setBounds(511, 39, 147, 26);
		jcpGeral.add(lblLocalizao);

		lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(511, 62, 100, 26);
		jcpGeral.add(lblEndereo);

		jtfEnderecoCliente = new JTextField();
		jtfEnderecoCliente.setBounds(511, 88, 522, 30);
		jcpGeral.add(jtfEnderecoCliente);
		jtfEnderecoCliente.setColumns(10);

		lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(511, 135, 72, 14);
		jcpGeral.add(lblBairro);

		jtfBairroCliente = new JTextField();
		jtfBairroCliente.setColumns(10);
		jtfBairroCliente.setBounds(511, 154, 522, 30);
		jcpGeral.add(jtfBairroCliente);

		lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(511, 190, 100, 14);
		jcpGeral.add(lblComplemento);

		jtfComplementoCliente = new JTextField();
		jtfComplementoCliente.setColumns(10);
		jtfComplementoCliente.setBounds(511, 208, 522, 30);
		jcpGeral.add(jtfComplementoCliente);

		jtfNomeCliente = new JTextField();
		jtfNomeCliente.setColumns(10);
		jtfNomeCliente.setBounds(10, 88, 475, 30);
		jcpGeral.add(jtfNomeCliente);

		lblContatos = new JLabel("CONTATOS:");
		lblContatos.setBounds(511, 279, 100, 26);
		jcpGeral.add(lblContatos);

		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(508, 311, 100, 26);
		jcpGeral.add(lblTelefone);

		jtfTelefoneCliente = new JTextField();
		jtfTelefoneCliente.setColumns(10);
		jtfTelefoneCliente.setBounds(508, 333, 522, 30);
		jcpGeral.add(jtfTelefoneCliente);

		btnGerarPedido = new JButton("Gerar pedido");
		btnGerarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtfNomeCliente.getText().equals("")
						|| jtfEnderecoCliente.getText().equals("")
						|| jtfBairroCliente.getText().equals("")
						|| jtfComplementoCliente.getText().equals("")
						|| jtfTelefoneCliente.getText().equals("")) {
					JOptionPane.showMessageDialog(btnGerarPedido,
							"Por favor, preencha todos os campos !!!");
				} else {
					// NOVO CLIENTE
					Cliente cliente = new Cliente();
					cliente.setNome(jtfNomeCliente.getText());
					// CADASTRA CLIENTE
					MongoDB.getCollection(Comanda.collectionClientes).insert(cliente);

					// NOVA ENTREGA
					Entrega entrega = new Entrega();
					entrega.setEndereco(jtfEnderecoCliente.getText());
					entrega.setBairro(jtfBairroCliente.getText());
					entrega.setComplemento(jtfComplementoCliente.getText());
					// CADASTRA ENTREGA
					MongoDB.getCollection(Comanda.collectionsEntregas).insert(entrega);
					
					// NOVO CONTATO
					Contato contato = new Contato();
					contato.setTelefone(jtfTelefoneCliente.getText());
					// CADASTRA TELEFONE
					MongoDB.getCollection(Comanda.collectionsTelefones).insert(contato);
					// NOVO PEDIDO
					Pedido pedido = new Pedido();
					// POPULANDO A LISTA
					for (int i = 0; i < dtmListaEscolhida.getRowCount(); i++) {
						Produto produto = new Produto();
						produto.set_id(new ObjectId(dtmListaEscolhida.getValueAt(i, 0).toString()));
						produto.setDescricao(dtmListaEscolhida.getValueAt(i, 1).toString());
						produto.setValor(Double.parseDouble(dtmListaEscolhida.getValueAt(i, 2).toString()));
						pedido.addItem(produto);
					}
					//PASSANDO CHAVES ESTRANGEIRAS
					pedido.setIdCliente(cliente.get_id());
					pedido.setIdEntrega(entrega.get_id());
					pedido.setIdContato(contato.get_id());
					// CADASTRA PEDIDO
					MongoDB.getCollection(Comanda.collectionsPedidos).insert(pedido);
					
					//LIMPANDO OS CAMPOS
					jtfNomeCliente.setText("");
					tjfTotalPedido.setText("");
					jtfEnderecoCliente.setText("");
					jtfBairroCliente.setText("");
					jtfComplementoCliente.setText("");
					jtfTelefoneCliente.setText("");
					dtmListaEscolhida.setNumRows(0);
					
					//MENSAGEM DE SUCESSO
					JOptionPane.showMessageDialog(btnGerarPedido, "Pedido montado com sucesso !!!");
					// CHAMANDO O RELATORIO
					new Relatorio(pedido);

				}

			}
		});
		btnGerarPedido.setBounds(896, 468, 135, 30);
		jcpGeral.add(btnGerarPedido);
		
		//CHAMANDO O METODO DE AÇÕES DOS BOTÕES
		this.doActions();
		
		setVisible(true);
	}
	
	//METODO PARA EXECUTAR FUNÇÕES DE BOTÃO
	public void doActions() {
		btnGravarItens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTabela.getSelectedRowCount() == 0) {
					JOptionPane.showMessageDialog(jtTabela,
							"Por favor selecione um item para adicionar !!!");
				} else {
					// CRIANDO LISTA DE ITENS
					int linha = jtTabela.getSelectedRow();
					String[] item = new String[3];
					for (int i = 0; i < 3; i++) {
						item[i] = dtmLista.getValueAt(linha, i).toString();
					}
					//SOMATORIO DOS ITENS ESCOLHIDOS
					Float total = Float.parseFloat(tjfTotalPedido.getText()) + Float.parseFloat(item[item.length - 1]);
					tjfTotalPedido.setText(total.toString());
					dtmListaEscolhida.addRow(item);
				}
			}
		});
	}

}

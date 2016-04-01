package br.com.main;

import java.awt.EventQueue;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bson.types.ObjectId;

import br.com.conexao.MongoDB;
import br.com.model.Cliente;
import br.com.model.Contato;
import br.com.model.Entrega;
import br.com.model.Pedido;

public class TodosPedido extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jcpGeral;
	private JLabel lblTodosOsPedidos;
	private JButton btnFechar;
	private JSeparator separator;
	private DefaultTableModel dtmListar;
	private JTable jtTabela;
	private JScrollPane jspRolagem;
	private JButton jbtDeletar;

	// INVOCA UMA COLEÇÃO
	private static final String collectionsPedidos = "pedidos";
	private static final String collectionsTelefones = "telefones";
	private static final String collectionsEntregas = "entregas";
	private static final String collectionsClientes = "clientes";

	public TodosPedido() {
		setTitle("Comanda >> Todos os Pedidos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 360);
		setLocationRelativeTo(null);
		setResizable(false);

		jcpGeral = new JPanel();
		jcpGeral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jcpGeral);
		jcpGeral.setLayout(null);

		lblTodosOsPedidos = new JLabel("TODOS OS PEDIDOS");
		lblTodosOsPedidos.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblTodosOsPedidos.setBounds(270, 11, 154, 14);
		jcpGeral.add(lblTodosOsPedidos);

		separator = new JSeparator();
		separator.setBounds(10, 36, 620, 2);
		jcpGeral.add(separator);

		dtmListar = new DefaultTableModel();
		dtmListar.addColumn("CÓDIGO PEDIDO");
		dtmListar.addColumn("CLIENTE");
		dtmListar.addColumn("TELEFONE");
		dtmListar.addColumn("ENTREGA");

		for (Pedido pedido : MongoDB
				.getCollection(TodosPedido.collectionsPedidos).find()
				.as(Pedido.class)) {
			Cliente cliente = MongoDB
					.getCollection(TodosPedido.collectionsClientes)
					.findOne(pedido.getIdCliente()).as(Cliente.class);
			Contato contato = MongoDB
					.getCollection(TodosPedido.collectionsTelefones)
					.findOne(pedido.getIdContato()).as(Contato.class);
			Entrega entrega = MongoDB
					.getCollection(TodosPedido.collectionsEntregas)
					.findOne(pedido.getIdEntrega()).as(Entrega.class);
			dtmListar.addRow(new String[] { pedido.get_id().toString(),
					cliente.getNome(), contato.getTelefone().toString(),
					entrega.getBairro().toString() });
		}

		jtTabela = new JTable(dtmListar);
		jtTabela.getColumnModel().getColumn(0).setMaxWidth(155);
		jtTabela.getColumnModel().getColumn(1).setMaxWidth(155);
		jtTabela.getColumnModel().getColumn(2).setMaxWidth(155);
		jtTabela.getColumnModel().getColumn(3).setMaxWidth(155);

		jspRolagem = new JScrollPane(jtTabela);
		jspRolagem.setBounds(10, 60, 620, 200);
		jcpGeral.add(jspRolagem);

		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});
		btnFechar.setBounds(541, 284, 89, 30);
		jcpGeral.add(btnFechar);

		jbtDeletar = new JButton("Deletar");
		jbtDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTabela.getSelectedRowCount() == 0) {
					JOptionPane.showMessageDialog(jtTabela,
							"Por favor selecione um pedido para deletar !!!");
				} else {
					int linha = jtTabela.getSelectedRow();
					ObjectId idPedido = new ObjectId(dtmListar.getValueAt(
							linha, 0).toString());
					ObjectId idCliente = new ObjectId(dtmListar.getValueAt(
							linha, 1).toString());
					ObjectId idTelefone = new ObjectId(dtmListar.getValueAt(
							linha, 2).toString());
					ObjectId idEntrega = new ObjectId(dtmListar.getValueAt(
							linha, 3).toString());

					// REMOVER PRODUTOS
					MongoDB.getCollection(TodosPedido.collectionsPedidos)
							.remove(idPedido);
					MongoDB.getCollection(TodosPedido.collectionsClientes)
							.remove(idCliente);
					MongoDB.getCollection(TodosPedido.collectionsEntregas)
							.remove(idEntrega);
					MongoDB.getCollection(TodosPedido.collectionsTelefones)
							.remove(idTelefone);

					dtmListar.setNumRows(0);

					// POPULANDO A TABELA
					MongoDB.getCollection(TodosPedido.collectionsPedidos)
							.find()
							.as(Pedido.class)
							.forEach(
									p -> dtmListar.addRow(new String[] {
											p.get_id().toString(),
											p.getIdCliente().toString(),
											p.getIdContato().toString() }));
				}
			}
		});
		jbtDeletar.setBounds(442, 284, 89, 30);
		jcpGeral.add(jbtDeletar);

		JButton jbtAlterar = new JButton("Alterar");
		jbtAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtTabela.getSelectedRowCount() == 0) {
					JOptionPane.showMessageDialog(jtTabela,
							"Por favor selecione um item para alterar !!!");
				} else {
					int linha = jtTabela.getSelectedRow();
					ObjectId idPedido = new ObjectId(dtmListar.getValueAt(linha, 0).toString());
					Pedido pedido = MongoDB.getCollection(TodosPedido.collectionsPedidos).findOne(idPedido).as(Pedido.class);
					new AlterarTelefone(pedido.getIdContato());
					dispose();
				}
			}
		});
		jbtAlterar.setBounds(343, 284, 89, 30);
		jcpGeral.add(jbtAlterar);

		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TodosPedido frame = new TodosPedido();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

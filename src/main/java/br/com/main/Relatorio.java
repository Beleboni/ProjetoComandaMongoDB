package br.com.main;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.conexao.MongoDB;
import br.com.model.Cliente;
import br.com.model.Contato;
import br.com.model.Entrega;
import br.com.model.Pedido;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Relatorio extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// INVOCA COLEÇÕES DO BANCO
	private static final String collectionsClientes = "clientes";
	private static final String collectionsContatos = "telefones";
	private static final String collectionsEntrega = "entregas";
	
	private Cliente cliente;
	private Contato contato;
	private Entrega entrega;
	
	private JLabel lblBairro;
	private JLabel lblComplemento;	
	private DefaultTableModel dtmListaEscolhida;
	private JTable jtTabelaEscolhida;
	private JScrollPane jspRolagemEscolhida;
	private JLabel jlbNomeCliente;
	private JLabel lblTelefone;
	private JLabel lblEndereo;
	private JPanel jcpGeral;
	private JLabel lblRelatorio;
	private JSeparator separator;
	private JButton btnFechar;

	

	public Relatorio(Pedido pedido) {
		setTitle("Comanda >> Relat\u00F3rio de pedido");
		
		cliente = MongoDB.getCollection(Relatorio.collectionsClientes).findOne(pedido.getIdCliente()).as(Cliente.class);
		contato = MongoDB.getCollection(Relatorio.collectionsContatos).findOne(pedido.getIdContato()).as(Contato.class);
		entrega = MongoDB.getCollection(Relatorio.collectionsEntrega).findOne(pedido.getIdEntrega()).as(Entrega.class);	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(685, 416);
		setLocationRelativeTo(null);
		setResizable(false);

		jcpGeral = new JPanel();
		jcpGeral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jcpGeral);
		jcpGeral.setLayout(null);

		lblRelatorio = new JLabel("RELAT\u00D3RIO PEDIDO CLIENTE");
		lblRelatorio.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblRelatorio.setBounds(256, 11, 201, 30);
		jcpGeral.add(lblRelatorio);

		separator = new JSeparator();
		separator.setBounds(10, 40, 650, 2);
		jcpGeral.add(separator);
		
		jlbNomeCliente = new JLabel("Nome do cliente: " + cliente.getNome());
		jlbNomeCliente.setBounds(10, 49, 280, 30);
		jcpGeral.add(jlbNomeCliente);
		
		lblTelefone = new JLabel("Telefone: " + contato.getTelefone());
		lblTelefone.setBounds(503, 53, 180, 30);
		jcpGeral.add(lblTelefone);
		
		dtmListaEscolhida = new DefaultTableModel();
		dtmListaEscolhida.addColumn("CÓDIGO");
		dtmListaEscolhida.addColumn("DESCRIÇÃO");
		dtmListaEscolhida.addColumn("PREÇO");
		
		
		pedido.getItens().forEach(c -> dtmListaEscolhida.addRow(new String[]{
				c.get_id().toString(),
				c.getDescricao().toString(),
				c.getValor().toString()
		}));

		jtTabelaEscolhida = new JTable(dtmListaEscolhida);
		jtTabelaEscolhida.getColumnModel().getColumn(0).setMaxWidth(350);
		jtTabelaEscolhida.getColumnModel().getColumn(1).setMaxWidth(450);
		jtTabelaEscolhida.getColumnModel().getColumn(2).setMaxWidth(100);

		jspRolagemEscolhida = new JScrollPane(jtTabelaEscolhida);
		jspRolagemEscolhida.setBounds(10, 80, 650, 140);
		jcpGeral.add(jspRolagemEscolhida);
				
		lblEndereo = new JLabel("Endere\u00E7o: " + entrega.getEndereco());
		lblEndereo.setBounds(10, 247, 500, 30);
		jcpGeral.add(lblEndereo);
		
		lblBairro = new JLabel("Bairro: " + entrega.getBairro());
		lblBairro.setBounds(10, 288, 300, 30);
		jcpGeral.add(lblBairro);
		
		lblComplemento = new JLabel("Complemento: " + entrega.getComplemento());
		lblComplemento.setBounds(10, 330, 500, 30);
		jcpGeral.add(lblComplemento);
		
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFechar.setBounds(580, 330, 89, 30);
		jcpGeral.add(btnFechar);

		setVisible(true);
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorio frame = new Relatorio(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

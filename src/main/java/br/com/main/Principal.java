package br.com.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jcpGeral;
	private JLabel lblProjetoComandas;
	private JButton btnMontarCardpio;
	private JButton btnPedidoCliente;
	private JButton btnTodos;

	public Principal() {
		
		setTitle("Comanda >> Projeto comandas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setSize(610, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		
		jcpGeral = new JPanel();
		jcpGeral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jcpGeral);
		jcpGeral.setLayout(null);

		lblProjetoComandas = new JLabel("PROJETO COMANDAS");
		lblProjetoComandas.setBounds(220, 39, 127, 30);
		jcpGeral.add(lblProjetoComandas);

		btnMontarCardpio = new JButton("Montar card\u00E1pio");
		btnMontarCardpio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CadastrarProduto();
			}
		});
		btnMontarCardpio.setBounds(54, 100, 145, 40);
		jcpGeral.add(btnMontarCardpio);

		btnPedidoCliente = new JButton("Pedido cliente");
		btnPedidoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Comanda();
			}
		});
		btnPedidoCliente.setBounds(220, 100, 145, 40);
		jcpGeral.add(btnPedidoCliente);
		
		btnTodos = new JButton("Todos os pedidos");
		btnTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TodosPedido();
			}
		});
		btnTodos.setBounds(387, 100, 145, 40);
		jcpGeral.add(btnTodos);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

package br.com.main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.bson.types.ObjectId;

import br.com.conexao.MongoDB;
import br.com.model.Contato;

public class AlterarTelefone extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jcpGeral;
	private JTextField jtfTelefone;
	private JLabel jlbTelefone;
	private JButton jbtAlterar;
	private JSeparator separator;
	private JLabel jlbAlterarNome;

	private static final String collectionsTelefone = "telefones";

	public AlterarTelefone(ObjectId idTelefone) {
				
		setTitle("Comanda >> Cadastrar produto\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		setResizable(false);

		jcpGeral = new JPanel();
		jcpGeral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jcpGeral);
		jcpGeral.setLayout(null);

		jlbAlterarNome = new JLabel("ALTERAR TELEFONE:");
		jlbAlterarNome.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		jlbAlterarNome.setBounds(132, 11, 164, 26);
		jcpGeral.add(jlbAlterarNome);

		jlbTelefone = new JLabel("Alterar:");
		jlbTelefone.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		jlbTelefone.setBounds(23, 65, 85, 26);
		jcpGeral.add(jlbTelefone);
		
		//MOSTRANDO TELEFONE
		Contato contato = MongoDB.getCollection(AlterarTelefone.collectionsTelefone).findOne(idTelefone).as(Contato.class);
		
		
		jtfTelefone = new JTextField(contato.getTelefone());
		jtfTelefone.setBounds(107, 64, 243, 30);
		jcpGeral.add(jtfTelefone);

		jbtAlterar = new JButton("Alterar");
		jbtAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtfTelefone.getText().equals("")) {
					JOptionPane.showMessageDialog(jbtAlterar,
							"Por favor, preencha todos os campos !!!");
				} else {
					//ATUALIZAR TELEFONE
					Contato contatoParaAutualizar = contato;
					contatoParaAutualizar.setTelefone(jtfTelefone.getText());
					MongoDB.getCollection(AlterarTelefone.collectionsTelefone).update("{_id : #}", contato.get_id()).with(contatoParaAutualizar);
					JOptionPane.showMessageDialog(jbtAlterar, "Alterado com sucesso !!!");
					dispose();
					new Principal();
				}
			}
		});
		jbtAlterar.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		jbtAlterar.setBounds(107, 183, 147, 30);
		jcpGeral.add(jbtAlterar);

		separator = new JSeparator();
		separator.setBounds(23, 48, 385, 2);
		jcpGeral.add(separator);
		
		setVisible(true);
	}
}

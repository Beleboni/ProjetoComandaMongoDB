package br.com.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	private ObjectId _id;
	private List<Produto> itens = new ArrayList<>();
	
	private ObjectId idCliente;
	private ObjectId idEntrega;
	private ObjectId idContato;
	
	public void addItem(Produto produto){
		this.itens.add(produto);
	}
	
}

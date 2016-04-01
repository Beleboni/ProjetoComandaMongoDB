package br.com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entrega {
	
	private ObjectId _id;
	private String endereco;
	private String bairro;
	private String complemento;

}

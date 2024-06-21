package com.prestamos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="monto")
public class Monto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMonto ;
	
	private double monto1;
	
	private double monto2;
	
	private double monto3;
	
	private double monto4;
	
	private double monto5;
	
}

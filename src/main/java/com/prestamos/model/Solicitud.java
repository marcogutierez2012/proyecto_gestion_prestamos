package com.prestamos.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idSolicitud;
    private double monto;
    private double interes;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_registro")
    private Date fecRegistro;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio_prestamo")
    private Date fecInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fin_prestamo")
    private Date fecFin;
    private int dias;
    private int diaslaborales;
    private double pagodiario;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_prestatario")
    private Usuario idPrestatario;

    @ManyToOne
    @JoinColumn(name = "id_prestamista")
    private Usuario idPrestamista;


}

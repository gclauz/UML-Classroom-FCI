package service;

import entities.Cliente;
import entities.Drone;
import entities.Entrega;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private Long proximaEntrega = 1L;
    private List<Cliente> clientes = new ArrayList<>();
    private List<Entrega> entregas = new ArrayList<>();
    private List<Drone> drones = new ArrayList<>();

    public Cliente buscarCliente(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public void salvarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getEmail()) == null) {
            clientes.add(cliente);
        } else {
            System.out.println("Cliente já cadastrado!");
        }
    }

    public void salvarEntrega(Entrega entrega) {
        entrega.setId(proximaEntrega++);
        entregas.add(entrega);
    }

    public List<Entrega> buscarHistorico(Cliente cliente) {
        List<Entrega> historico = new ArrayList<>();
        for (Entrega e : entregas) {
            if (e.getCliente().equals(cliente)) {
                historico.add(e);
            }
        }
        return historico;
    }

    public void salvarDrone(Drone drone) {
        drones.add(drone);
    }

    public Drone buscarDroneDisponivel(double peso) {
        for (Drone d : drones) {
            if (d.verificarDisponibilidade() && d.getCapacidadeCarga() >= peso) {
                return d;
            }
        }
        return null;
    }

}
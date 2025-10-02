package service;

import entities.Cliente;
import entities.Drone;
import entities.Entrega;
import entities.Pagamento;

import java.time.LocalDateTime;
import java.util.List;

public class ControleEntrega {
    private BancoDeDados banc;

    public ControleEntrega(BancoDeDados banc) {
        this.banc = banc;
    }

    public void armazenaDados(Entrega entrega) {
        if (!entrega.getDestino().validarEndereco()) {
            throw new IllegalArgumentException("Endereço inválido!");
        }
        banc.salvarEntrega(entrega);
    }

    public void entregar(Entrega entrega) {
        entrega.iniciar();
        System.out.println("Entrega em andamento...");
        entrega.terminar();
    }

    public List<Entrega> consultarHistorico(Cliente cliente) {
        return banc.buscarHistorico(cliente);
    }

    public boolean processarEntrega(Entrega entrega, double valor, String metodo, LocalDateTime data) {
        Pagamento pagamento = new Pagamento(valor, metodo, data);
        boolean ok = pagamento.processarPagamento();
        entrega.setPagamento(pagamento);
        return ok;
    }

    public boolean atribuirDrone(Entrega entrega) {
        Drone drone = banc.buscarDroneDisponivel(entrega.getPeso());
        if (drone != null) {
            drone.associarEntrega(entrega);
            entrega.setDrone(drone);
            return true;
        }
        return false;
    }

}
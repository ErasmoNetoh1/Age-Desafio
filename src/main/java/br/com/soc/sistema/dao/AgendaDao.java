package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaDao extends Dao {

	public void insertAgenda(AgendaVo agendaVo) {
		String query = "INSERT INTO agenda (nm_agenda, tp_periodo_disponivel) VALUES (?, ?)";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setString(1, agendaVo.getNome());
			ps.setString(2, agendaVo.getPeriodoDisponivel().getCodigo());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new TechnicalException("Nao foi possivel incluir a agenda", e);
		}
	}

	public List<AgendaVo> findAllAgendas() {
		String query = "SELECT rowid id, nm_agenda nome, tp_periodo_disponivel periodo "
				+ "FROM agenda ORDER BY rowid";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery()
		) {
			List<AgendaVo> agendas = new ArrayList<>();

			while (rs.next()) {
				AgendaVo agenda = new AgendaVo();
				agenda.setRowid(rs.getString("id"));
				agenda.setNome(rs.getString("nome"));
				agenda.setPeriodoDisponivel(PeriodoDisponivel.buscarPor(rs.getString("periodo")));
				agendas.add(agenda);
			}

			return agendas;
		} catch (SQLException e) {
			throw new TechnicalException("Nao foi possivel consultar as agendas", e);
		}
	}

	public AgendaVo findByCodigo(Integer codigo) {
		String query = "SELECT rowid id, nm_agenda nome, tp_periodo_disponivel periodo "
				+ "FROM agenda WHERE rowid = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setInt(1, codigo);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					AgendaVo agenda = new AgendaVo();
					agenda.setRowid(rs.getString("id"));
					agenda.setNome(rs.getString("nome"));
					agenda.setPeriodoDisponivel(PeriodoDisponivel.buscarPor(rs.getString("periodo")));
					return agenda;
				}
			}
		} catch (SQLException e) {
			throw new TechnicalException("Nao foi possivel consultar a agenda", e);
		}

		return null;
	}

	public void updateAgenda(AgendaVo agendaVo) {
		String query = "UPDATE agenda SET nm_agenda = ?, tp_periodo_disponivel = ? WHERE rowid = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setString(1, agendaVo.getNome());
			ps.setString(2, agendaVo.getPeriodoDisponivel().getCodigo());
			ps.setLong(3, Long.parseLong(agendaVo.getRowid()));
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new TechnicalException("Nao foi possivel alterar a agenda", e);
		}
	}

	public void deleteAgenda(String codigo) {
		String query = "DELETE FROM agenda WHERE rowid = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setLong(1, Long.parseLong(codigo));
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new TechnicalException("Nao foi possivel excluir a agenda", e);
		}
	}
}

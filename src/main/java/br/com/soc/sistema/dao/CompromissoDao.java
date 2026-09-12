package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoDao extends Dao {

	public void insertCompromisso(CompromissoVo compromissoVo) {
		String query = "INSERT INTO compromisso (id_funcionario, id_agenda, dt_compromisso, hr_compromisso) "
				+ "VALUES (?, ?, ?, ?)";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setLong(1, Long.parseLong(compromissoVo.getCodigoFuncionario()));
			ps.setLong(2, Long.parseLong(compromissoVo.getCodigoAgenda()));
			ps.setDate(3, Date.valueOf(LocalDate.parse(compromissoVo.getData())));
			ps.setTime(4, Time.valueOf(LocalTime.parse(compromissoVo.getHorario())));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<CompromissoVo> findAllCompromissos() {
		String query = "SELECT c.rowid id, c.id_funcionario codigo_funcionario, f.nm_funcionario nome_funcionario, "
				+ "c.id_agenda codigo_agenda, a.nm_agenda nome_agenda, c.dt_compromisso data, c.hr_compromisso horario "
				+ "FROM compromisso c "
				+ "INNER JOIN funcionario f ON f.rowid = c.id_funcionario "
				+ "INNER JOIN agenda a ON a.rowid = c.id_agenda "
				+ "ORDER BY c.dt_compromisso, c.hr_compromisso";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery()
		) {
			List<CompromissoVo> compromissos = new ArrayList<>();

			while (rs.next()) {
				compromissos.add(montarCompromisso(rs));
			}

			return compromissos;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public List<CompromissoVo> findCompromissosPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
		String query = "SELECT c.rowid id, c.id_funcionario codigo_funcionario, f.nm_funcionario nome_funcionario, "
				+ "c.id_agenda codigo_agenda, a.nm_agenda nome_agenda, c.dt_compromisso data, c.hr_compromisso horario "
				+ "FROM compromisso c "
				+ "INNER JOIN funcionario f ON f.rowid = c.id_funcionario "
				+ "INNER JOIN agenda a ON a.rowid = c.id_agenda "
				+ "WHERE c.dt_compromisso BETWEEN ? AND ? "
				+ "ORDER BY c.dt_compromisso, c.hr_compromisso";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setDate(1, Date.valueOf(dataInicial));
			ps.setDate(2, Date.valueOf(dataFinal));

			try (ResultSet rs = ps.executeQuery()) {
				List<CompromissoVo> compromissos = new ArrayList<>();

				while (rs.next()) {
					compromissos.add(montarCompromisso(rs));
				}

				return compromissos;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public List<CompromissoVo> findCompromissosPorAgenda(String codigoAgenda) {
		String query = "SELECT c.rowid id, c.id_funcionario codigo_funcionario, f.nm_funcionario nome_funcionario, "
				+ "c.id_agenda codigo_agenda, a.nm_agenda nome_agenda, c.dt_compromisso data, c.hr_compromisso horario "
				+ "FROM compromisso c "
				+ "INNER JOIN funcionario f ON f.rowid = c.id_funcionario "
				+ "INNER JOIN agenda a ON a.rowid = c.id_agenda "
				+ "WHERE c.id_agenda = ? "
				+ "ORDER BY c.dt_compromisso, c.hr_compromisso";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setLong(1, Long.parseLong(codigoAgenda));

			try (ResultSet rs = ps.executeQuery()) {
				List<CompromissoVo> compromissos = new ArrayList<>();

				while (rs.next()) {
					compromissos.add(montarCompromisso(rs));
				}

				return compromissos;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public CompromissoVo findByCodigo(Integer codigo) {
		String query = "SELECT c.rowid id, c.id_funcionario codigo_funcionario, f.nm_funcionario nome_funcionario, "
				+ "c.id_agenda codigo_agenda, a.nm_agenda nome_agenda, c.dt_compromisso data, c.hr_compromisso horario "
				+ "FROM compromisso c "
				+ "INNER JOIN funcionario f ON f.rowid = c.id_funcionario "
				+ "INNER JOIN agenda a ON a.rowid = c.id_agenda "
				+ "WHERE c.rowid = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setInt(1, codigo);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? montarCompromisso(rs) : null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void updateCompromisso(CompromissoVo compromissoVo) {
		String query = "UPDATE compromisso SET id_funcionario = ?, id_agenda = ?, dt_compromisso = ?, hr_compromisso = ? "
				+ "WHERE rowid = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setLong(1, Long.parseLong(compromissoVo.getCodigoFuncionario()));
			ps.setLong(2, Long.parseLong(compromissoVo.getCodigoAgenda()));
			ps.setDate(3, Date.valueOf(LocalDate.parse(compromissoVo.getData())));
			ps.setTime(4, Time.valueOf(LocalTime.parse(compromissoVo.getHorario())));
			ps.setLong(5, Long.parseLong(compromissoVo.getRowid()));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCompromisso(String codigo) {
		String query = "DELETE FROM compromisso WHERE rowid = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setLong(1, Long.parseLong(codigo));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean existeCompromissoParaAgenda(String codigoAgenda) {
		String query = "SELECT COUNT(*) quantidade FROM compromisso WHERE id_agenda = ?";

		try (
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setLong(1, Long.parseLong(codigoAgenda));

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getInt("quantidade") > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private CompromissoVo montarCompromisso(ResultSet rs) throws SQLException {
		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setRowid(rs.getString("id"));
		compromisso.setCodigoFuncionario(rs.getString("codigo_funcionario"));
		compromisso.setNomeFuncionario(rs.getString("nome_funcionario"));
		compromisso.setCodigoAgenda(rs.getString("codigo_agenda"));
		compromisso.setNomeAgenda(rs.getString("nome_agenda"));
		compromisso.setData(rs.getDate("data").toLocalDate().toString());
		compromisso.setHorario(rs.getTime("horario").toLocalTime().toString());
		return compromisso;
	}
}

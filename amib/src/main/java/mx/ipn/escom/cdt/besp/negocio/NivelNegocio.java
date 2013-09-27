package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.NivelDao;
import mx.ipn.escom.cdt.besp.modelo.Nivel;
import mx.ipn.escom.cdt.besp.modelo.Programa;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("nivelNegocio")
public class NivelNegocio {
	private NivelDao nivelDao;

	@Transactional
	public List<Nivel> findAll() {
		return nivelDao.findAll();
	}

	@Transactional
	public Nivel findById(Integer id) {
		return nivelDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Nivel save(Nivel entidad) {
		return nivelDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Nivel update(Nivel entidad) {
		return nivelDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Nivel entidad) {
		nivelDao.delete(entidad);
	}

	@Transactional
	public List<Nivel> findByExample(Nivel nivel) {
		return nivelDao.findByExample(nivel);
	}

	@Transactional
	public Boolean existe(Nivel nivel) {
		Nivel nivelEjemplo = new Nivel();
		List<Nivel> nivels = null;
		nivelEjemplo.setNombre(nivel.getNombre());
		nivelEjemplo.setIdPrograma(nivel.getIdPrograma());

		nivels = findByExample(nivelEjemplo);

		if (nivels != null && nivels.size() > 0) {
			for (Nivel nivelEncontrado : nivels) {
				if (!nivelEncontrado.getIdNivel().equals(nivel.getIdNivel())) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional
	public Boolean estaAsociado(Nivel nivel) {
		return nivel.getEstructuras().size() > 0;
	}

	@Transactional
	public Boolean puedeSerEliminado(Nivel nivel) {
		return (noTieneEstructuras(nivel) && esMayor(nivel));
	}

	@Transactional
	public Boolean noTieneEstructuras(Nivel nivel) {
		return nivel.getEstructuras().isEmpty();
	}

	/**
	 * 
	 * @param nivel
	 * @return Si el nivel <code>nivel</code> es el de mayor profundidad del
	 *         programa asociado
	 */
	@Transactional
	public Boolean esMayor(Nivel nivel) {
		Programa padre = nivel.getPrograma();
		
		List<Nivel> nivelesPadre = padre.getNiveles();
		Integer r = nivel.getPosicion();

		for (Nivel n : nivelesPadre)
		{
			if (n.getPosicion() > r)
			{
				return false;
			}
		}
		return true;
	}

	public NivelDao getTipoAvisoDao() {
		return nivelDao;
	}

	public void setNivelDao(NivelDao nivelDao) {
		this.nivelDao = nivelDao;
	}

}

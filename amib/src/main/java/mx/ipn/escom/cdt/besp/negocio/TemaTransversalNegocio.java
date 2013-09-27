package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.TemaTransversalDao;
import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;

@Singleton
@Named("temaTransversalNegocio")
public class TemaTransversalNegocio {
	private TemaTransversalDao temaTransversalDao;
    @Transactional
	public List<TemaTransversal> findAll() { 
		return temaTransversalDao.findAll();
	}
    @Transactional
	public TemaTransversal findById(Integer id) { 
		return temaTransversalDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public TemaTransversal save(TemaTransversal entidad) { 
		return temaTransversalDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public TemaTransversal update(TemaTransversal entidad) { 
		return temaTransversalDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(TemaTransversal entidad) { 
		temaTransversalDao.delete(entidad);
	}

	
    @Transactional
	public List<TemaTransversal> findByExample(TemaTransversal temaTransversal) {
		return temaTransversalDao.findByExample(temaTransversal);
	}
    @Transactional
	public Boolean existe(String nombre) { 
		TemaTransversal temaTransversalEjemplo = new TemaTransversal();
		List<TemaTransversal> temaTransversals = null;
		temaTransversalEjemplo.setNombre(nombre);
		temaTransversals = findByExample(temaTransversalEjemplo);
		if (temaTransversals != null && temaTransversals.size() > 0) {
			return true;
		}
		return false;
	}
    @Transactional
	public Boolean estaAsociado(TemaTransversal temaTransversal) {
		return temaTransversal.getProyectoTema().size() > 0;
	}
    public TemaTransversalDao getTipoAvisoDao() {
		return temaTransversalDao;
	}

	public void setTemaTransversalDao(TemaTransversalDao temaTransversalDao) {
		this.temaTransversalDao = temaTransversalDao;
	}
}

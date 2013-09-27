package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.AvanceFinancieroDao;
import mx.ipn.escom.cdt.besp.modelo.AvanceFinanciero;

@Singleton
@Named("avanceFinancieroNegocio")
public class AvanceFinancieroNegocio {
	private Logger logger = Logger.getLogger(AvanceFinancieroNegocio.class);

	private AvanceFinancieroDao avanceFinancieroDao;

	@Transactional
	public List<AvanceFinanciero> findAll() {
		return avanceFinancieroDao.findAll();
	}

	@Transactional
	public AvanceFinanciero findById(Integer id) {
		return avanceFinancieroDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public AvanceFinanciero save(AvanceFinanciero entidad) {
		return avanceFinancieroDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(AvanceFinanciero entidad) {
		avanceFinancieroDao.delete(entidad);
	}

	@Transactional
	public List<AvanceFinanciero> findByExample(
			AvanceFinanciero avanceFinanciero) {
		return avanceFinancieroDao.findByExample(avanceFinanciero);
	}

	/**
	 * valida regla de negocio No 63
	 * 
	 * @param avanceFinanciero
	 * @return 
	 * Regresa verdadero si la suma de los ejercicios del indicador financiero mas el ejercicio que se va a agregar
	 * es menor al monto aprobado del indicador financiero
	 * Regresa falso si la suma de los ejercicios del indicador financiero mas el ejercicio que se va a agregar
	 * es mayor al monto aprobado del indicador financiero
	 */
	@Transactional
	public Boolean validaSumaMontoMenorAprobado(
			AvanceFinanciero avanceFinanciero) {
		logger.trace("*************En mi validacion**********");
		List<AvanceFinanciero> avanceFinancieroList = null;
		float suma = 0;
		avanceFinancieroList = avanceFinanciero.getIndicadorFinanciero()
				.getAvancesFinancieros();
		if (avanceFinancieroList != null && avanceFinancieroList.size() > 0) {

			for (AvanceFinanciero avanceFinanciero2 : avanceFinancieroList) {
				suma += avanceFinanciero2.getMonto();
			}
			if ((suma + avanceFinanciero.getMonto()) > avanceFinanciero
					.getIndicadorFinanciero().getMontoAprobado()) {
				return false;
			}
		}
		return true;
	}

	public AvanceFinancieroDao getAvanceFinancieroDao() {
		return avanceFinancieroDao;
	}

	public void setAvanceFinancieroDao(AvanceFinancieroDao avanceFinancieroDao) {
		this.avanceFinancieroDao = avanceFinancieroDao;
	}

	public Boolean validaMontoMayorAcero(AvanceFinanciero avanceFinanciero) {
		if (avanceFinanciero.getMonto() <= 0) {
			return false;
		}
		return true;
	}

}

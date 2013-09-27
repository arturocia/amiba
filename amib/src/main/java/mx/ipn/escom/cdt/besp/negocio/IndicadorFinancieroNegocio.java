package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.IndicadorFinancieroDao;
import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;

import org.springframework.transaction.annotation.Transactional;
@Singleton
@Named("indicadorFinancieroNegocio")
public class IndicadorFinancieroNegocio {

	private IndicadorFinancieroDao indicadorFinancieroDao;
    @Transactional
	public List<IndicadorFinanciero> findAll() {
		return indicadorFinancieroDao.findAll();
	}
    @Transactional
	public IndicadorFinanciero findById(Integer id) {
		return indicadorFinancieroDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public IndicadorFinanciero save(IndicadorFinanciero entidad) { 
		return indicadorFinancieroDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public IndicadorFinanciero update(IndicadorFinanciero entidad) { 
		return indicadorFinancieroDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(IndicadorFinanciero entidad) { 
		indicadorFinancieroDao.delete(entidad);
	}
    @Transactional
	public List<IndicadorFinanciero> findByExample(IndicadorFinanciero indicadorFinanciero) {
		return indicadorFinancieroDao.findByExample(indicadorFinanciero);
	}
    
    /**
     * Este método valida la regla de negocio 57 para indicador financiero
     * @param indicadorFinanciero
     * @return false si el monto solicitado es menor a cero
     * true si el monto solicitado es mayor a cero
     */
    @Transactional
	public Boolean validaMayorAcero(IndicadorFinanciero indicadorFinanciero){
		if(indicadorFinanciero.getMontoSolicitado()<=0){
			return false;
		}
		return true;
	}
	/**
	 * Este método valida la regla de negocio 58 para indicador financiero
	 * @param indicadorFinanciero
	 * @return false si el presupuesto no está aprobado
	 * true si el presupuesto está aprobado
	 */
	@Transactional
	public Boolean validaEstaAprobado(IndicadorFinanciero indicadorFinanciero){
		if(indicadorFinanciero.getFechaAprobado()==null){
			return false;
		}
		return true;
	}
	/**
	 * Este método valida la regla de negocio 57 para indicador financiero
	 * @param indicadorFinanciero
	 * @return false si el monto aprobado es menor que 0
	 * true en caso contrario
	 */
	@Transactional
	public Boolean validaAprobacionMayorAcero(IndicadorFinanciero indicadorFinanciero){
		if(indicadorFinanciero.getMontoAprobado()<=0){
			return false;
		}
		return true;
	}

	
	public IndicadorFinancieroDao getIndicadorFinancieroDao() {
		return indicadorFinancieroDao;
	}

	public void setIndicadorFinancieroDao(
			IndicadorFinancieroDao indicadorFinancieroDao) {
		this.indicadorFinancieroDao = indicadorFinancieroDao;
	}

}

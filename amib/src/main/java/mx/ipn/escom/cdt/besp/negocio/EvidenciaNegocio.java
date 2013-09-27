package mx.ipn.escom.cdt.besp.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.controller.SubirEvidenciaController;
import mx.ipn.escom.cdt.besp.dao.EvidenciaDao;
import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Evidencia;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Synchronize;
import org.jboss.seam.annotations.Synchronized;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("evidenciaNegocio")
public class EvidenciaNegocio {

	private EvidenciaDao evidenciaDao;
	private Logger logger = Logger.getLogger(EvidenciaNegocio.class);

	@Transactional
	public List<Evidencia> findAll() {
		return evidenciaDao.findAll();
	}

	@Transactional
	public Evidencia findById(Integer id) {
		return evidenciaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Evidencia save(Evidencia entidad) {
		return evidenciaDao.save(entidad);
	}
	
	@Transactional
	public Evidencia update(Evidencia entidad){
		return evidenciaDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Evidencia entidad) {
		evidenciaDao.delete(entidad);
	}

	@Transactional
	public List<Evidencia> findByExample(Evidencia evidencia) {
		return evidenciaDao.findByExample(evidencia);
	}

	public EvidenciaDao getEstadoDao() {
		return evidenciaDao;
	}

	public void setEvidenciaDao(EvidenciaDao evidenciaDao) {
		this.evidenciaDao = evidenciaDao;
	}

	@Transactional
	public List<Evidencia> getEvidencias(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Evidencia> evidencias = this.evidenciaDao.getEvidencias(
				idProgramas, idEjes, idTemas, idAreas);
		return evidencias;
	}
	
	@Transactional
	public List<Evidencia> buscaEvidencias(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Evidencia> evidencias = this.evidenciaDao.buscaEvidencias(
				idProgramas, idEjes, idTemas, idAreas);
		return evidencias;
	}
	
	//@Synchronize
	@Transactional
	public Evidencia guardarEvidencia(Evidencia model, Accion accion, File fileUpload, String fileUploadFileName, String path1){
		
		File fdest;
		Calendar fechaCarpeta;
		String path;
		String rutaArchivo;
		String nombreArchivo;
		if (fileUpload != null) {
			System.out.println("entro a l negocio donde archivo no es nulo");
			fechaCarpeta=Calendar.getInstance();
			fechaCarpeta.setTime(model.getFecha());
			path=path1;
			rutaArchivo=fechaCarpeta.get(Calendar.YEAR)+File.separator+fechaCarpeta.get(Calendar.MONTH)+File.separator+"proyecto_"+accion.getIdProyecto();
			File carpeta = new File(path+rutaArchivo);
			carpeta.mkdirs();
			System.out.println("creo la carpeta");
			nombreArchivo=rutaArchivo+File.separator+fileUploadFileName;
			byte[] bytes=new byte[(int)fileUpload.length()];
			//se deja el nulo pensando que el campo bl_archivo se va a eliminar de la BD
			model.setArchivo(null);
			model.setNombreArchivoFisico(nombreArchivo);
			model.setIdAccion(accion.getIdAccion());
			model.setIdProyecto(accion.getIdProyecto());
			this.save(model);
			List<Evidencia> listEviAux=this.findByExample(model);			
			if(!listEviAux.isEmpty()){				
				Evidencia aux=listEviAux.get(0);
				if(model.getIdAccion().equals(aux.getIdAccion())&&model.getIdProyecto().equals(aux.getIdProyecto())&&model.getFecha().equals(aux.getFecha())&&model.getClaveDocumental().equals(aux.getClaveDocumental())){					
					model.setIdEvidencia(aux.getIdEvidencia());
					aux.setNombreArchivoFisico(rutaArchivo+File.separator+aux.getIdEvidencia()+"_"+fileUploadFileName);
					this.update(aux);
				}
			}
			fdest=new File(path+rutaArchivo+File.separator+model.getIdEvidencia()+"_"+fileUploadFileName);
			try{
			FileUtils.copyFile(fileUpload, fdest);
			FileInputStream fileInputStream=new FileInputStream(fileUpload);
			fileInputStream.read(bytes);
			fileInputStream.close();
			}catch(FileNotFoundException e1){
				logger.trace(e1.getCause());
			}catch(IOException e){
				logger.trace(e.getCause());
			}
		}
		return model;
	}
}

package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.negocio.ContactoNegocio;
import mx.ipn.escom.cdt.besp.negocio.TipoContactoNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName",
		"catalogo-usuario/new" }) })
public class CatalogoContactoTemporalController extends ActionSupport implements
		ModelDriven<Contacto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891841404280141252L;
	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */
	private ContactoNegocio contactoNegocio;
	private TipoContactoNegocio tipoContactoNegocio;
	private UsuarioNegocio usuarioNegocio;

	/**
	 * Referencia el Capitulo con el que se est� trabajando, para el caso de
	 * nuevo, editar y eliminar.
	 */
	private Contacto model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se
	 * efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	/**
	 * Almacena los nuevos contactos, hasta que el nuevo usuario se agregue a la base de datos.
	 */
	private List<Contacto> list = null;

	private Integer idUsuarioSel;
	private List<TipoContacto> tipoContactos;

	@SkipValidation
	public HttpHeaders index() {

		list = (List<Contacto>) ActionContext.getContext().getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		return new DefaultHttpHeaders("index").disableCaching();

	}

	@SkipValidation
	public String editNew() {
		tipoContactos = tipoContactoNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPO_CONTACTOS, tipoContactos);

		return "editNew";
	}

	@SuppressWarnings("unchecked")
	public void validateCreate() {
		list = (List<Contacto>) ActionContext.getContext().getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (model.getPrincipal() == 1) {
			addActionError("No puedes agregar el contacto,Ya tienes un contacto principal de este tipo de Contacto");
		}
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getContacto().equals(model.getContacto())) {
					addActionError("El contacto ya existe");
					break;
				}
			}
		}
		// Fix me (Validar que el contacto no este en la lista
		tipoContactos = (List<TipoContacto>) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.TIPO_CONTACTOS);
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.contacto", type = ValidatorType.FIELD, key = "contacto.intro"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") })
	public HttpHeaders create() {
		list = (List<Contacto>) ActionContext.getContext().getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (list == null) {
			list = new ArrayList<Contacto>();
		}
		model.setIdContacto(list.size());
		list.add(model);
		ActionContext.getContext().getSession().put(NombreObjetosSesion.CONTACTOS_TEMPORALES, list);
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdContacto());
	}

	@SkipValidation
	public String edit() {

		tipoContactos = tipoContactoNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPO_CONTACTOS, tipoContactos);
		return "edit";
	}

	@SuppressWarnings("unchecked")
	public void validateUpdate() {
		tipoContactos = (List<TipoContacto>) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.TIPO_CONTACTOS);
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.contacto", type = ValidatorType.FIELD, key = "contacto.intro"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") })
	public String update() {
		list = (List<Contacto>) ActionContext.getContext().getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (list != null) {
			list.set(model.getIdContacto(), model);
		}
		addActionMessage("El contacto " + model.getContacto()
				+ " se actualizo correctamente");

		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		if (contactoNegocio.esPrincipalOblogatorio(model.getIdTipoContacto(),
				model.getPrincipal())) {
			addActionError(getText("elimContactoPrincipal"));
		}
	}

	public String destroy() {
		list = (List<Contacto>) ActionContext.getContext().getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (list != null)
		{
			list.remove(model);
		}
		return "success";
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		list = (List<Contacto>) ActionContext.getContext().getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (idSel != null && list != null)
		{
			model = list.get(idSel);
		}
	}

	public Contacto getModel() {
		if (model == null) {
			model = new Contacto();
		}

		return model;
	}

	public List<Contacto> getList() {
		return list;
	}

	public void setList(List<Contacto> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Contacto model) {
		this.model = model;
	}

	public void setTipoContactos(List<TipoContacto> tipoContactos) {
		this.tipoContactos = tipoContactos;
	}

	public List<TipoContacto> getTipoContactos() {
		return tipoContactos;
	}

	public TipoContactoNegocio getTipoContactosNegocio() {
		return tipoContactoNegocio;
	}

	public void setTipoContactoNegocio(TipoContactoNegocio tipoContactoNegocio) {
		this.tipoContactoNegocio = tipoContactoNegocio;
	}

	public Integer getIdUsuarioSel() {
		return idUsuarioSel;
	}

	public void setIdUsuarioSel(Integer idUsuarioSel) {
		this.idUsuarioSel = idUsuarioSel;
	}

	public ContactoNegocio getContactoNegocio() {
		return contactoNegocio;
	}

	public void setContactoNegocio(ContactoNegocio contactoNegocio) {
		this.contactoNegocio = contactoNegocio;
	}

	public TipoContactoNegocio getTipoContactoNegocio() {
		return tipoContactoNegocio;
	}
}
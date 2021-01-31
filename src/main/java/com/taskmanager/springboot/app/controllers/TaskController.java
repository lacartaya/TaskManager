package com.taskmanager.springboot.app.controllers;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.taskmanager.springboot.app.models.entity.Task;
import com.taskmanager.springboot.app.util.StatusEnum;
import com.taskmanager.springboot.app.models.service.ITaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taskmanager.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("task")
public class TaskController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private MessageSource messageSource;



	@RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}	
		
		Pageable pageRequest = PageRequest.of(page, 10);

		Page<Task> tasks = taskService.findAll(pageRequest);

		PageRender<Task> pageRender = new PageRender<Task>("/listar", tasks);
		model.addAttribute("titulo", messageSource.getMessage("text.task.listar.titulo", null, locale));
		model.addAttribute("tasks", tasks);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model, Locale locale) {

		Task task = new Task();
		model.put("task", task);
		model.put("titulo", messageSource.getMessage("text.task.form.titulo.crear", null, locale));
		return "form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Task task = null;

		if (id > 0) {
			task = taskService.findOne(id);
			if (task == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.task.flash.db.error", null, locale));
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.task.flash.id.error", null, locale));
			return "redirect:/listar";
		}
		model.put("task", task);
		model.put("titulo", messageSource.getMessage("text.task.form.titulo.editar", null, locale));
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Task task, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.task.form.titulo", null, locale));
			return "form";
		}

		String mensajeFlash = (task.getId() != null) ? messageSource.getMessage("text.task.flash.editar.success", null, locale) : messageSource.getMessage("text.task.flash.crear.success", null, locale);

		taskService.save(task);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/editarCheck", method = RequestMethod.POST)
	public String editarCheck(@RequestParam(name = "id") Long id) {
		Task task = null;
		if (id > 0) {
			task = taskService.findOne(id);
			if(task.getCompleted().equalsIgnoreCase("0"))
				task.setCompleted(StatusEnum.COMPLETED.getCodigo());
			else
				task.setCompleted(StatusEnum.UNCOMPLETED.getCodigo());
		}
		taskService.save(task);

		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		if (id > 0) {
			Task task = taskService.findOne(id);

			taskService.delete(id);
			flash.addFlashAttribute("success", messageSource.getMessage("text.task.flash.eliminar.success", null, locale));


		}
		return "redirect:/listar";
	}
	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
	}
}

package com.github.luksrn.noticias;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.luksrn.util.PageWrapper;

@Controller
public class ListaNoticiaController {
	
	@Inject
	NoticiaRepository noticiasRepository;

	@RequestMapping(value="/noticias")
	public String listarNoticias( Model uiModel  , Pageable pageable){
		
		Page<Noticia> noticias = noticiasRepository.findAll(pageable);		
		PageWrapper<Noticia> page = new PageWrapper<Noticia>( noticias , "/noticias");
		
		uiModel.addAttribute("noticiaList", noticias.getContent() );
		uiModel.addAttribute("pageNoticiaList", page);

		return "noticias/lista_noticias";
	}
}

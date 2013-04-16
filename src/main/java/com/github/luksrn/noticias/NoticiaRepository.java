package com.github.luksrn.noticias;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepository extends PagingAndSortingRepository<Noticia, Long> {

	 Page<Noticia> findAll( Pageable pageable );
}

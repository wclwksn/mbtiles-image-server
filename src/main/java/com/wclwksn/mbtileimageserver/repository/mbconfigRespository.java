package com.wclwksn.mbtileimageserver.repository;
 
import org.springframework.data.repository.CrudRepository;

import com.wclwksn.mbtileimageserver.model.mbconfig;
 

public interface  mbconfigRespository extends CrudRepository<mbconfig, Long> { 
	  mbconfig findById(long id);
}

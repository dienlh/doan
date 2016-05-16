package com.hotel.app.service.impl;

import com.hotel.app.service.ImagesService;
import com.hotel.app.domain.Images;
import com.hotel.app.repository.ImagesRepository;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Images.
 */
@Service
@Transactional
public class ImagesServiceImpl implements ImagesService{

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);
    
    @Inject
    private ImagesRepository imagesRepository;
    
    /**
     * Save a images.
     * @return the persisted entity
     */
    public Images save(Images images) {
        log.debug("Request to save Images : {}", images);
        Images result = imagesRepository.save(images);
        return result;
    }

    /**
     *  get all the imagess.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Images> findAll(Pageable pageable) {
        log.debug("Request to get all Imagess");
        Page<Images> result = imagesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one images by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Images findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        Images images = imagesRepository.findOne(id);
        return images;
    }

    /**
     *  delete the  images by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.delete(id);
    }
    
    @Override
    public String writeFileImage(MultipartFile file) throws IOException {
    	String fileName = DateTime.now().getMillis() + file.getOriginalFilename();
    	byte[] bytes = file.getBytes();
		BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(
				new File("E:/apache-tomcat-8.0.32/webapps/hotel/src/main/webapp/upload/" + fileName)));
		buffStream.write(bytes);
		buffStream.close();
		return fileName;
    }
    @Override
    public HashSet<Images> saveFileImages(HashSet<Images> hashSet,MultipartFile[] files) {
    	String fileName = null;
    	for (int i = 0; i < files.length; i++) {
			try {
//				fileName = DateTime.now().getMillis() + files[i].getOriginalFilename();
//				byte[] bytes = files[i].getBytes();
//				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(
//						new File("D:/Leature/doantotnghiep/source/hotel/src/main/webapp/upload/" + fileName)));
//				buffStream.write(bytes);
//				buffStream.close();
				fileName = writeFileImage(files[i]);
				
				Images images = new Images();
				images.setUrl("upload/" + fileName);
				images.setCreate_date(ZonedDateTime.now());

				Images images2 = save(images);
				hashSet.add(images2);
			} catch (Exception e) {
				return null;
			}
		}
    	return hashSet;
    }
}

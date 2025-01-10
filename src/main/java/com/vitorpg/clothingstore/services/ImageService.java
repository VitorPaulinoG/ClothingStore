package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.exceptions.EntityNotFoundException;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.repositories.ImageDao;

import java.util.List;

public class ImageService {
    private final ImageDao dao;
    public ImageService () {
        this.dao = new ImageDao();
    }

    public Image findById (Long id) {
        Image image = this.dao.findById(id);
        if(image == null)
            throw new EntityNotFoundException("Entity not found by id = " + id);
        return image;
    }

    public List<Image> getAllByProductId(Long productId) {
        return this.dao.getAllByProductId(productId);
    }

//    public byte[] convertImageToBytes(Image image, String format) {
//        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(bufferedImage, format, outputStream);
//            return outputStream.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void addToProduct(Image image, Long productId) {
        this.dao.addToProduct(image, productId);
    }

    public void update(Long id, Image image) {
        this.dao.update(id, image);
    }

    public void delete(Long id) {
        this.dao.delete(id);
    }
}

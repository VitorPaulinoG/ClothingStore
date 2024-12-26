package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.interfaces.EntityNotFoundException;
import com.vitorpg.clothingstore.repositories.ImageDao;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ImageService {
    private final ImageDao dao;
    public ImageService (ImageDao dao) {
        this.dao = dao;
    }

    public ByteArrayInputStream findById (Long id) {
        byte[] bytes = this.dao.findById(id);
        if(bytes == null)
            throw new EntityNotFoundException("Entity not found by id = " + id);
        return new ByteArrayInputStream(bytes);
    }

    public List<ByteArrayInputStream> getAllByProductId(Long productId) {
        return this.dao.getAllByProductId(productId).stream().map(x -> new ByteArrayInputStream(x)).toList();
    }

    public byte[] convertImageToBytes(Image image, String format) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addToProduct(Image image, String format, Long productId) {
        this.dao.addToProduct(convertImageToBytes(image, format), productId);
    }

    public void update(Long id, Image image, String format) {
        this.dao.update(id, convertImageToBytes(image, format));
    }

    public void delete(Long id) {
        this.dao.delete(id);
    }
}

package io.github.weiijunfu.poi.entity;

import org.apache.poi.common.usermodel.PictureType;

import java.io.InputStream;

/**
 *
 * @title  : 
 * @author : ijunfu <ijunfu@163.com>
 * @date   : 2024/6/28 21:13
 * @version: 1.0
 * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
public class Image {

    private String name;

    private InputStream imageData;

    private PictureType type;

    private int width;

    private int height;

    public Image() {
    }

    public Image(String name, InputStream imageData, PictureType type, int width, int height) {
        this.name = name;
        this.imageData = imageData;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getImageData() {
        return imageData;
    }

    public void setImageData(InputStream imageData) {
        this.imageData = imageData;
    }

    public PictureType getType() {
        return type;
    }

    public void setType(PictureType type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", imageData=" + imageData +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

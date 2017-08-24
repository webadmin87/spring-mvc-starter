package ru.rzncenter.webcore.web;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import ru.rzncenter.webcore.domains.FileDomain;
import ru.rzncenter.webcore.domains.Previews;
import ru.rzncenter.webcore.utils.FileUtils;
import org.imgscalr.Scalr.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;


/**
 * Компонент ресайза изображений
 */
@Component
public class ResizerImpl implements Resizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResizerImpl.class);

    @Autowired
    private FileUtils fileUtils;

    private String dirName = "thumbs";

    /**
     * Возвращает имя уменьшенного изображения
     * @param file
     * @param width
     * @param height
     * @return
     */
    public String getThumbName(File file, int width, int height) {
        String name = file.getAbsolutePath() + "_" + file.lastModified() + "_" + width + "_" + height;
        String ext = fileUtils.getExtension(file.getAbsolutePath());
        String md5Name = DigestUtils.md5Hex(name);
        if(ext.length()>0) {
            md5Name += "." + ext;
        }
        return md5Name;
    }

    /**
     * Возвращает путь к уменьшенному изображению
     * @param file
     * @param width
     * @param height
     * @return
     */
    public String getThumbPath(File file, int width, int height) {
        String dir = fileUtils.getServerPath() + "/" + dirName;
        File dirFile = new File(dir);
        if(!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String thumbName = getThumbName(file, width, height);
        String thumbPath = dir + "/" + thumbName;
        return thumbPath;
    }


    /**
     * Уменьшет изображение
     * @param file
     * @param width
     * @param height
     * @return путь к изображению относительно веб сервера
     */
    public String resize(File file, int width, int height) {
        if(!file.exists() || !fileUtils.isImage(file)) {
            return "";
        }
        try {
            String thumbPath = getThumbPath(file, width, height);
            File thumb = new File(thumbPath);
            if (!thumb.exists()) {
                resizeInternal(file, thumbPath, width, height);
            }
            return fileUtils.serverToWebPath(thumbPath);
        } catch (Exception e) {
            LOGGER.error("Resize error", e);
            return "";
        }
    }

    public String resize(File file, int width) {
        return resize(file, width, 0);
    }

    /**
     * Ресайз изображений
     * @param file
     * @param savePath
     * @param width
     * @param height
     * @return
     */
    protected boolean resizeInternal(File file, String savePath, int width, int height) {
        if(width == 0 && height == 0) {
            return false;
        }
        try {
            BufferedImage inp = ImageIO.read(file);
            BufferedImage out;
            if(width == 0) {
                out = Scalr.resize(inp, Method.AUTOMATIC, Mode.FIT_TO_HEIGHT, height);
            } else if(height == 0) {
                out = Scalr.resize(inp, Method.AUTOMATIC, Mode.FIT_TO_WIDTH, width);
            } else {
                // Делаем ресайз до указанной пропорции
                double prop = width*1.0 / height;
                int originWidth = inp.getWidth();
                int originHeight = inp.getHeight();
                int cropHeight = (int) (originWidth / prop);
                int cropWidth;
                if (cropHeight > originHeight) {
                    cropWidth = (int) (originHeight * prop);
                    cropHeight = originHeight;
                } else {
                    cropWidth = originWidth;
                }
                int x = (originWidth - cropWidth) / 2;
                int y = (originHeight - cropHeight) / 2;
                BufferedImage croped = Scalr.crop(inp, x, y, cropWidth, cropHeight);
                out = Scalr.resize(croped, Mode.AUTOMATIC, width, height);
            }
            Scalr.resize(inp, width);
            String ext = fileUtils.getExtension(savePath).toLowerCase();
            ImageIO.write(out, ext, new File(savePath));
            return true;
        } catch (Exception e) {
            LOGGER.error("Internal resize error", e);
            return false;
        }
    }

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public void setFileUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public void resize(Previews model) {
        Set<? extends FileDomain> previews = model.getPreviews();
        if(previews == null) {
            return;
        }
        SortedSet<FileDomain> resized = new TreeSet<>();
        for(FileDomain domain: previews) {
            String resizedPath = resize(new File(fileUtils.webToServerPath(domain.getPath())), model.previewWidth(), model.previewHeight());
            if(resizedPath != null && resizedPath.length()>0) {
                FileDomain resizedDomain = new FileDomain();
                resizedDomain.setPath(resizedPath);
                resizedDomain.setTitle(domain.getTitle());
                resizedDomain.setSort(domain.getSort());
                resized.add(resizedDomain);
            }
        }
        model.setPreviews(resized);
    }

    @Override
    public void resize(List models) {
        for(Object model : models) {
            if(model instanceof Previews) {
                resize((Previews) model);
            }
        }
    }
}

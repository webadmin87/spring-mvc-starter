package ru.rzncenter.webcore.web;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import ru.rzncenter.webcore.domains.Previews;
import ru.rzncenter.webcore.utils.FileUtils;
import org.imgscalr.Scalr.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Компонент ресайза изображений
 */
@Component
public class ResizerImpl implements Resizer {

    @Autowired
    FileUtils fileUtils;

    String dirName = "thumbs";

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

        if(!file.exists() || !fileUtils.isImage(file))
            return "";

        try {
            String thumbPath = getThumbPath(file, width, height);

            File thumb = new File(thumbPath);

            if (!thumb.exists()) {

                resizeInternal(file, thumbPath, width, height);

            }

            return fileUtils.serverToWebPath(thumbPath);
        } catch (Exception e) {
            e.printStackTrace();
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

                int cropHeight = (int) Math.floor(originWidth / prop);

                int cropWidth = 0;

                if (cropHeight > originHeight) {
                    cropWidth = (int) Math.floor(originHeight * prop);
                }

                if (cropWidth != 0) {
                    cropHeight = (int) Math.floor(cropWidth / prop);
                } else {
                    cropWidth = (int) Math.floor(cropHeight * prop);
                }

                int x = (int) Math.floor((originWidth - cropWidth) / 2);

                int y = (int) Math.floor((originHeight - cropHeight) / 2);


                BufferedImage croped = Scalr.crop(inp, x, y, cropWidth, cropHeight);

                out = Scalr.resize(croped, Mode.AUTOMATIC, width, height);

            }

            Scalr.resize(inp, width);

            String ext = fileUtils.getExtension(savePath).toLowerCase();

            ImageIO.write(out, ext, new File(savePath));

            return true;

        } catch (Exception e) {

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

        Map<Integer, String> previews = model.getPreviews();

        if(previews == null)
            return;

        SortedMap<Integer, String> resized = new TreeMap<>();

        for(Map.Entry<Integer, String> entry : previews.entrySet()) {

            String resizedPath = resize(new File(fileUtils.webToServerPath(entry.getValue())), model.previewWidth(), model.previewHeight());

            if(resizedPath != null && resizedPath.length()>0) {

                resized.put(entry.getKey(), resizedPath);

            }

        }

        model.setPreviews(resized);


    }

    @Override
    public void resize(List models) {

        for(Object model : models) {

            if(model instanceof Previews)
                resize((Previews) model);
        }

    }
}

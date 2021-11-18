package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
* 定义地面类
* */
public class ground {
//    图片
    BufferedImage image;
//    位置
    int x, y;
//    宽高
    int width, height;

//    初始化地面
    public ground() throws Exception{
        image = ImageIO.read(getClass().getResource("/resources/ground.png"));
        width = image.getWidth();
        height = image.getHeight();
        x = 0;
        y = 500;

    }

//    向左移动一步
    public void step(){
        x--;
        if (x == -109){
            x = 0;
        }
    }

}

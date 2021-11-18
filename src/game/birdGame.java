package game;

import sun.awt.image.BufferedImageDevice;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/*
* 游戏界面
* */

public class birdGame extends JPanel {

//    背景
    BufferedImage background;
//    开始图片
    BufferedImage startImage;
//    结束图片
    BufferedImage gameOverImage;

//    地面
    ground Ground;
//    柱子
    colum Colum1, Colum2;
//    小鸟
    bird Bird;

//    游戏分数
    int score;

//    游戏状态
    int state;
//    状态常量
    public static final int START = 0; //开始
    public static final int RUNNING = 1; //运行
    public static final int GAME_OVER = 2; //结束

    /*
    * 初始化游戏
    * */
    public birdGame() throws Exception{
//        初始化背景图片
        background = ImageIO.read(getClass().
                getResource("/resources/bg.png"));
//        初始化开始图片
          startImage = ImageIO.read(getClass().
                  getResource("/resources/start.png"));
//          初始化游戏结束图片
          gameOverImage = ImageIO.read(getClass().
                  getResource("/resources/gameover.png"));

//          初始化地面,柱子,小鸟
        Ground = new ground();
        Colum1 = new colum(1);
        Colum2 = new colum(2);
        Bird = new bird();

//        初始化分数
        score = 0;

//        初始化状态
        state = START;
    }



    /*
    * 绘制界面
    * */
    public void paint(Graphics g) {
//        绘制背景
        g.drawImage(background,0, 0,null);

//        绘制地面
        g.drawImage(Ground.image, Ground.x, Ground.y, null);

//        绘制柱子
        g.drawImage(Colum1.image, Colum1.x - Colum1.width / 2,
                Colum1.y - Colum1.height / 2, null);
        g.drawImage(Colum2.image, Colum2.x - Colum2.width / 2,
                Colum2.y - Colum2.height / 2, null);

//        绘制小鸟(旋转坐标系)
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-Bird.alpha, Bird.x, Bird.y);
        g.drawImage(Bird.image, Bird.x - Bird.width / 2,
                Bird.y - Bird.height / 2, null);
        g2.rotate(Bird.alpha, Bird.x, Bird.y);

//        绘制分数
        Font f = new Font(Font.SANS_SERIF, Font.BOLD,40);
        g.setFont(f);
        g.drawString("" + score, 40, 60);
        g.setColor(Color.WHITE);
        g.drawString("" + score, 40 - 3, 60 -3);

//        绘制开始与结束界面
        switch (state) {
            case START:
                g.drawImage(startImage, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameOverImage, 0, 0, null);
                break;
        }

    }

    /**
     * 开始游戏
     *
     */
    public void action() throws Exception{
        //        鼠标监听器
        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
//                    针对不同状态时鼠标按下的反应
                    switch (state) {
                        case START:
//                            在开始状态,则转入运行状态
                            state = RUNNING;
                            break;
                        case RUNNING:
//                            在运行状态,则小鸟向上飞行
                            Bird.flyppy();
                            break;
                        case GAME_OVER:
//                            在结束状态,按下鼠标再次转化为开始状态
                            Colum1 = new colum(1);
                            Colum2 = new colum(2);
                            Bird = new bird();
                            score = 0;
                            state = START;
                            break;


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

//        将鼠标监听器添加到当前的面板上
        addMouseListener(l);

//        不断移动与重绘
        while(true) {

            switch (state) {
                case  START:
//                    小鸟做出飞行动作
                    Bird.fly();
//                    地面向左移动一步
                    break;
                case RUNNING:
//                    地面向左移动一步
                    Ground.step();
//                    柱子向左移动一步
                    Colum1.step();
                    Colum2.step();
//                    小鸟做出飞行动作
                    Bird.fly();
//                    小鸟上下移动一步
                    Bird.step();
//                    计算分数
                   if (Bird.x == Colum1.x || Bird.x == Colum2.x) {
                       score ++;
                   }
//                   检测是否发生碰撞
                    if (Bird.hit(Ground) || Bird.hit(Colum1) || Bird.hit(Colum2)){
                        state = GAME_OVER;
                    }
                    break;
            }
//            重新绘制界面
            repaint();
//            休眠 1000/60 毫秒
            Thread.sleep(1000 / 60);
        }




    }




    /*
    * 启动方法
    * */
    public static void main(String[] args) throws Exception{
        JFrame frame = new JFrame();
        birdGame game = new birdGame();
        frame.add(game);
        frame.setSize(440,670);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.action();
    }

}

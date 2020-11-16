package com.xuegao.xuegaololskills;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.DefaultDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class XuegaoLolSkillsApplication {
    private static JTextField topTime;
    private static Boolean topTimeFlag = false;
    private static AtomicInteger topTime_Seconds_60 = new AtomicInteger();

    private static JTextField jugTime;
    private static Boolean jugTimeFlag = false;
    private static AtomicInteger jugTime_Seconds_60 = new AtomicInteger();

    private static JTextField midTime;
    private static Boolean midTimeFlag = false;
    private static AtomicInteger midTime_Seconds_60 = new AtomicInteger();

    private static JTextField adcTime;
    private static Boolean adcTimeFlag = false;
    private static AtomicInteger adcTime_Seconds_60 = new AtomicInteger();

    private static JTextField supTime;
    private static Boolean supTimeFlag = false;
    private static AtomicInteger supTime_Seconds_60 = new AtomicInteger();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
        SpringApplication.run(XuegaoLolSkillsApplication.class, args);
    }

    private static void createAndShowGUI() {
        // 创建 JFrame 实例
        final JFrame frame = new JFrame("LOL");

        // 设置 frame 窗口的整体大小
        frame.setSize(200, 400);
        // 点击 X 进程直接关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 获得一个对象
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // 设置frame再最中央显示
        frame.setLocation((dimension.width - frame.getWidth()) / 2, (dimension.height - frame.getHeight()) / 2);
        // 创建面板，这个类似于 HTML 的 div 标签
        JPanel panel = new JPanel();
        // 设置内容面板
        frame.setContentPane(panel);

        // 调用用户定义的方法并添加组件到面板
        addDisplay(panel);
        addBeginButton(panel);
        addRestButton(panel);
        addLicense(panel);

        // 报错
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        // 设置 frame 可见
        frame.setVisible(true);

        // 屏蔽最大化
        frame.setResizable(false);
        // 禁用关闭
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.NORMAL);
        // 设置 frame 在最上层显示
        frame.setAlwaysOnTop(true);


        // 开启线程去处理 全局按键监听事件
        new Runnable() {
            @Override
            public void run() {
                new TabKeyListener(frame);
            }
        }.run();

        // 时间递减
        setTimer();
    }

    private static void addDisplay(JPanel panel) {
        // 布局部分我们这边不多做介绍
        // 这边设置布局为 null
        panel.setLayout(null);
        panel.setFocusable(true);

        // 创建 上单
        JLabel userLabel = new JLabel("top");
        // setBounds(x, y, width, heig  ht)
        // x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        // 创建文本域用于用户输入
        topTime = new JTextField("5:00");
        topTime.setBounds(40, 20, 35, 25);
        // 添加键盘监听事件
        panel.add(topTime);

        // 创建 打野
        JLabel userLabel2 = new JLabel("jug");
        // setBounds(x, y, width, heig  ht)
        // x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
        userLabel2.setBounds(10, 45, 80, 25);
        panel.add(userLabel2);
        // 创建文本域用于用户输入
        jugTime = new JTextField("5:00");
        jugTime.setBounds(40, 45, 35, 25);
        // 添加键盘监听事件
        panel.add(jugTime);

        // 创建 中单
        JLabel userLabel3 = new JLabel("mid");
        // setBounds(x, y, width, heig  ht)
        // x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
        userLabel3.setBounds(10, 70, 80, 25);
        panel.add(userLabel3);
        // 创建文本域用于用户输入
        midTime = new JTextField("5:00");
        midTime.setBounds(40, 70, 35, 25);
        // 添加键盘监听事件
        panel.add(midTime);

        // 创建 ADC
        JLabel userLabel4 = new JLabel("adc");
        // setBounds(x, y, width, heig  ht)
        // x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
        userLabel4.setBounds(10, 95, 80, 25);
        panel.add(userLabel4);
        // 创建文本域用于用户输入
        adcTime = new JTextField("5:00");
        adcTime.setBounds(40, 95, 35, 25);
        // 添加键盘监听事件
        panel.add(adcTime);

        // 创建 辅助
        JLabel userLabel5 = new JLabel("sup");
        // setBounds(x, y, width, heig  ht)
        // x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
        userLabel5.setBounds(10, 120, 80, 25);
        panel.add(userLabel5);
        // 创建文本域用于用户输入
        supTime = new JTextField("5:00");
        supTime.setBounds(40, 120, 35, 25);
        // 添加键盘监听事件
        panel.add(supTime);

    }

    private static void addLicense(JPanel panel) {
        // 创建登录按钮
        JButton systemButton = new JButton("LOL闪现计时工具-关闭");
        systemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        systemButton.setBounds(10, 200, 180, 25);
        panel.add(systemButton);
        // 创建文本域用于用户输入
        JTextField version = new JTextField(20);
        version.setText("版本：V1.0.0");
        version.setBounds(10, 225, 180, 25);
        // 添加键盘监听事件
        panel.add(version);
        // 创建文本域用于用户输入
        JTextField github = new JTextField("5:00");
        github.setText("GitHub：WarriorFromLongAgo");
        github.setBounds(10, 250, 180, 25);
        // 添加键盘监听事件
        panel.add(github);
    }

    private static void addBeginButton(JPanel panel) {
        // top
        JButton top = new JButton("开始");
        top.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topTimeFlag = true;
                // String topTimeText = topTime.getText();
                // String[] split = topTimeText.split(":");
                // int topTimeInt = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
                // System.out.println(topTimeInt);
            }
        });
        top.setBounds(70, 20, 65, 25);
        panel.add(top);

        // jug
        JButton jug = new JButton("开始");
        jug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugTimeFlag = true;
            }
        });
        jug.setBounds(70, 45, 65, 25);
        panel.add(jug);

        // mid
        JButton mid = new JButton("开始");
        mid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                midTimeFlag = true;
            }
        });
        mid.setBounds(70, 70, 65, 25);
        panel.add(mid);

        // adc
        JButton adc = new JButton("开始");
        adc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adcTimeFlag = true;
            }
        });
        adc.setBounds(70, 95, 65, 25);
        panel.add(adc);

        // sup
        JButton sup = new JButton("开始");
        sup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supTimeFlag = true;
            }
        });
        sup.setBounds(70, 120, 65, 25);
        panel.add(sup);
    }

    private static void addRestButton(final JPanel panel) {
        // top
        JButton top = new JButton("重置");
        top.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topTimeFlag = false;
                topTime.setText("5:00");
            }
        });
        top.setBounds(130, 20, 65, 25);
        panel.add(top);

        // jug
        JButton jug = new JButton("重置");
        jug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugTimeFlag = false;
                jugTime.setText("5:00");
            }
        });
        jug.setBounds(130, 45, 65, 25);
        panel.add(jug);

        // mid
        JButton mid = new JButton("重置");
        mid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                midTimeFlag = false;
                midTime.setText("5:00");
            }
        });
        mid.setBounds(130, 70, 65, 25);
        panel.add(mid);

        // adc
        JButton adc = new JButton("重置");
        adc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adcTimeFlag = false;
                adcTime.setText("5:00");
            }
        });
        adc.setBounds(130, 95, 65, 25);
        panel.add(adc);

        // sup
        JButton sup = new JButton("重置");
        top.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supTimeFlag = false;
                supTime.setText("5:00");
            }
        });
        sup.setBounds(130, 120, 65, 25);
        panel.add(sup);
    }

    public void checkTime() {


    }

    //设置Timer 1000ms实现一次动作 实际是一个线程
    private static void setTimer() {
        Timer timeAction = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsTimeDec();
            }
        });
        timeAction.start();
    }

    // 分时间递减
    public static void secondsTimeDec() {
        // TimeUnit.MINUTES
        if (topTimeFlag) {
            String topTimeText = topTime.getText();
            String[] split = topTimeText.split(":");
            Integer minutes = Integer.valueOf(split[0]);
            Integer seconds = Integer.valueOf(split[1]);
            if (minutes == 0 && seconds == 0) {
                topTimeFlag = false;
                topTime.setText("5:00");
                return;
            }
            if (seconds >= 1) {
                seconds--;
            }
            int timeMinutes = topTime_Seconds_60.incrementAndGet();
            if (timeMinutes >= 60 || seconds == 0) {
                if (minutes >= 1) {
                    minutes--;
                    seconds = 60;
                }
                topTime_Seconds_60.set(0);
                topTime.setText(minutes + ":" + seconds);
            } else {
                if (seconds < 10) {
                    topTime.setText(split[0] + ":0" + seconds);
                } else {
                    topTime.setText(split[0] + ":" + seconds);
                }
            }
        }
        if (jugTimeFlag) {
            String jugTimeText = jugTime.getText();
            String[] split = jugTimeText.split(":");
            Integer minutes = Integer.valueOf(split[0]);
            Integer seconds = Integer.valueOf(split[1]);
            if (minutes == 0 && seconds == 0) {
                jugTimeFlag = false;
                jugTime.setText("5:00");
                return;
            }
            if (seconds >= 1) {
                seconds--;
            }
            int timeMinutes = jugTime_Seconds_60.incrementAndGet();
            if (timeMinutes >= 60 || seconds == 0) {
                if (minutes >= 1) {
                    minutes--;
                    seconds = 60;
                }
                jugTime_Seconds_60.set(0);
                jugTime.setText(minutes + ":" + seconds);
            } else {
                if (seconds < 10) {
                    jugTime.setText(split[0] + ":0" + seconds);
                } else {
                    jugTime.setText(split[0] + ":" + seconds);
                }
            }
        }
        if (midTimeFlag) {
            String midTimeText = midTime.getText();
            String[] split = midTimeText.split(":");
            Integer minutes = Integer.valueOf(split[0]);
            Integer seconds = Integer.valueOf(split[1]);
            if (minutes == 0 && seconds == 0) {
                midTimeFlag = false;
                midTime.setText("5:00");
                return;
            }
            if (seconds >= 1) {
                seconds--;
            }
            int timeMinutes = midTime_Seconds_60.incrementAndGet();
            if (timeMinutes >= 60 || seconds == 0) {
                if (minutes >= 1) {
                    minutes--;
                    seconds = 60;
                }
                midTime_Seconds_60.set(0);
                midTime.setText(minutes + ":" + seconds);
            } else {
                if (seconds < 10) {
                    midTime.setText(split[0] + ":0" + seconds);
                } else {
                    midTime.setText(split[0] + ":" + seconds);
                }
            }
        }
        if (adcTimeFlag) {
            String adcTimeText = adcTime.getText();
            String[] split = adcTimeText.split(":");
            Integer minutes = Integer.valueOf(split[0]);
            Integer seconds = Integer.valueOf(split[1]);
            if (minutes == 0 && seconds == 0) {
                adcTimeFlag = false;
                adcTime.setText("5:00");
                return;
            }
            if (seconds >= 1) {
                seconds--;
            }
            int timeMinutes = adcTime_Seconds_60.incrementAndGet();
            if (timeMinutes >= 60 || seconds == 0) {
                if (minutes >= 1) {
                    minutes--;
                    seconds = 60;
                }
                adcTime_Seconds_60.set(0);
                adcTime.setText(minutes + ":" + seconds);
            } else {
                if (seconds < 10) {
                    adcTime.setText(split[0] + ":0" + seconds);
                } else {
                    adcTime.setText(split[0] + ":" + seconds);
                }
            }
        }
        if (supTimeFlag) {
            String supTimeText = supTime.getText();
            String[] split = supTimeText.split(":");
            Integer minutes = Integer.valueOf(split[0]);
            Integer seconds = Integer.valueOf(split[1]);
            if (minutes == 0 && seconds == 0) {
                supTimeFlag = false;
                supTime.setText("5:00");
                return;
            }
            if (seconds >= 1) {
                seconds--;
            }
            int timeMinutes = supTime_Seconds_60.incrementAndGet();
            if (timeMinutes >= 60 || seconds == 0) {
                if (minutes >= 1) {
                    minutes--;
                    seconds = 60;
                }
                supTime_Seconds_60.set(0);
                supTime.setText(minutes + ":" + seconds);
            } else {
                if (seconds < 10) {
                    supTime.setText(split[0] + ":0" + seconds);
                } else {
                    supTime.setText(split[0] + ":" + seconds);
                }
            }
        }
    }
}

class TabKeyListener implements NativeKeyListener {
    private JFrame frame;
    private static final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public TabKeyListener(JFrame frame) {
        this.frame = frame;
        //关闭日志
        logger.setLevel(Level.OFF);
        //添加监听调度员，如果是Swing程序，就用SwingDispatchService，不是就用默认的
        GlobalScreen.setEventDispatcher(new DefaultDispatchService());
        try {
            //注册监听
            GlobalScreen.registerNativeHook();
            //加入键盘监听，
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    // pressed = NATIVE_KEY_PRESSED,keyCode=15,keyText=Tab,keyChar=未定义,keyLocation=KEY_LOCATION_STANDARD,rawCode=9
    // released = NATIVE_KEY_RELEASED,keyCode=15,keyText=Tab,keyChar=未定义,keyLocation=KEY_LOCATION_STANDARD,rawCode=9
    //实现键盘监听的三个方法，根据自己情况实现
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        System.out.println("typed = " + nativeEvent.paramString());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        boolean flag = splitAndJudgeTab(nativeEvent.paramString());
        if (flag) {
            // 设置窗口正常大小
            frame.setExtendedState(JFrame.NORMAL);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        boolean flag = splitAndJudgeTab(nativeEvent.paramString());
        if (flag) {
            // 设置窗口最小化
            frame.setExtendedState(JFrame.ICONIFIED);
        }
    }

    public boolean splitAndJudgeTab(String paramString) {
        String[] split = paramString.split(",");
        if (split.length < 6) {
            System.exit(1);
        }
        // keyCode=15
        String[] split1 = split[1].split("=");

        // keyText=Tab
        String[] split2 = split[2].split("=");
        System.out.println(split1[1] + "===" + split2[1]);

        if (split1[1].equalsIgnoreCase("15") && split2[1].equalsIgnoreCase("Tab")) {
            return true;
        }
        return false;
    }

}
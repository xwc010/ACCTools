package com.autotest.acctools.Serial;

/**
 * Created by xwc on 2017/11/6.
 */

public class TimeOutTest {
    static String[] shareObj = { "true" };
    public static void main(String[] args){

        doThings(new CallBack(){
            @Override
            public void succ() {
                System.out.println("Succ Back!");
                synchronized (shareObj) {
                    shareObj[0] = "false";
                    shareObj.notifyAll();
                }
            }
        });

        action();
    }

    public static void action(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (shareObj) {
                    while ("true".equals(shareObj[0])) {
                        try {
                            shareObj.wait(13000);
                            if("true".equals(shareObj[0])){
                                System.out.println("timeout!");
                            }
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }



    static interface CallBack{
        public void succ();
    }

    static interface TimeOut{
        public void out();
    }

    public static void doThings(final CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("doThings run !");

                    Thread.sleep(1000*5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                callBack.succ();
            }
        }).start();
    }
}

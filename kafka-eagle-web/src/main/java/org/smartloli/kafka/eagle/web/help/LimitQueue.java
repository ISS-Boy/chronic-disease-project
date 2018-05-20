package org.smartloli.kafka.eagle.web.help;

import java.util.concurrent.LinkedBlockingDeque;

public class LimitQueue<E> extends LinkedBlockingDeque<E> {

  //重写入队
    @Override
    public boolean offer(E e){
        if(this.size() >9){
            //如果超出长度,入队时,先出队
            poll();
        }
        return super.offer(e);
    }


}

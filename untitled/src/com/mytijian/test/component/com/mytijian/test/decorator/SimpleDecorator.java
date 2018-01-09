package com.mytijian.test.component.com.mytijian.test.decorator;

import com.mytijian.test.component.TheGreateSage;

/**
 * 抽象装饰角色
 * created by panta on 2018/1/2.
 */
public class SimpleDecorator implements TheGreateSage {

    private TheGreateSage theGreateSage;

    public SimpleDecorator(TheGreateSage theGreateSage) {
        this.theGreateSage = theGreateSage;
    }

    @Override
    public void move() {
        theGreateSage.move();
    }
}

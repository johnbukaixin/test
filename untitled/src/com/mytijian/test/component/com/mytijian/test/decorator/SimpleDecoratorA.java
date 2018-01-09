package com.mytijian.test.component.com.mytijian.test.decorator;

import com.mytijian.test.component.TheGreateSage;

/**
 * 具体装饰角色A
 * created by panta on 2018/1/2.
 */
public class SimpleDecoratorA extends SimpleDecorator {

    public SimpleDecoratorA(TheGreateSage theGreateSage) {
        super(theGreateSage);
    }

    @Override
    public void move() {
        super.move();
        //todo:
        System.out.println("It A move");
    }
}

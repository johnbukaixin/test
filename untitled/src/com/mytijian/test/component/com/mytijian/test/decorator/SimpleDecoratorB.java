package com.mytijian.test.component.com.mytijian.test.decorator;

import com.mytijian.test.component.TheGreateSage;

/**
 * 具体装饰角色B
 * created by panta on 2018/1/2.
 */
public class SimpleDecoratorB extends SimpleDecorator {
    public SimpleDecoratorB(TheGreateSage theGreateSage) {
        super(theGreateSage);
    }

    @Override
    public void move() {
        super.move();
        //todo:

        System.out.println("It B move");
    }
}

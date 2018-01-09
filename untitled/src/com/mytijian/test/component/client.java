package com.mytijian.test.component;

import com.mytijian.test.component.GreateSageImpl.ConcreteComponent;
import com.mytijian.test.component.com.mytijian.test.decorator.SimpleDecoratorA;
import com.mytijian.test.component.com.mytijian.test.decorator.SimpleDecoratorB;

/**
 * created by panta on 2018/1/2.
 */
public class client {

    public static void main(String[] args) {
        TheGreateSage sage = new ConcreteComponent();

        TheGreateSage bird = new SimpleDecoratorA(sage);
        TheGreateSage fish = new SimpleDecoratorB(bird);
        fish.move();
    }
}

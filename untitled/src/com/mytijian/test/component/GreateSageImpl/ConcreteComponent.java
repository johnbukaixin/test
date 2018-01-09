package com.mytijian.test.component.GreateSageImpl;

import com.mytijian.test.component.TheGreateSage;

/**
 * 具体构建角色
 * created by panta on 2018/1/2.
 */
public class ConcreteComponent implements TheGreateSage {
    @Override
    public void move() {
        System.out.printf("原始本尊");
    }
}

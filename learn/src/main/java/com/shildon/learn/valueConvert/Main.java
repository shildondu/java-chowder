package com.shildon.learn.valueConvert;

/**
 * Java在调用方法传递参数的时候，会创建一个新的引用指向那个方法参数，
 * 所以，修改参数对象的field是可以在上层方法感知到，而重新对参数对象
 * 进行赋值只会在该方法中起作用。
 */
public class Main {

    private void changeValueInObject(Entry entry) {
        entry.setValue("after");
    }

    private void changeString(String value) {
        value = "after";
    }

    private void changeBoxedType(Integer value) {
        value = 2;
    }

    public static void main(String[] args) {
        Entry entry = new Entry();
        entry.setValue("before");
        new Main().changeValueInObject(entry);
        System.out.println(entry.getValue());   // after

        String value = "before";
        new Main().changeString(value);
        System.out.println(value);  // before

        Integer val = 1;
        new Main().changeBoxedType(val);
        System.out.println(val);    // 1
    }

}

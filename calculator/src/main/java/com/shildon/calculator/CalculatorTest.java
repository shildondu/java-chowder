package com.shildon.calculator;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * 计算器测试用例，这里放在同一个包下方便查看
 *
 * @author shildon
 * @since 1.0.0
 */
public class CalculatorTest {

    /**
     * 常规加减乘除用例
     */
    @Test
    public void testNormal() {
        double result = Calculator.of(1)
                .add(2)
                .add(3)
                .multiply(5)
                .subtract(4)
                .divide(20)
                .equals()
                .doubleValue();
        Assertions.assertEquals(17.8, result);
    }

    /**
     * undo用例
     */
    @Test
    public void testUndo() {
        double result = Calculator.of(12.3)
                .add(3)
                .multiply(14)
                .divide(6)
                .undo()
                .add(10)
                .equals()
                .doubleValue();
        Assertions.assertEquals(64.3, result);
    }

    /**
     * redo用例
     */
    @Test
    public void testRedo() {
        double result = Calculator.of(12.3)
                .add(3)
                .multiply(14)
                .divide(6)
                .undo()
                .redo()
                .add(10)
                .equals()
                .doubleValue();
        Assertions.assertEquals(29.3, result);
    }

    /**
     * 除以0的用例
     */
    @Test
    public void testDivide0() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                Calculator.of(1)
                        .divide(0)
                        .equals();
            }
        });
    }

}

package com.shildon.calculator;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 计算器，实现加减乘除功能，并支持优先级运算
 *
 * @author shildon
 * @since 1.0.0
 */
public final class Calculator {

    /**
     * 历史命令，使用双向链表存储，方便便利
     */
    private final List<Operation> histories;
    /**
     * undo的命令，使用栈存储，方便弹出
     */
    private final Stack<Operation> undoOps;

    private Calculator() {
        this.histories = new LinkedList<>();
        this.undoOps = new Stack<>();
    }

    /**
     * 构建计算器实例
     *
     * @param initialValue 初始值
     * @return 计算器实例
     */
    public static Calculator of(double initialValue) {
        Calculator calculator = new Calculator();
        calculator.histories.add(new Operation(Operator.ADD, BigDecimal.valueOf(initialValue)));
        return calculator;
    }

    /**
     * 加法
     *
     * @param num 加数
     * @return 计算器实例
     */
    public Calculator add(double num) {
        return this.operate(Operator.ADD, num);
    }

    /**
     * 减法
     *
     * @param num 减数
     * @return 计算器实例
     */
    public Calculator subtract(double num) {
        return this.operate(Operator.SUBTRACT, num);
    }

    /**
     * 乘法
     *
     * @param num 乘数
     * @return 计算器实例
     */
    public Calculator multiply(double num) {
        return this.operate(Operator.MULTIPLY, num);
    }

    /**
     * 除法
     *
     * @param num 除数
     * @return 计算器实例
     */
    public Calculator divide(double num) {
        if (num == 0) {
            throw new IllegalArgumentException("Cannot divide by 0!");
        }
        return this.operate(Operator.DIVIDE, num);
    }

    /**
     * 重做
     *
     * @return 计算器实例
     */
    public Calculator redo() {
        if (this.undoOps.isEmpty()) {
            return this;
        }
        Operation redoOp = this.undoOps.pop();
        this.histories.add(redoOp);
        return this;
    }

    /**
     * 撤销
     *
     * @return 计算器实例
     */
    public Calculator undo() {
        if (this.histories.isEmpty()) {
            return this;
        }
        Operation undoOp = this.histories.remove(this.histories.size() - 1);
        this.undoOps.add(undoOp);
        return this;
    }

    /**
     * 计算结果
     *
     * @return 结果值
     */
    public BigDecimal equals() {
        // 先计算乘法和除法
        for (int i = 0; i < this.histories.size(); i++) {
            Operation cur = this.histories.get(i);
            if (cur.isMultiOrDivide()) {
                Operation prev = this.histories.remove(i - 1);
                Operation merge = cur.merge(prev);
                this.histories.set(i - 1, merge);
                i = i - 1;
            }
        }
        // 再计算加法和减法
        for (int i = 1; i < this.histories.size(); i++) {
            Operation cur = this.histories.get(i);
            Operation prev = this.histories.remove(i - 1);
            Operation merge = cur.merge(prev);
            this.histories.set(i - 1, merge);
            i = i - 1;
        }
        return this.histories.get(0).getOperand();
    }

    private Calculator operate(Operator operator, double operand) {
        this.histories.add(new Operation(operator, BigDecimal.valueOf(operand)));
        this.undoOps.clear();
        return this;
    }

    /**
     * 操作命令
     */
    private static class Operation {
        /**
         * 操作符，包含加减乘除
         */
        private Operator operator;
        /**
         * 操作数
         */
        private BigDecimal operand;

        public Operation(Operator operator, BigDecimal operand) {
            this.operator = operator;
            this.operand = operand;
        }

        /**
         * 将上一个操作命令与本操作命令进行合并计算
         *
         * @param prev 上一个操作命令
         * @return 合并后的操作命令
         */
        public Operation merge(Operation prev) {
            switch (this.operator) {
                case ADD: {
                    return new Operation(prev.operator, prev.getOperand().add(this.getOperand()));
                }
                case SUBTRACT: {
                    return new Operation(prev.operator, prev.getOperand().subtract(this.getOperand()));
                }
                case MULTIPLY: {
                    return new Operation(prev.operator, prev.getOperand().multiply(this.getOperand()));
                }
                case DIVIDE: {
                    return new Operation(prev.operator, prev.getOperand().divide(this.getOperand()));
                }
                default: {
                    return null;
                }
            }
        }

        /**
         * 判断是否是乘法或除法
         *
         * @return 是否
         */
        public boolean isMultiOrDivide() {
            return this.operator == Operator.MULTIPLY || this.operator == Operator.DIVIDE;
        }

        public Operator getOperator() {
            return operator;
        }

        public void setOperator(Operator operator) {
            this.operator = operator;
        }

        public BigDecimal getOperand() {
            return operand;
        }

        public void setOperand(BigDecimal operand) {
            this.operand = operand;
        }
    }

    /**
     * 操作符号枚举
     */
    private enum Operator {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
    }

}

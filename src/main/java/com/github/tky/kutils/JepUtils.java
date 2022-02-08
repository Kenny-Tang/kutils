package com.github.tky.kutils;

import com.sun.tools.javac.util.StringUtils;
import org.apache.poi.util.StringUtil;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class JepUtils {

    //表示百分比的字段名后缀
    public static final String percentEnd= "Rate";

    //精度要求，几位小数
    private static final int jepScale = 4;

    //创建JEP实例
    public static JEP createInstance() {
        JEP jep = new JEP();
        jep.addStandardFunctions();
        jep.addFunction("max", new JepUtils.Max());
        return jep;
    }

    /**
     * 按计算参数、公式，结合输入参数进行计算，将结果放入结果集合.
     *
     * @param caculateCache 计算结果集合.
     * @param inputMap    (前端)输入参数集合.
     * @param paramMap    计算参数、公式集合.
     * @param paramName   具体的参数名称,paramMap里的一个key.
     */
    public static Object caculate(Map<String, Object> caculateCache, Map<String, Object> inputMap, Map<String, ParamDTO> paramMap, String paramName) {
        if (caculateCache.containsKey(paramName)) {
            //算完了，有值
            return caculateCache.get(paramName);
        }
        if (inputMap.containsKey(paramName)) {
            //输入参数里有
            caculateCache.put(paramName, inputMap.get(paramName));
            return caculateCache.get(paramName);
        }
        ParamDTO param = paramMap.get(paramName);
        if (param == null) {
            throw new RuntimeException("param not exist, name: " + paramName);
        }
        switch (param.type) {
            //参数类型。1,普通值；2，公式；3，待填字符串
            case 1:
                caculateCache.put(param.getName(), param.getValue());
                break;
            case 2:
            case 3:
                //公式 或 字符串
                String formula = ((Map<Object, Object> ) param.getValue()).keySet().toArray()[0].toString();
                //参数列表
                List<String> formulaParamList = (List<String>) ((Map<Object, Object>) param.getValue()).get(formula);
                for (String formulaParam : formulaParamList) {
                    //每个参数都去计算
                    caculate(caculateCache, inputMap, paramMap, formulaParam);
                }
                if (param.type == 2) {
                    //公式的计算
                    JEP jep = JepUtils.createInstance();
                    for (String formulaParam : formulaParamList) {
                        jep.addVariable(formulaParam, new BigDecimal(caculateCache.get(formulaParam).toString()).setScale(jepScale, RoundingMode.HALF_UP).doubleValue());
                    }
                    Object result = null;
                    try {
                        Node node = jep.parse(formula);
                        result = jep.evaluate(node);
                    } catch (ParseException ex) {
                        throw new RuntimeException("execute " + formula + " error!");
                    }
                    caculateCache.put(param.getName(), new BigDecimal(result.toString()).setScale(jepScale, RoundingMode.HALF_UP));
                } else {
                    //字符串的，去format
                    List<String> list = new ArrayList<>();
                    for (String formulaParam : formulaParamList) {
                        Object obj = caculateCache.get(formulaParam);
                        if(formulaParam.endsWith(percentEnd)){
                            //需要百分比的
                            if (obj instanceof BigDecimal) {
                                list.add(((BigDecimal) obj).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue() + "%");
                            } else if (obj instanceof Double) {
                                list.add(BigDecimal.valueOf((double)obj).multiply(BigDecimal.valueOf(100)).setScale( 2, RoundingMode.HALF_UP).doubleValue() + "%");
                            } else if (obj instanceof Integer) {
                                list.add(BigDecimal.valueOf((Integer)obj).multiply(BigDecimal.valueOf(100)).setScale( 2, RoundingMode.HALF_UP).doubleValue() + "%");
                            } else {
                                //分支闭环，其实不会走到这
                                list.add(obj.toString());
                            }
                            continue;
                        }
                        list.add(obj.toString()) ;
                    }
                    //计算结果放入结果集
                    caculateCache.put(param.getName(), String.format(formula, list.toArray()));
                }
                break;
            default:
                throw new RuntimeException("undefine param type, paramName: " + paramName);
        }
        return caculateCache.get(paramName);
    }

    public static class ParamDTO {
        //参数名称
        private String name;

        //参数描述
        private String desc;

        //参数类型。1,普通值；2，公式；3，待填字符串
        private Integer type;

        //参数值。根据类型判断，类型=1时，是数值，double；类型=2，是公式Map<公式str,参数名称列表>；类型=3，是字符串内容
        private Object value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class Max extends PostfixMathCommand {
        public Max() {
            super();
            // 使用参数的数量
            numberOfParameters = 2;
        }

        @Override
        public void run(Stack inStack) throws ParseException {
            //检查栈
            checkStack(inStack);
            Object param2 = inStack.pop();
            Object param1 = inStack.pop();

            if ((param1 instanceof Number) && (param2 instanceof Number)) {
                double p1 = ((Number) param2).doubleValue();
                double p2 = ((Number) param1).doubleValue();

                double result = Math.max(p1, p2);

                inStack.push(new Double(result));
            } else {
                throw new ParseException("max method. Invalid parameter type");
            }
            return;
        }

    }
}
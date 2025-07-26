package com.meta.util;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldDefinition;
import com.meta.core.field.FieldType;

import java.util.*;

public class FieldBeanSorter {

    /**
     * 对 FieldBean 列表进行排序并检测循环依赖。
     * @param fieldBeans 待排序的 FieldBean 列表
     * @return 排序后的 FieldBean 列表
     * @throws CircularDependencyException 如果检测到循环依赖
     */
    public List<FieldBean> sortFieldBeans(List<FieldBean> fieldBeans) throws CircularDependencyException {
        if (fieldBeans == null || fieldBeans.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. 构建依赖图和入度
        Map<String, FieldBean> fieldBeanMap = new HashMap<>(); // ID -> FieldBean 对象
        Map<String, Integer> inDegree = new HashMap<>();       // ID -> 入度
        Map<String, Set<String>> graph = new HashMap<>();      // ID -> 它所依赖的 FieldBean IDs (作为键) 的集合

        // 初始化
        for (FieldBean fb : fieldBeans) {
            fieldBeanMap.put(fb.getCode(), fb);
            inDegree.put(fb.getCode(), 0);
            graph.put(fb.getCode(), new HashSet<>()); // 初始化为空的依赖集合
        }

        // 填充图和计算入度
        for (FieldDefinition currentBean : fieldBeans) {
            String currentId = currentBean.getCode();
            List<String> dependentVariables = currentBean.getDependentVariables();

            // 遍历当前 bean 表达式中解析出的所有依赖变量
            for (String depVar : dependentVariables) {
                // 如果这个依赖变量实际上是另一个 fieldBean 的 ID
                if (fieldBeanMap.containsKey(depVar)) {
                    // 表示 currentBean 依赖于 depVar 对应的 fieldBean
                    // 在图中表示为：depVar -> currentId (注意方向，Kahn's算法是 A->B 表示 A 是 B 的前置)
                    // 所以，我们记录的是 currentBean 依赖于哪些 bean，即 graph 的键是 currentBean
                    // 但是，Kahn's 算法的入度是计算指向当前节点的边数，所以我们需要反向思考。
                    // A 依赖 B，那么排序时 B 必须在 A 之前。
                    // 也就是说，有一条从 B 到 A 的有向边。
                    // 那么 A 的入度增加。

                    // 这里的 graph 存储的是：Key -> List of Nodes that Key points to (nodes that depend on Key)
                    // inDegree 存储的是：Key -> Number of nodes that point to Key (nodes that Key depends on)

                    // 换个思路，我们直接构建 "当前节点依赖哪些节点" 的图
                    // 这样 A 依赖 B 就在图里存 A -> B
                    // 那么 B 的入度就增加

                    // 构建图: K = 字段ID, V = 该字段表达式所依赖的其他字段ID
                    // graph: { "B": [], "A": ["B"] } 表示 A 依赖 B
                    // 入度: inDegree: { "B": 1, "A": 0 }
                    // 这种构建方式，inDegree 的含义就对了

                    graph.computeIfAbsent(depVar.toString(), k -> new HashSet<>()).add(currentId);
                    inDegree.put(currentId, inDegree.get(currentId) + 1);
                }
            }
        }

        // 2. 初始化入度为 0 的节点队列
        Queue<FieldBean> queue = new LinkedList<>();
        List<FieldBean> sortedList = new ArrayList<>();

        // 优先将无任何外部依赖的变量放在最前面（例如表达式为 "100" 的常量字段）
        // 也就是那些 inDegree 为 0 的节点
        for (FieldBean fb : fieldBeans) {
            if (inDegree.get(fb.getCode()) == 0) {
                queue.offer(fb);
            }
        }

        // 3. 执行拓扑排序
        while (!queue.isEmpty()) {
            FieldBean current = queue.poll();
            sortedList.add(current);

            // 获取当前 FieldBean ID 所依赖的 FieldBean ID 列表 (注意图的构建方式)
            // graph 存储的是 A 依赖 B，那么 graph.get(A) 会得到 B
            // 但拓扑排序是处理“前置条件”的关系。如果 B 是 A 的前置，那 A 应该在 B 之后。
            // 我们的图是 A -> B 表示 A 依赖 B。
            // 所以，我们需要遍历所有被 current 依赖的节点，让它们的入度减一

            // 遍历所有依赖 currentBean 的 FieldBean (即 graph 中 key 为 currentBean.id 的 value 列表)
            Set<String> dependents = graph.get(current.getCode());
            if (dependents != null) {
                for (String dependentId : dependents) {
                    inDegree.put(dependentId, inDegree.get(dependentId) - 1);
                    if (inDegree.get(dependentId) == 0) {
                        queue.offer(fieldBeanMap.get(dependentId));
                    }
                }
            }
        }

        // 4. 循环依赖检测
        if (sortedList.size() != fieldBeans.size()) {
            // 找出未能加入排序列表的节点，它们构成了循环
            Set<String> remainingNodes = new HashSet<>(fieldBeanMap.keySet());
            for (FieldBean fb : sortedList) {
                remainingNodes.remove(fb.getCode());
            }
            throw new CircularDependencyException("Circular dependency detected involving nodes: " + remainingNodes);
        }

        return sortedList;
    }

    // 自定义异常，用于表示循环依赖
    public static class CircularDependencyException extends Exception {
        public CircularDependencyException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        // 示例数据
        List<FieldBean> fieldBeans = new ArrayList<>();
        fieldBeans.add(FieldBean.of("A", "A",FieldType.NUMBER_DECIMAL.name(), "10", Collections.emptyList())); // 无依赖
        fieldBeans.add(FieldBean.of("B", "B", FieldType.NUMBER_DECIMAL.name(), "A * 2", Arrays.asList("A"))); // 依赖A
        fieldBeans.add(FieldBean.of("C", "C", FieldType.NUMBER_DECIMAL.name(), "A + B", Arrays.asList("A", "B"))); // 依赖A, B
        fieldBeans.add(FieldBean.of("D", "D", FieldType.NUMBER_DECIMAL.name(), "C / 2", Arrays.asList("C"))); // 依赖C
        fieldBeans.add(FieldBean.of("E", "E", FieldType.NUMBER_DECIMAL.name(), "D + 5", Arrays.asList("D"))); // 依赖D
        fieldBeans.add(FieldBean.of("F", "F", FieldType.NUMBER_DECIMAL.name(), "B + E", Arrays.asList("B", "E"))); // 依赖B, E

        System.out.println("Original FieldBeans: " + fieldBeans);

        FieldBeanSorter sorter = new FieldBeanSorter();
        try {
            List<FieldBean> sortedList = sorter.sortFieldBeans(fieldBeans);
            System.out.println("\nSorted FieldBeans (topological order):");
            sortedList.forEach(fb -> System.out.println(fb.getCode()));

            // 示例：存在循环依赖
            System.out.println("\n--- Testing Circular Dependency ---");
            List<FieldBean> circularBeans = new ArrayList<>();
            circularBeans.add(FieldBean.of("X","", FieldType.NUMBER_DECIMAL.name(), "Y + 1", Arrays.asList("Y")));
            circularBeans.add(FieldBean.of("Y","", FieldType.NUMBER_DECIMAL.name(), "Z * 2", Arrays.asList("Z")));
            circularBeans.add(FieldBean.of("Z","", FieldType.NUMBER_DECIMAL.name(), "X / 3", Arrays.asList("X"))); // Z 依赖 X, 形成循环

            try {
                sorter.sortFieldBeans(circularBeans);
            } catch (CircularDependencyException e) {
                System.err.println("Error: " + e.getMessage());
            }

            // 示例：自循环
            System.out.println("\n--- Testing Self-Loop Dependency ---");
            List<FieldBean> selfLoopBeans = new ArrayList<>();
            selfLoopBeans.add(FieldBean.of("P","", FieldType.NUMBER_DECIMAL.name(), "P + 1", Arrays.asList("P"))); // P 依赖 P
            selfLoopBeans.add(FieldBean.of("Q","", FieldType.NUMBER_DECIMAL.name(), "P * 2", Arrays.asList("P")));

            try {
                sorter.sortFieldBeans(selfLoopBeans);
            } catch (CircularDependencyException e) {
                System.err.println("Error: " + e.getMessage());
            }

        } catch (CircularDependencyException e) {
            System.err.println("Fatal Error: " + e.getMessage());
        }
    }
}

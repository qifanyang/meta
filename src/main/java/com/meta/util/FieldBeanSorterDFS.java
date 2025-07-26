package com.meta.util;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;

import java.util.*;

public class FieldBeanSorterDFS {

    // 节点状态枚举，这是实现“检测到循环立即终止”的关键
    private enum NodeState {
        UNVISITED,  // 未访问：节点尚未被任何DFS路径访问
        VISITING,   // 正在访问：节点已进入当前的DFS递归栈，正在被探索
        VISITED     // 已访问：节点及其所有依赖都已成功访问并处理完毕
    }

    /**
     * 对 FieldBean 列表进行排序并检测循环依赖（DFS实现）。
     * @param fieldBeans 待排序的 FieldBean 列表
     * @param allowSelfLoop 如果为 true，则 x = x + 5 这种表达式中的自循环被允许，不计入循环依赖。
     * @return 排序后的 FieldBean 列表
     * @throws CircularDependencyException 如果检测到不允许的循环依赖，会立即抛出
     */
    public List<FieldBean> sortFieldBeans(List<FieldBean> fieldBeans, boolean allowSelfLoop) throws CircularDependencyException {
        if (fieldBeans == null || fieldBeans.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, FieldBean> fieldBeanMap = new HashMap<>(); // ID -> FieldBean 对象，方便通过ID查找对象
        // graph 存储的是：key -> List of nodes that key depends on
        // 也就是说，如果 A 依赖 B，那么 graph.get(A) 会包含 B
        Map<String, Set<String>> graph = new HashMap<>();

        // 1. 初始化所有 FieldBean 的映射和图结构
        for (FieldBean fb : fieldBeans) {
            fieldBeanMap.put(fb.getCode(), fb);
            graph.put(fb.getCode(), new HashSet<>()); // 初始化为空依赖集合
        }

        // 2. 填充图：构建 "谁依赖谁" 的关系图
        // 如果 currentId 依赖于 depVar，则添加从 currentId 到 depVar 的有向边
        for (FieldBean currentBean : fieldBeans) {
            String currentId = currentBean.getCode();
            List<String> dependentVariables = currentBean.getDependentVariables();
            for (String depVar : dependentVariables) {
                // 确保依赖的变量确实是另一个 FieldBean 的ID
                if (fieldBeanMap.containsKey(depVar)) {
                    // 如果允许自循环且当前依赖是自身，则不将此边加入图，以便DFS不会将其视为循环
                    if (allowSelfLoop && depVar.equals(currentId)) {
                        continue;
                    }
                    // 添加边：currentId 依赖于 depVar
                    graph.get(currentId).add(depVar);
                }
            }
        }

        // DFS 相关数据结构
        Map<String, NodeState> states = new HashMap<>(); // 存储每个节点的访问状态
        List<FieldBean> sortedList = new ArrayList<>(); // 存储拓扑排序结果（初始为逆序）

        // 3. 初始化所有节点为 UNVISITED 状态
        for (FieldBean fb : fieldBeans) {
            states.put(fb.getCode(), NodeState.UNVISITED);
        }

        // 4. 遍历所有节点，进行 DFS 访问
        // 确保所有连通分量都能被访问到
        for (FieldBean fb : fieldBeans) {
            // 只对未访问的节点启动DFS，避免重复访问
            if (states.get(fb.getCode()) == NodeState.UNVISITED) {
                dfsVisit(fb.getCode(), fieldBeanMap, graph, states, sortedList);
            }
        }

        // 5. DFS 拓扑排序的结果是逆序的（后完成DFS的节点应该在前面），所以需要反转
//        Collections.reverse(sortedList);

        // 6. 最终验证：如果排序后的列表大小不等于原始列表大小，说明存在未能被处理的循环
        // （除了那些被 allowSelfLoop 允许的自循环）
        if (sortedList.size() != fieldBeans.size()) {
            Set<String> remainingNodes = new HashSet<>(fieldBeanMap.keySet());
            for (FieldBean fb : sortedList) {
                remainingNodes.remove(fb.getCode());
            }
            throw new CircularDependencyException("Circular dependency detected involving nodes: " + remainingNodes);
        }

        return sortedList;
    }

    /**
     * 递归的 DFS 访问函数。这是核心逻辑。
     * @param nodeId 当前访问的节点 ID
     * @param fieldBeanMap ID 到 FieldBean 对象的映射
     * @param graph 依赖图 (Key -> List of dependencies)
     * @param states 节点访问状态
     * @param sortedList 拓扑排序结果列表
     * @throws CircularDependencyException 如果检测到循环依赖，会立即抛出
     */
    private void dfsVisit(String nodeId,
                          Map<String, FieldBean> fieldBeanMap,
                          Map<String, Set<String>> graph,
                          Map<String, NodeState> states,
                          List<FieldBean> sortedList) throws CircularDependencyException {

        // 步骤 A: 标记当前节点为 VISITING
        // 这表示我们已经进入了这个节点的探索路径
        states.put(nodeId, NodeState.VISITING);

        // 步骤 B: 遍历当前节点的所有直接依赖
        Set<String> dependencies = graph.get(nodeId);
        if (dependencies != null) {
            for (String depId : dependencies) {
                NodeState depState = states.get(depId);
                if (depState == NodeState.UNVISITED) {
                    // 如果依赖节点未访问，则递归访问它
                    dfsVisit(depId, fieldBeanMap, graph, states, sortedList);
                } else if (depState == NodeState.VISITING) {
                    // 核心循环检测逻辑：
                    // 如果发现一个依赖节点当前正在被访问（即在当前递归栈中），
                    // 这就意味着存在一个循环。立即抛出异常，终止排序。
                    throw new CircularDependencyException("Circular dependency detected involving node: " + depId + " (dependency of " + nodeId + ")");
                }
                // 如果 depState == NodeState.VISITED，表示该依赖已经处理过，直接跳过
                // 这是正常的拓扑排序路径，不会导致循环
            }
        }

        // 步骤 C: 所有依赖都已处理完毕，标记当前节点为 VISITED
        // 这表示从当前节点出发的所有路径都已探索完毕，该节点可以安全地从递归栈中移除
        states.put(nodeId, NodeState.VISITED);

        // 步骤 D: 将当前节点添加到排序列表（此时为逆拓扑序）
        sortedList.add(fieldBeanMap.get(nodeId));
    }

    // 自定义异常类
    public static class CircularDependencyException extends Exception {
        public CircularDependencyException(String message) {
            super(message);
        }
    }

    // FieldBean 定义（与之前相同）

    public static void main(String[] args) {
        // 示例数据
        List<com.meta.core.field.FieldBean> fieldBeans = new ArrayList<>();
        fieldBeans.add(com.meta.core.field.FieldBean.of("C", "C", FieldType.NUMBER_DECIMAL.name(), "A + B", Arrays.asList("A", "B"))); // 依赖A, B
        fieldBeans.add(com.meta.core.field.FieldBean.of("A", "A", FieldType.NUMBER_DECIMAL.name(), "10", Collections.emptyList())); // 无依赖
        fieldBeans.add(com.meta.core.field.FieldBean.of("D", "D", FieldType.NUMBER_DECIMAL.name(), "C / 2", Arrays.asList("C"))); // 依赖C
        fieldBeans.add(com.meta.core.field.FieldBean.of("B", "B", FieldType.NUMBER_DECIMAL.name(), "A * 2", Arrays.asList("A"))); // 依赖A
        fieldBeans.add(com.meta.core.field.FieldBean.of("E", "E", FieldType.NUMBER_DECIMAL.name(), "D + 5", Arrays.asList("D"))); // 依赖D
        fieldBeans.add(com.meta.core.field.FieldBean.of("F", "F", FieldType.NUMBER_DECIMAL.name(), "B + E", Arrays.asList("B", "E"))); // 依赖B, E

        System.out.println("Original FieldBeans: " + fieldBeans);

        FieldBeanSorterDFS sorter = new FieldBeanSorterDFS();
        try {
            List<com.meta.core.field.FieldBean> sortedList = sorter.sortFieldBeans(fieldBeans, true);
            System.out.println("\nSorted FieldBeans (topological order):");
            sortedList.forEach(fb -> System.out.println(fb.getCode()));

            // 示例：存在循环依赖
            System.out.println("\n--- Testing Circular Dependency ---");
            List<com.meta.core.field.FieldBean> circularBeans = new ArrayList<>();
            circularBeans.add(com.meta.core.field.FieldBean.of("X","", FieldType.NUMBER_DECIMAL.name(), "Y + 1", Arrays.asList("Y")));
            circularBeans.add(com.meta.core.field.FieldBean.of("Y","", FieldType.NUMBER_DECIMAL.name(), "Z * 2", Arrays.asList("Z")));
            circularBeans.add(com.meta.core.field.FieldBean.of("Z","", FieldType.NUMBER_DECIMAL.name(), "X / 3", Arrays.asList("X"))); // Z 依赖 X, 形成循环

            try {
                sorter.sortFieldBeans(circularBeans, true);
            } catch (CircularDependencyException e) {
                System.err.println("Error: " + e.getMessage());
            }

            // 示例：自循环
            System.out.println("\n--- Testing Self-Loop Dependency ---");
            List<com.meta.core.field.FieldBean> selfLoopBeans = new ArrayList<>();
            selfLoopBeans.add(com.meta.core.field.FieldBean.of("P","", FieldType.NUMBER_DECIMAL.name(), "P + 1", Arrays.asList("P"))); // P 依赖 P
            selfLoopBeans.add(com.meta.core.field.FieldBean.of("Q","", FieldType.NUMBER_DECIMAL.name(), "P * 2", Arrays.asList("P")));

            try {
                sorter.sortFieldBeans(selfLoopBeans, true);
            } catch (CircularDependencyException e) {
                System.err.println("Error: " + e.getMessage());
            }
            try {
                sorter.sortFieldBeans(selfLoopBeans, false);
            } catch (CircularDependencyException e) {
                System.err.println("Error: " + e.getMessage());
            }

        } catch (CircularDependencyException e) {
            System.err.println("Fatal Error: " + e.getMessage());
        }
    }
}

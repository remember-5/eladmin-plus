/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.system.modules.generator.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangjiahao
 * @date 2023/7/7 17:13
 */
@Data
public class Node {

    private String label;
    private String value;
    private List<Node> children;

    public Node(String label,String value) {
        this.label = label;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void reverseList() {
        if (children != null) {
            // 反转list
            Collections.reverse(children);
            // 递归反转子节点的list
            for (Node node : children) {
                node.reverseList();
            }
        }
    }
}

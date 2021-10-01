package com.example.empty.mydb;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
class DFS
{
    static void dfs(List<List<Integer>> results, List<Integer> path, int[] nums) {
        if(path.size() == nums.length) {
            results.add(new ArrayList<>(path));
            System.out.println("add");
            return;
        }
        else {
            for (int i=0;i<nums.length;i++) {
                if (path.contains(nums[i])) {
                    System.out.println("i = "+i+"  "+"return path" + path);
                    continue;
                }
                path.add(nums[i]);
                System.out.println("i = "+i+"  "+"path" + path);
                dfs(results, path, nums);
                System.out.println( "i = "+i+"  "+"before remove path" + path);
                path.remove(path.size() - 1); //#backtracking
                System.out.println("i = "+i+"  "+"after remove path" + path);
            }
        }

    }
    public static void main(String[] args) {
      //  String[]tmp2="apple".split("\\+");
        int []nums={1,2,3};
        List<List<Integer>> results = new ArrayList<>();
        dfs(results, new ArrayList(), nums);
        /* */
        for (List<Integer> result : results) {
            for (Integer integer : result) {
                System.out.print(integer);
            }
            System.out.println();
        }
    }
}
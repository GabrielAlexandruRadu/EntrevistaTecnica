package com.example.taskmanager;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeetCode {
    public int findMostFrequentElement(int [] nums) {
        Map<Integer,Long> frequencyMap = Arrays.stream(nums).boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    public int MoneyMaker(int [] prices){
        if (prices == null || prices.length < 2) {
            return 0;
        }

        // Use an array to keep track of the minimum price seen so far
        int[] minPrice = {prices[0]};

        // Compute the maximum profit using streams
        return IntStream.range(1, prices.length)
                .map(i -> {
                    // Calculate the potential profit
                    int potentialProfit = prices[i] - minPrice[0];
                    // Update the minimum price
                    minPrice[0] = Math.min(minPrice[0], prices[i]);
                    return potentialProfit;
                })
                .filter(profit -> profit > 0)
                .max() // Find the maximum profit
                .orElse(0); // If no profit can be made, return 0
    }

    public int removeDuplicates2(int[] nums){
        int k = 1;
        int counter = 1;
        for (int i =1; i<nums.length;i++){
            if(nums[i] == nums[i-1]){
                counter++;
            }else {
                counter = 1;
            }
            if (counter <=2 ){
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }

    public void rotateArray(int[] nums, int k) {
        int n = nums.length;
        k = k % n; // Normalize k to be within the bounds of the array length

        // Create the rotated array using streams
        int[] rotatedArray = IntStream.concat(
                Arrays.stream(nums, n - k, n), // Last k elements
                Arrays.stream(nums, 0, n - k)  // First n - k elements
        ).toArray();

        System.arraycopy(rotatedArray, 0, nums, 0, n);
    }

    public int maxProfit(int [] prices) {


        return IntStream.range(0,prices.length-1)
                .map(i -> prices[i+1] - prices[i])
                .filter(profit -> profit >0)
                .sum();

    }

    public boolean canJump(int [] nums){

        int reachable=0;

        for (int i = 0; i< nums.length; i++){
            if (i > reachable){
                return false;
            }

            // update the farthest reachable index
            reachable = Math.max(reachable, i + nums[i]);
        }
        return true;

    }
    public int Jump(int[] nums){
        int[] state = {0, 0, 0}; // state[0]: jumps, state[1]: currentEnd, state[2]: farthest


        IntStream.range(0,nums.length -1)
                .forEach( i -> {
                    state[2] =Math.max(state[2], i + nums[i]);
                    if (i == state[1]){
                        state[0]++;
                        state[1] = state[2];
                    }
                });
        return state[0];
    }

    public int hIndex(int[] citations) {

        // Sort citations in descending order
        int[] sortedCitations = IntStream.of(citations)
                .boxed()
                .sorted((a, b) -> b - a)
                .mapToInt(Integer::intValue)
                .toArray();

        // Create pairs of (h, citation)
        return IntStream.range(0, sortedCitations.length)
                .mapToObj(i -> new int[]{i + 1, sortedCitations[i]})
                .filter(pair -> pair[1] >= pair[0])
                .mapToInt(pair -> pair[0])
                .max()
                .orElse(0);

    }


}






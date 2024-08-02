package com.example.taskmanager;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.AbstractMap.SimpleEntry;

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


    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = IntStream.of(gas).sum();
        int totalCost = IntStream.of(cost).sum();

        // Check if the trip is feasible
        if (totalGas < totalCost) {
            return -1;
        }

        // Variables to track the starting point and current balance
        int startingPoint = 0;
        int currentGas = 0;

        // Traverse through the gas stations
        for (int i = 0; i < gas.length; i++) {
            currentGas += gas[i] - cost[i];

            // If currentGas is negative, reset the starting point
            if (currentGas < 0) {
                startingPoint = i + 1;
                currentGas = 0;
            }
        }

        return startingPoint;
    }

    public int candy(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;

        int[] candies = new int[n];
        // Each child gets at least one candy
        Arrays.fill(candies, 1);
        System.out.println("Initial candies: " + Arrays.toString(candies));

        // Left to right: ensure each child with a higher rating than the previous one gets more candies
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        System.out.println("Candies after left to right pass: " + Arrays.toString(candies));

        // Right to left: ensure each child with a higher rating than the next one gets more candies
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        System.out.println("Candies after right to left pass: " + Arrays.toString(candies));

        // Calculate the total number of candies
        int result = 0;
        for (int candy : candies) {
            result += candy;
        }

        return result;
    }

    public int trap(int[] height) {
        int n = height.length;
        if (n == 0) return 0;

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Calculate left max for each position
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // Calculate right max for each position
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        // Calculate the trapped water
        int trappedWater = 0;
        for (int i = 0; i < n; i++) {
            trappedWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return trappedWater;
    }

    public int romanToInt(String s) {
        Map< Character,Integer> numberToCharMap = new HashMap<>();
        numberToCharMap.put('I',1);
        numberToCharMap.put( 'V',5);
        numberToCharMap.put( 'X',10);
        numberToCharMap.put( 'L',50);
        numberToCharMap.put( 'C',100);
        numberToCharMap.put( 'D',500);
        numberToCharMap.put( 'M',1000);

        // List to store the result
        List<Integer> resultList = new ArrayList<>();

        for (char c : s.toCharArray()){
            Integer number = numberToCharMap.get(c);
            if (number != null) {
                resultList.add(number);

            }
        }
        int finalResult = 0;

        for (int i = 0; i < resultList.size() - 1; i++) {
            if (resultList.get(i) >= resultList.get(i + 1)) {
                finalResult += resultList.get(i);
            } else {
                finalResult -= resultList.get(i);
            }
        }
        // Add the last element to the finalResult (since it has no next element to compare)
        finalResult += resultList.get(resultList.size() - 1);



        return finalResult;





    }
    public String intToRoman(int num) {
        List<SimpleEntry<Integer, String>> romanNumerals = new ArrayList<>();
        romanNumerals.add(new SimpleEntry<>(1000, "M"));
        romanNumerals.add(new SimpleEntry<>(900, "CM"));
        romanNumerals.add(new SimpleEntry<>(500, "D"));
        romanNumerals.add(new SimpleEntry<>(400, "CD"));
        romanNumerals.add(new SimpleEntry<>(100, "C"));
        romanNumerals.add(new SimpleEntry<>(90, "XC"));
        romanNumerals.add(new SimpleEntry<>(50, "L"));
        romanNumerals.add(new SimpleEntry<>(40, "XL"));
        romanNumerals.add(new SimpleEntry<>(10, "X"));
        romanNumerals.add(new SimpleEntry<>(9, "IX"));
        romanNumerals.add(new SimpleEntry<>(5, "V"));
        romanNumerals.add(new SimpleEntry<>(4, "IV"));
        romanNumerals.add(new SimpleEntry<>(1, "I"));

        StringBuilder result = new StringBuilder();

        for (SimpleEntry<Integer, String> entry : romanNumerals) {
            int value = entry.getKey();
            String symbol = entry.getValue();
            while (num >= value) {
                num -= value;
                result.append(symbol);
            }
        }

        return result.toString();


    }

    public int lengthOfLastWord(String s) {

        String[] words = s.split("[\\s.,]+");
        String lastWord = words[words.length - 1];
        int letterCount = lastWord.length();
        return letterCount;

    }

    public String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0) return "";

        // Find the shortest string in the array
        String shortest = strs[0];
        for (String str : strs) {
            if (str.length() < shortest.length()) {
                shortest = str;
            }
        }

        StringBuilder result = new StringBuilder();

        // Iterate up to the length of the shortest string
        for (int i = 0; i < shortest.length(); i++) {
            char currentChar = shortest.charAt(i);

            // Compare this character with the same position in other strings
            for (String str : strs) {
                if (str.charAt(i) != currentChar) {
                    return result.toString(); // Return result if there's a mismatch
                }
            }

            // If all strings have the same character at this position, append it to the result
            result.append(currentChar);
        }

        return result.toString();

    }

    public String reverseWords(String s) {

        String[] words = s.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i=words.length-1; i>=0; i-- ) {
            result.append(words[i]);
            if(i != 0){
                result.append(" ");
            }

        }
        return result.toString();

    }





}






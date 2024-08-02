package com.example.taskmanager;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LeetCodeTest {

    @Test
    public void givenArray_whenFindingMostFrequent_thenCorrectElement(){
        LeetCode leetCode = new LeetCode();

        int num = 3749;
        String expected = "MMMDCCXLIX";

        assertEquals(expected,leetCode.intToRoman(num));

//        int [] height1 = {4,2,0,3,2,5};
//
//        assertEquals(9,leetCode.trap(height1));

//        // Test case 2
//        int[] nums2 = {-1, 1, 0, -3, 3};
//        int[] expected2 = {0, 0, 9, 0, 0};
//        int[] result2 = leetCode.productExceptSelf(nums2);
//        assertArrayEquals(expected2, result2);

    }

}
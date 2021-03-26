public class Backtracking1 {
    public static void main(String[] args) {
        solve();
    }
    
    public static void solve() {
        coinChange();
    }
    
    public static void coinChange() {
        int[] nums = {2, 3, 5, 7};
        int tar = 10;
        
        System.out.println(permutationSingleCoins(nums, tar, ""));
        // System.out.println(permutationInfiCoins(nums, tar, ""));
        // System.out.println(combinationInfiCoins(nums, 0, tar, ""));
        // System.out.println(combinationSingleCoins(nums, 0, tar, ""));
        // System.out.println(subsQCombinationSingleCoins(nums, 0, tar, ""));
        // System.out.println(subsQCombinationInfiCoins(nums, 0, tar, ""));
        // System.out.println(subsQPermutationInfiCoins(nums, 0, tar, ""));
        // System.out.println(subsQPermutationSingleCoins(nums, 0, tar, ""));
        
    }
    

    
    //[start-  Coin Change]-----------------------------

    //faith- call every element and subtract tar from each element in next call until it becomes 0;
    public static int permutationInfiCoins(int[] coins, int tar, String asf) {
        if(tar == 0) {
            System.out.println(asf);
            return 1;
        }
        int res = 0;

        //nothing fancy, just a loop from 0 and if tar - coin[i] is greater than 0, then call tar - coins[i],
        for (int i = 0; i < coins.length; i++) {
            int num = coins[i];
            if (tar - num >= 0) {
                res += permutationInfiCoins(coins,tar - num, asf + num);
            }
        }
        return res;
    }

    //faith- same as above just this time, we just need to call forward element of array
    public static int combinationInfiCoins(int[] coins, int idx, int tar, String asf) {
        if(tar == 0) {
            System.out.println(asf);
            return 1;
        }
        int res = 0;

        //nothing fancy, just a loop from 0 and if tar - coin[i] is greater than 0, then call tar - coins[i],
        for (int i = idx; i < coins.length; i++) {
            int num = coins[i];
            if (tar - num >= 0) {
                res += combinationInfiCoins(coins,i, tar - num, asf + num);
            }
        }
        return res;
    }
    
    //faith: call just like permutationInfiCoins but this time mark vis(pre) and unmark at time of backtrack.
    public static int permutationSingleCoins(int[] coins, int tar, String asf) {
        if(tar == 0) {
            System.out.println(asf);
            return 1;
        }
        
        int res = 0;

        //nothing fancy, just a loop from 0 and if tar - coin[i] is greater than 0, then call tar - coins[i],
        for (int i = 0; i < coins.length; i++) {
            int num = coins[i];
            //we can put num[i] = 0 as visited only in case there's no negative elements otherwise
            //we have to use vis Array
            if (coins[i] > 0 && tar - num >= 0) {
                // vis[i] = true;
                coins[i] = 0;
                res += permutationSingleCoins(coins,tar - num, asf + num);
                // vis[i] = false;
                coins[i] = num;
            }
        }
        return res;
    }
    
    //faith: same as combinationInfi but this time i + 1 every time,means we have to move idx every in every call
    public static int combinationSingleCoins(int[] coins,int idx, int tar, String asf) {
        if(tar == 0 || idx == coins.length) {
            if(tar == 0){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        
        int res = 0;

        //nothing fancy, just a loop from 0 and if tar - coin[i] is greater than 0, then call tar - coins[i],
        for (int i = idx; i < coins.length; i++) {
            int num = coins[i];
            if (tar - num >= 0) {
                res += combinationSingleCoins(coins,i + 1, tar - num, asf + num);
            }
        }
        return res;
    }

    
    //faith: isme hm ek coin ko select krenge ek ko nhi, to jisme nhi krenge usme tar minus nhi hoga
    //basecase: age tar== 0 ,then print or idx == length, then return 0;
    public static int subsQCombinationSingleCoins(int[] coins, int idx , int tar, String asf) {
        if(idx == coins.length || tar == 0) {
            if(tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        int count = 0;

        if(tar - coins[idx] >= 0)
            count += subsQCombinationSingleCoins(coins,  idx + 1,  tar - coins[idx],  asf + coins[idx]);
        count += subsQCombinationSingleCoins(coins,  idx + 1,  tar,  asf);

        return count;
    
    }


    //faith: same as above, this time if we select the coin, then idx will be same
    public static int subsQCombinationInfiCoins(int[] coins, int idx, int tar, String asf) {
        if(tar == 0 || idx == coins.length) {
            if(tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;
        int num = coins[idx];

        if(tar - num >= 0)
            count += subsQCombinationInfiCoins(coins, idx, tar - num, asf + num);
        count += subsQCombinationInfiCoins(coins, idx + 1, tar , asf);

        return count;
    }


    //faith: ( I'm In, I'm Out) || If in start from 0, else call to next idx until tar == 0;
    public static int subsQPermutationInfiCoins(int[] coins, int idx, int tar, String asf) {
        if(tar == 0 || idx == coins.length) {
            if(tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;
        int num = coins[idx];

        if(tar - num >= 0)
            count += subsQPermutationInfiCoins(coins, 0, tar - num, asf + num);
        count += subsQPermutationInfiCoins(coins, idx + 1, tar , asf);


        return count;
    }


    public static int subsQPermutationSingleCoins(int[] nums, int idx, int tar, String asf) {
        if(tar == 0 || idx == nums.length) {
            if(tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;
        int num = nums[idx];

        if( num > 0 && tar - num >= 0){
            nums[idx] = 0;
            count += subsQPermutationSingleCoins(nums, 0, tar - num, asf + num);
            nums[idx] = num;
        }
        count += subsQPermutationSingleCoins(nums, idx + 1, tar , asf);


        return count;
    }
    //[end] Coin Change ---------------------------------

    public static void display(int[] arr ) {
        for(int n: arr) 
            System.out.print(n + " ");
    }

    public static void display_2D(int[][] arr ) {
        for(int i = 0; i< arr.length; i++){
            for(int j = 0; j< arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
        }      
    }


}

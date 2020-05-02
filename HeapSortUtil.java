import java.util.*;
// Heap sort

public class HeapSortUtil{
    public static <T extends Comparable> void heatSort(T arr[]){
        if (arr == null)
            throw new RuntimeException("No Data");
        int length = arr.length;
        if (length > 1) {
            //1. Initialize the maximum heap
            initMaxHeap(arr, length - 1);
            //2. Heap sort
            //swap the first element with the last element
            //and adjust the heap length of i=N-1 as the max heap
            //continue minimize sort range until there's no unsorted element
            for (int i = length - 1; i > 0; i--) {
            	//2.1 swap the first element with the last element
                swap(arr, 0, i);
                //2.2 minimize unsorted range as i-1, adjust it to max heap
                adjustMaxHeap(arr, 0, i - 1);
            }
        }
    }


    //Construct max heap(unsorted)
    //<param name="arr"> array for sort </param>
    //<param name="maxIndex"> max index of array for sort </param> 
    private static <T extends Comparable>void initMaxHeap(T arr[], int maxIndex){
    	//From the last non-leaf node in Complete Binary Tree
    	//If the index of root node in array is 0, the child nodes at index n is 2n+1 and 2n+2
    	//Its parent node index is (n-1)/2
        for (int i = (maxIndex - 1) / 2; i >= 0; i--) {
            adjustMaxHeap(arr, i, maxIndex);
        }
    }

    //Adjust B-tree with required parent node as max heap
    //<param name="arr"> array for sort </param>
    //<param name="parentNodeIndex"> required parent node </param>
    //<param name="maxIndex"> max index of array for sort </param>
    private static <T extends Comparable>void adjustMaxHeap(T arr[], int parentNodeIndex, int maxIndex){
        if (maxIndex > 0){   //Only one element at top, no need adjust
            int resultIndex = -1;
            //The node with index i has child nodes with indexs 2i+1 and 2i+2
            int leftIndex = 2 * parentNodeIndex + 1;
            int rightIndex = 2 * parentNodeIndex + 2;
            if (leftIndex > maxIndex) {
                //This parent node has no child node
                return;
            }
            else if (rightIndex > maxIndex)
                resultIndex = leftIndex;
            else
                //Compare left and right node
                resultIndex = max(arr, leftIndex, rightIndex);

            //Compare parent node with the larger child node
            resultIndex = max(arr, parentNodeIndex, resultIndex);

            if (resultIndex != parentNodeIndex) {
            	//If paernt node is not the largest node, swap them
                swap(arr, parentNodeIndex, resultIndex);
                //Adjust new sub-tree as it may not a max heap
                adjustMaxHeap(arr, resultIndex, maxIndex);
            }
        }
    }

    //Get array indexes with larger number
    //<param name="arr"> array for sort </param>
    //<param name="leftIndex"> left node index </param>
    //<param name="rightIndex"> right node index </param>
    //<returns> return array index with larger number </returns>
    private static <T extends Comparable>int max(T arr[], int leftIndex, int rightIndex){
        //When they are equal, set left node as the max
        T leftObj = arr[leftIndex];
        T rightObj = arr[rightIndex];
        return leftObj.compareTo(rightObj)>= 0 ? leftIndex : rightIndex;
    }

    //Swap elements in array
    //<typeparam name="T"></typeparam>
    //<param name="arr"> array </param>
    //<param name="i"> element1 for swap </param>
    //<param name="j"> element2 for swap </param>
    private static <T extends Comparable>void swap(T arr[], int i, int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

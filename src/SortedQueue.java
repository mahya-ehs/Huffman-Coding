/**a class for ascending sort using min heap**/

public class SortedQueue
{
    private QueueNode[] arr;
    private int heapSize = 0;
    private QueueNode temp_node = new QueueNode(100000, ' ');

    public SortedQueue()
    {
        //the array of min heap
        arr = new QueueNode[127];
    }

    //getting the size of the sorted queue
    public int size()
    {
        return heapSize ;
    }

    //getting the first element of the queue
    public QueueNode front()
    {
        return arr[1];
    }

    //adding element to queue
    public void add(QueueNode key)
    {
        heapSize ++;
        arr[heapSize] = temp_node;

        decreaseKey(arr, heapSize, key);
    }

    //when adding an element to a min heap,
    // you have to check where it can be placed according to its value
    private void decreaseKey(QueueNode[] queue, int index, QueueNode key)
    {
        queue[index] = key;
        //saving the index of the queue node
        key.setIndex(index);

        //a loop for comparing the values
        while((index > 1) && ( queue[getParent(queue, index)].getCount() > queue[index].getCount()))
        {
            //swap
            QueueNode temp;
            temp = queue[getParent(queue, index)];
            queue[getParent(queue, index)] = queue[index];
            queue[index] = temp;
            index = getParent(queue, index);
        }
    }

    //getting the minimum value of the queue
    public QueueNode pop()
    {
        //storing the root of the min heap in minimum and
        // then replacing it with the last node of the heap
        QueueNode minimum = arr[1];
        arr[1] = arr[heapSize];
        heapSize--;
        heapify(arr, 1);

        return minimum;
    }

    //after extracting the root of the min heap,
    // we have to heapify the min heap again
    private void heapify(QueueNode[] queue, int index)
    {
        //in_root is the index of the last node of the min heap
        // which is be replaced with node an then will be swapped with is children if needed
        int in_root = index;
        int leftChildIndex = getLeft(queue, index);
        int rightChildIndex = getRight(queue, index);

        if ((leftChildIndex > 0) && (leftChildIndex <= heapSize))
        {
            if (queue[leftChildIndex].getCount() < queue[in_root].getCount())
            {
                in_root = leftChildIndex;
            }
        }

        if ((rightChildIndex > 0) && (rightChildIndex <= heapSize))
        {
            if (queue[rightChildIndex].getCount() < queue[in_root].getCount())
            {
                in_root = rightChildIndex;
            }
        }

        //if the children is less than the root, we swap them
        if (in_root != index)
        {
            //swap
            QueueNode temp;
            temp = queue[in_root];
            queue[in_root] = queue[index];
            queue[index] = temp;
            heapify(queue, in_root);
        }
    }

    //getting the children and parents of an element of a min heap
    private int getRight(QueueNode []queue, int index)
    {
        if((((2 *index) + 1) < queue.length && (index >= 1)))
            return (2 * index) + 1;

        return -1;
    }

    private int getLeft(QueueNode []queue, int index)
    {
        if(((2 * index) < queue.length && (index >= 1)))
            return 2*index;

        return -1;
    }

    private int getParent(QueueNode []queue, int index)
    {
        if ((index > 1) && (index < queue.length)) {
            return index/2;
        }
        return -1;
    }
}

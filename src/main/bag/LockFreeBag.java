package edu.vt.ece.hw5.bag;

public class LockFreeBag<T> {
    private static int count = 0;

    class ListNode
    {
        ModifiedLockFreeList<Integer> mlist;
        int thrID;
        public ListNode(int id)
        {
            this.thrID = id;
            this.mlist = new ModifiedLockFreeList<Integer>();

        }
    }

    private final ThreadLocal<Integer> threadID = new ThreadLocal<Integer>()
    {
        protected Integer initialValue()
        {
            return count++;
        }
    };

    private ThreadLocal<ListNode> localNode = new ThreadLocal<ListNode>()
    {
        protected ListNode initialValue()
        {
            return null;
        }
    };

    private ThreadLocal<ListNode> remoteRemovalNode = new ThreadLocal<ListNode>()
    {
        protected ListNode initialValue()
        {
            return null;
        }
    };

    private ThreadLocal<Integer> remoteRemovalNodeID = new ThreadLocal<Integer>();

    ModifiedLockFreeList<ListNode> LFBag;

    public LockFreeBag()
    {
        LFBag = new ModifiedLockFreeList<ListNode>();
    }

    public void add(int item)
    {
        ListNode curr = localNode.get();
        if(curr != null)
        {
            curr.mlist.add(item, threadID.get());
        }
        else
        {
            ListNode mynode = new ListNode(threadID.get());
            mynode.mlist.add(item,threadID.get());
            LFBag.add(mynode,threadID.get());
            localNode.set(mynode);
        }
    }

    public void tryRemoveAny()
    {
        boolean removeStatus = false;
        //LFNode curr = LFBag.contains(threadID.get());
        ListNode curr = localNode.get();
        if(curr != null)
        {
            removeStatus = curr.mlist.remove(threadID.get());
            if(removeStatus)
                return;
        }
        ListNode rmnode = remoteRemovalNode.get();
        if(rmnode != null)
        {
            if(rmnode.mlist.remove(remoteRemovalNodeID.get()))
            {
                return;
            }
            else
            {
                remoteRemovalNode.set(null);
            }
        }
        if(curr == null || removeStatus == false)
        {
            for(int i = 0; i < count; i++)
            {
                ListNode node = LFBag.contains(i);
                if((node != null) && node.mlist.remove(i))
                {
                    remoteRemovalNode.set(node);
                    remoteRemovalNodeID.set(i);
                    break;
                }
            }
        }
    }
}

# 3408. Design Task Manager

# There is a task management system that allows users to manage their tasks, each associated with a priority. The system should efficiently handle adding, modifying, executing, and removing tasks.

# Implement the TaskManager class:

# TaskManager(vector<vector<int>>& tasks) initializes the task manager with a list of user-task-priority triples. Each element in the input list is of the form [userId, taskId, priority], which adds a task to the specified user with the given priority.

# void add(int userId, int taskId, int priority) adds a task with the specified taskId and priority to the user with userId. It is guaranteed that taskId does not exist in the system.

# void edit(int taskId, int newPriority) updates the priority of the existing taskId to newPriority. It is guaranteed that taskId exists in the system.

# void rmv(int taskId) removes the task identified by taskId from the system. It is guaranteed that taskId exists in the system.

# int execTop() executes the task with the highest priority acrAoss all users. If there are multiple tasks with the same highest priority, execute the one with the highest taskId. After executing, the taskId is removed from the system. Return the userId associated with the executed task. If no tasks are available, return -1.

# Note that a user may be assigned multiple tasks.

 

# Example 1:

# Input:
# ["TaskManager", "add", "edit", "execTop", "rmv", "add", "execTop"]
# [[[[1, 101, 10], [2, 102, 20], [3, 103, 15]]], [4, 104, 5], [102, 8], [], [101], [5, 105, 15], []]

# Output:
# [null, null, null, 3, null, null, 5]

# Explanation

# TaskManager taskManager = new TaskManager([[1, 101, 10], [2, 102, 20], [3, 103, 15]]); // Initializes with three tasks for Users 1, 2, and 3.
# taskManager.add(4, 104, 5); // Adds task 104 with priority 5 for User 4.
# taskManager.edit(102, 8); // Updates priority of task 102 to 8.
# taskManager.execTop(); // return 3. Executes task 103 for User 3.
# taskManager.rmv(101); // Removes task 101 from the system.
# taskManager.add(5, 105, 15); // Adds task 105 with priority 15 for User 5.
# taskManager.execTop(); // return 5. Executes task 105 for User 5.
 

# Constraints:

# 1 <= tasks.length <= 10^5
# 0 <= userId <= 10^5
# 0 <= taskId <= 10^5
# 0 <= priority <= 10^9
# 0 <= newPriority <= 10^9
# At most 2 * 10^5 calls will be made in total to add, edit, rmv, and execTop methods.
# The input is generated such that taskId will be valid.


# Solution:

import bisect

class TaskManager(object):

    def __init__(self, tasks):
        """
        :type tasks: List[List[int]]
        """
        self.task_map = {}  # taskId -> (userId, priority)
        self.sorted_tasks = []  # sorted list of (priority, taskId)

        for userId, taskId, priority in tasks:
            self.task_map[taskId] = (userId, priority)
            bisect.insort(self.sorted_tasks, (priority, taskId))

    def add(self, userId, taskId, priority):
        """
        :type userId: int
        :type taskId: int
        :type priority: int
        :rtype: None
        """
        self.task_map[taskId] = (userId, priority)
        bisect.insort(self.sorted_tasks, (priority, taskId))

    def edit(self, taskId, newPriority):
        """
        :type taskId: int
        :type newPriority: int
        :rtype: None
        """
        if taskId in self.task_map:
            userId, oldPriority = self.task_map[taskId]
            index = bisect.bisect_left(self.sorted_tasks, (oldPriority, taskId))
            if index < len(self.sorted_tasks) and self.sorted_tasks[index] == (oldPriority, taskId):
                self.sorted_tasks.pop(index)
            self.task_map[taskId] = (userId, newPriority)
            bisect.insort(self.sorted_tasks, (newPriority, taskId))

    def rmv(self, taskId):
        """
        :type taskId: int
        :rtype: None
        """
        if taskId in self.task_map:
            userId, priority = self.task_map[taskId]
            index = bisect.bisect_left(self.sorted_tasks, (priority, taskId))
            if index < len(self.sorted_tasks) and self.sorted_tasks[index] == (priority, taskId):
                self.sorted_tasks.pop(index)
            del self.task_map[taskId]

    def execTop(self):
        """
        :rtype: int
        """
        if not self.sorted_tasks:
            return -1
        priority, taskId = self.sorted_tasks.pop()  # get task with highest priority
        userId, _ = self.task_map.pop(taskId)
        return userId

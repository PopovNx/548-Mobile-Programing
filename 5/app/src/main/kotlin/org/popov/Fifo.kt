package org.popov

interface Queue<T : Any> {
    fun enqueue(element: T): Boolean
    fun dequeue(): T?
    val count: Int
        get
    val isEmpty: Boolean
        get() = count == 0

    fun peek(): T?
}


class SuperQueueueueue<T : Any> : Queue<T> {
    private val internalList: ArrayList<T> = ArrayList()

    override val count: Int
        get() = internalList.size

    override val isEmpty: Boolean
        get() = count == 0

    override fun enqueue(element: T): Boolean {
        try {
            internalList.add(element)
            return true
        } catch (_: OutOfMemoryError) {
            return false
        }

    }

    override fun dequeue(): T? {
        if (isEmpty) return null
        return internalList.removeAt(0)
    }

    override fun peek(): T? {
        if (isEmpty) return null
        return internalList[0]
    }
}


fun main() {
    val testqueue: Queue<Int> = SuperQueueueueue()

    require(testqueue.enqueue(1))
    require(testqueue.enqueue(2))
    require(testqueue.enqueue(3))

    require(testqueue.count == 3)
    require(!testqueue.isEmpty)
    require(testqueue.peek() == 1)
    require(testqueue.dequeue() == 1)
    require(testqueue.dequeue() == 2)
    require(testqueue.dequeue() == 3)
    require(testqueue.isEmpty)

    require(testqueue.dequeue() == null)
    require(testqueue.peek() == null)
    require(testqueue.count == 0)
    require(testqueue.isEmpty)

    require(testqueue.enqueue(123123))
    require(testqueue.peek() == 123123)
    require(testqueue.dequeue() == 123123)
}
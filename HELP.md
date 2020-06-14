# 一.分类概览
## 并发安全（线程安全）
### 底层角度
1. 互斥同步
- 各种互斥同步锁
    - synchronized
    - ReentryLock
    - ReadWriteLock
    - ...
- 同步工具类
    - Collections.synchronizedList(New ArrayList<E>())
    - Vector  
2. 非互斥同步
    - 6种原子类
        - 基本原子类
          - AtomicInteger:整型原子类
          - AtomicLong:长整型原子类
          - AtomicBoolean:布尔型原子类
        - Atomic*Array数组类型的原子类(数组里的元素都可以保证原子性)
          - AtomicInterArray:整型数组原子类
          - AtomicLongArray:长整形数组原子类
          - AtomicReferenceArray:引用类型数组原子类
        - Atomic*Reference引用类型原子类
          - AtomicReference:引用类型原子类
          - AtomicStampedReference:引用类型原子类升级,带时间戳,可以解决ABA问题
          - AtomicMarkableReference
        - Atomic*FieldUpdater升级原子类
          - 用Atomic*FieldUpdater等升级自己的变量
          - AtomicIntegerFieldUpdaterL原子更新整型字段的更新器
          - AtomicLongFieldUpdater:原子更新长整型字段更新器
        - Adder加法器(JDK1.8加入)
          - LongAdder
          - DoubleAdder
        - Accumulator累加器(JDK1.8加入)
          - LongAccumulator
          - DoubleAccumulator
            
3. 结合互斥和非互斥同步
    - 并发容器
        - ConcurrentHashMao(结合CAS和Synchronize)
        - CopyOnWriteArraylist
        - 并发队列
            - 阻塞队列(和线程池关系紧密)
                1. ArrayBlockingQueue
                2. LinkedBlockingQueue
                3. PriorityBlockingQueue
                4. SynchronousQueue
                5. DelayedQueue
                6. TransferQueue
                7. ...
            - 非阻塞队列
                - ConcurrentLinkedQueue
        - ConcurrentSkipListMap和ConcurrentSkipListSet
4. 无同步方案,不可变
    - final关键字
    - 线程封闭
        - ThreadLocal
        - 栈封闭
### 使用者角度
1. 避免共享变量
    - 线程封闭
        -  TreadLocal
        -  栈封闭
2. 共享变量,但是加以限制
    - 互斥同步
        - 使用各种互斥同步锁
          - synchronized
          - Lock接口的相关类
    - final关键字        
3. 使用成熟的工具类
    - 线程安全的并发容器
        - ConcurrentHashMap
        - CopyOnWriteArrayList
        - 并发队列
        - ConcurrentSkipListMap和ConcurrentSkipListSet
        - 使用同步工具类
    - atomic包,原子类    
## 线程管理
   - 线程池相关类
     - Executor
     - Executors
     - ExecutorService
     - 常见线程池
       - FixedTheadPool
       - CachedThreadPool
       - ScheduledThreadPool
       - SingleTheadPool
       - ForkJoinPool
       - ...
    
   - 能获取子线程的运行结果
     - Callable
     - Future
     - Future Task
     - ...
## 线程协作
  - CountDownLatch
  - CycliBarrier
  - Semaphore
  - Condition
  - Exchanger
  - Phaser
  - AQS
  - ...
---

# 二.线程池
## 为什么要用线程池
### 问题
1. 反复创建线程开销大
2. 过多的线程会占用太多内存
### 好处
1. 加快相应速度
2. 合理利用CPU和内存资源
3. 统一管理
### 使用场合
1. 服务器接收到大量请求,tomcat和netty内部都使用了线程池
2. 开发中,如果需要创建5个以上的线程,用线程池效率较高
## 创建和停止线程池
1.  构造函数的参数
    1. corePoolSize(int):核心线程数,通常会一直存活
    2. maxPoolSize(int):最大线程数
    3. keepAliveTime(long): 线程保持存活的最长时间(默认针对多余核心数量的线程)
    4. workQueue(Blocking Queue):任务存储队列
    5. thredFactory(TreadFactory):线程工厂
    6. Handler(RejectedExecutionHandler):拒绝处理器
2.  线程池应该手动创建还是自动创建
3.  线程池里面的线程数量设置为多少才合适 
4.  如何停止线程池
##线程里的线程数量设置成多少
1. CPU密集型(加密,计算hash等):最佳线程数为CPU核心数的1~2倍
2. 耗时IO(读写数据库,文件,网络读写等):参考Brain Goetz推荐的计算方法`线程数=CPU核心数*(1+平均等待时间/平均工作时间)
3. 实际以压测为准
##比较常见四种线程池的构造函数参数

 |Parameter| FixedThreadPool | CachedThreadPool |ScheduledThreadPool | SingleThreadPool|
 |-|-|-|-|-|
 |corePoolSize|constructor-arg|0|constructor-arg|1|
 |maxPoolSize|same as corePoolSize|Integer.MAX_VALUE|Integer.MAX_VALUE|1|
 |keepAliveTime|0 seconds|60 seconds|0 seconds|0 seconds|

 ## 阻塞队列分析
 1. FixedThreadPool和SingleThreadExecutor的Queue是LinkedBlockingQueue(无限大)
 2. CachedThreadPool使用的Queue是SynchronousQueue(不存储)
 3. ScheduledThreadPool用的DelayedWorkQueue
 4. JDK8加入workStealingPool线程池

 ## 停止线程
 1. shutdown():并不会马上停止,会把队列中等待的任务和正在执行的任务全部执行完,才会停止线程池,但是不会再接收新的任务
 2. isShutdown():判断线程池是否进入停止状态
 3. isTerminated():判断线程是否完全停止
 4. awaitTermination():测试线程池在未来几秒会不会停止,返回一个boolean值
 5. shutdownNow():立刻停止线程池,返回队列和正在执行的任务的列表

## 任务太多怎么拒绝
1. 拒绝时机
   1. 当Executor关闭时,新任务会被拒绝
   2. 当线程池所有线程都在执行任务,队列里面任务也满了,也会拒绝
2. 拒绝策略
   1. AbortPolicy:直接拒绝,抛出异常
   2. DiscardPolicy:默默丢弃,不会通知
   3. DiscardOldestPolicy:丢弃最老的
   4. CallerRunsPolicy:让提交任务的线程(一般是主线程)去执行任务,负反馈机制,给线程池缓冲时间
## 钩子算法
- 在任务执行前后加日志,统计之类的
- 代码演示:PauseableThreadPool.java

## 线程池组成部分
1. 线程池管理器
2. 工作线程
3. 任务队列(blocking queue)
4. 任务接口(Task)

## Executor家族
![1](src/main/resources/课程图片/哪个是线程池.png)

## 线程池状态
1. Running:接收新任务并处理排队任务
2. ShutDown:不接受新任务,但是处理排队任务
3. Stop:不接受新任务,也不处理排队任务,并且中断正在进行的任务
4. Terminated:terminate()执行完成
5. Tidying:所有任务都已经终止,workerCount为0,然后会运行terminate钩子方法

## 使用线程池的注意点
1. 避免任务堆积
2. 避免线程数过度增加
3. 排查线程泄露
----
# 三.ThreadLocal
## 两大使用场景
1. 每个线程需要一个独享的对象(通常是工具类,典型的包括SimpleDateFormat和Random,因为他们线程不安全)
    - 每个Thread内有自己的实例副本,不共享
    - 代码演示ThreadLocalNormalUsage00.java~ThreadLocalNormalUsage05.java
2. 每个线程内需要保存全局的变量(比如拦截器中获取用户信息),可以让不同方法直接使用,避免传递参数的麻烦
    - 遇到的问题:![2](src/main/resources/课程图片/ThreadLocal演进过程/6.png)
        - 每一次业务传递都要传递用户信息
    - 解决方案1:![3](src/main/resources/课程图片/ThreadLocal演进过程/7.png)
        - 图中才用静态的UserMap对象存储用户信息,可以如果涉及到多线程,是无法保证线程安全的
    - 解决方案1遇到的问题:![4](src/main/resources/课程图片/ThreadLocal演进过程/8.png)
        - 这种情况下可以用Synchronize或者ConcurrentHashMap来解决,但是都会对性能造成影响
    - 最终解决方案:使用ThreadLocal
        - 用ThreadLocal保存一些业务内容(用户权限信息,用户名,userId等)
        - 这些信息在同一个线程内相同,在不同的线程内使用的业务内容是不相同的
        - 在线程生命周期里,都通过这个静态ThreadLocal实例的get()获取之前set过的那个对象,避免了对象作为传参的麻烦
        - 强调的是同一个请求(同一个线程里)不同方法的共享
        - 不需要重写initialValue()方法,但必须手动调用set()方法
3. 代码见[ThreadLocalNormalUsage06.java](src/main/java/com/lyming/threadlocal/ThreadLocalNormalUsage06.java)
4. 总结
    - ThreadLocal的两大作用
        1. 让某个需要用到的对象线程隔离(每个线程都有自己独立的对象)
        2. 在任何方法中都可以轻松获取到该对象
    - 根据对象的生成时机不同,选择initialValue()或者set()保存对象
        1. 在ThreadLocal第一次get的时候把对象给初始化出来,对象初始化受我们自己控制用`initialValue()`
        2. ThreadLocal保存的对象不由我们控制,比如由拦截器控制就用`set()`
    - ThreadLocal带来的好处
        1. 达到线程安全
        2. 不需要加锁,提高执行效率
        3. 更加高效利用内存,节省开销
        4. 避免传参的繁琐,降低耦合
## 原理
1. 区分Thread,ThreadLocal,ThreadMap
  ![5](src/main/resources/课程图片/ThreadLocal原理图.png)
  在每个Thread中都持有一个ThreadMap对象
2. 常用方法
    - T initialValue():
        - 该方法会返回当前线程对应的初始值,这是一个延迟加载的方法,只有调用get()的时候才会触发;
        - 但是如果手动set,就不会调用initialValue();
        - 通常情况下,每个线程最多只调用一次该方法,除非调用了remove(),再次调用get()还是会调用initialValue()
        - 如果不重写initialValue(),默认会返回null,一般都使用匿名内部类来重写initialValue()方法
    - void set(T t):为该线程设置一个新值
    - T get():得到这个线程对应的Value,如果首次调用,则会调用initialValue()方法来得到该Value
    - void remove():删除对应这个线程的值
    -  **需要注意的是**保存的东西实际是保存在线程当中,而不是ThreadLocal中,通过源码可以知道,ThreadLocalMap是Thread的一个成员变量,而TreadLocal的Set方法,实际调用的是ThreadLocalMap的set(ThreadLocal local,T value)方法
    - ThreadLocalMap和HashMap很相似,有一点不同就是解决hash冲突的算法,常规的hashmap会采用拉链法,长度>8采用红黑树,而ThreadLocalMap则是采用线性探测法,将冲突的元素赋值给下一个空的内存中
## 需要注意的点
1. 内存泄露(某个内存不再使用,但是占用的内存不能被回收)
    1. 内存定义的特点:ThreadLocalMap中的entry继承WeakReference,是弱引用
    ```java
               static class Entry extends WeakReference<ThreadLocal<?>> {
                   /** The value associated with this ThreadLocal. */
                   Object value;
       
                   Entry(ThreadLocal<?> k, Object v) {
                       super(k);
                       value = v;
                   }
               }
    ```
   弱引用的特点是,如果这个对象只被弱引用关联(没有任何强引用关联),那么这个对象可以被GC回收.  
   所以可以看出ThreadLocalMap中的每个Entry都是一个对key的弱引用,同时,entry都包含了一个对value的强引用
   2. 正常情况下,当线程终止,保存在ThreadLocal中的value会被垃圾回收,因为没有任何强引用了
   3. 但是,如果线程不终止(比如线程需要保持很久,线程池里面的核心线程,他们是同一个线程反复被使用),那么key对应的value就不会被回收,因为有以下的调用链  
     `Thread-->ThreadLocalMap-->Entry(key为null)-->Value`  
     这样,value和Thread之间存在强引用链路,所以导致Value无法被回收,就会导致OOM  
     JDK已经考虑到这个问题,所以在set,remove,rehash方法中会扫描key为null的Entry,并把对应的value设置为null,这样value对象就可以被回收
     ```
       private void resize() {
           Entry[] oldTab = table;
           int oldLen = oldTab.length;
           int newLen = oldLen * 2;
           Entry[] newTab = new Entry[newLen];
           int count = 0;

           for (int j = 0; j < oldLen; ++j) {
               Entry e = oldTab[j];
               if (e != null) {
                   ThreadLocal<?> k = e.get();
                   if (k == null) {
                       e.value = null; // Help the GC
                   } else {
                       int h = k.threadLocalHashCode & (newLen - 1);
                       while (newTab[h] != null)
                           h = nextIndex(h, newLen);
                       newTab[h] = e;
                       count++;
                   }
               }
           }
     ```  
     但是这样还不够,因为这样的前提是调用这些方法来触发设置Value为null,如果一个ThreadLocal不被使用,那么实际上set,remove,rehash这些方法也不会被调用了,如果同时线程又不停止,那么调用链就一直存在,还是会导致内存泄露.
   4. 如何避免内存泄露(阿里规约)  
     调用remove方法,就会删除对用的entry对象,可以避免内存泄露,所以使用完ThreadLocal之后,手动调用remove方法
2. ThreadLocal空指针的问题  
    代码见[ThreadLocalNPE.java](src/main/java/com/lyming/threadlocal/ThreadLocalNPE.java),实际上装箱拆箱遇到的问题,不是ThreadLocal本身的问题
3. 共享对象  
    如果在每个线程内ThreadLocal.set()进去的东西本来就是多线程共享的同一个对象,比如static对象,那么get()到的还是这个共享对象,会有并发访问问题,所以static对象不要用ThreadLocal
4. 优先使用框架的支持,而不是自己创造  
    例如在Spring中,如果可以使用RequestContextHolder那么就不需要自己维护ThreadLocal,因为自己可能会忘记调用remove方法,造成内存泄露  
    可以看看DateTimeContextHolder类的源码
5. 每次HTTP请求都对应一个线程,线程之间相互隔离,这就是ThreadLocal的典型应用场景
----
# 四.各种锁
## Lock接口
1. 简介,地位,作用  
    - 锁是一种工具,用于控制对共享资源的方法  
    - Lock和synchronized,这两个是最常见的锁,他们都可以达到线程安全的目的,但在使用和功能上有较大的不同  
    - Lock并不是来替代synchronized的,而是当使用synchronized不适合或者不满足要求的时候,来提供高级功能
    - Lock接口最常见的实现类是`ReentrantLock`
    - 通常情况下,Lock只允许一个线程来访问这个共享资源.不过有些时候,一些特殊的实现也可以允许并发访问,比如ReadWriteLock中的`ReadLock`
2. 为什么synchronized不够用,为什么要Lock
    - 效率低:锁的释放情况少(1.完全执行完毕释放锁 2.异常,JVM来释放锁);试图获得锁时不能设置超时;不能中断一个正在试图获得锁的线程
    - 不够灵活(读写锁更灵活):加锁和释放的时间单一,每个锁仅有单一的条件(某个对象),可能是不够的
    - 无法知道是否成功获取到锁
3. 方法介绍
    - 四个常用方法:lock(),tryLock(),tryLock(long time,TimeUnit unit)和lockInterruptibly()
    - lock()就是最普通的获取锁,如果锁已经被其他线程获取,则进行等待  
        Lock不会像Synchronized一样在异常时自动释放锁,所以最佳实践是,在finally中释放锁,以保证发生异常时锁一定被释放
    - lock()方法不能被中断,这会带来很大的隐患:一旦陷入死锁,lock()就会陷入永久等待
    - tryLock()用来尝试获取锁,如果当前锁没有被其他线程占用,则获取成功,返回true,否则返回false,因此可以根据返回的结果来决定后续程序的行为
    - tryLock(long time,TimeUnit unit):超时就放弃
    - lockInterruptibly():相当于tryLock(long time,TimeUnit unit)把超时时间设置为无限,在等待锁的过程中,线程可以被中断
4. 可见性保证
    - Lock的加解锁和Synchronize的有同样的内存语义,也就是说,下一个线程加锁后可以看到前一个线程解锁前发生的所有操作(happens-before)
## 锁的分类
1. 分类是从不同角度去看的,并不是互斥的,也就是说一个锁可以同属多种类型,比如ReentrantLock既是互斥锁,又是可重入锁
2. 分类
    - 线程要不要锁住同步资源:锁住-->悲观锁;不锁住-->乐观锁
    - 多线程是否共享一把锁:可以-->共享锁;不可以-->独占锁
    - 多线程竞争时,是否排队:排队-->公平锁;先尝试插队,插队失败再排队-->非公平锁
    - 同一个线程是否可以重复获取同一把锁:可以-->可重入锁;不可以-->不可重入锁
    - 是否可以中断:可以-->可中断锁;不可以:非可中断锁
    - 等待锁的过程:自旋(不停的尝试)-->自旋锁;阻塞-->非自旋锁
## 乐观锁(又称为非互斥同步锁)和悲观锁(又称为互斥同步锁)
1. 悲观锁的劣势:
    - 阻塞和唤醒带来的性能劣势
    - 永久阻塞:如果持有锁的线程被永久阻塞,比如遇到了无限循环,死锁等活跃性问题,那么等待改线程释放锁的线程也将永远得不到执行
    - 优先级反转:优先级低的线程拿到锁,如果不释放,优先级高的线程也只能干等
2. 悲观锁考虑的太详细,为了保证结果的正确性,会在每次获取并修改资源时,把数据锁住,来确保万无一失
    - 最典型的悲观锁就是Synchronized和Lock相关类,Synchronized优化后前面有乐观的过程,但是总的还是悲观锁
    - ![6](src/main/resources/课程图片/悲观锁.jpg)
    - ![7](src/main/resources/课程图片/悲观锁2.jpg)
3. 乐观锁
    - 认为自己在处理操作的时候不会有其他线程来干扰,所以并不会锁住被操作对象
    - 在更新的时候,去对比当前线程修改的期间数据有没有被其他人改变过:如果没有改变,则说明真的只有当前线程在操作,那就正常去修改数据
    - 如果数据和线程一开始拿到的不一样了,就说明别的线程在这段期间修改过数据,那就可以放弃修改,报错,重试等策略
    - 乐观锁的实现一般都是利用`CAS`算法来实现的,CAS(Compare and Swap)的核心就是在一个原子操作内,把数据对比而且交换,在此期间不被打断
    - ![8](src/main/resources/课程图片/乐观锁1.jpg)
    - ![9](src/main/resources/课程图片/乐观锁2.jpg)
    - ![10](src/main/resources/课程图片/乐观锁3.jpg)
    - ![11](src/main/resources/课程图片/乐观锁4.jpg)
    - ![12](src/main/resources/课程图片/乐观锁5.jpg)
    - 典型的例子就是原子类,并发容器,包括Git
    - 对数据库而言,`select for update`就是悲观锁,加入version概念就是乐观锁
4. 开销对比
    - 悲观锁的原始开销要高于乐观锁,但是一劳永逸
    - 乐观锁一开始的开销要比悲观锁小,但是如果自旋时间很长或者不停重试,那么消耗的资源也会越来越多
5. 使用场景
    - 悲观锁:适合并发`写入`多的情况,适用于临界区持锁时间比较长的情况,悲观锁可以避免大量的无用的自旋等操作,典型情况:
        - 临界区有IO操作
        - 临界区代码复杂或者循环量大
        - 临界区竞争非常激烈
    - 乐观锁:适合并发`写入`少,大部分是读取的场景,不加锁就能让读取性能大幅提高
## 可重入锁和非可重入锁,以ReentrantLock为例(重点)
1. ReentrantLock普通用法1:预订电影院座位  
    ![13](src/main/resources/课程图片/ReentrantLock使用案例.png)  
    ![14](src/main/resources/课程图片/ReentrantLock使用案例2.png)  
    ![15](src/main/resources/课程图片/ReentrantLock使用案例3.png)  
    代码见[CinemaBookSeat.java](src/main/java/com/lyming/lock/reentrantlock/CinemaBookSeat.java)
2. ReentrantLock普通用法2:打印字符串  
    代码见[LockDemo.java](src/main/java/com/lyming/lock/reentrantlock/LockDemo.java)
3. 可重入性质
    - 什么是可重入?==>也称为递归锁,同一个线程可以多次获取同一把锁,不需要先释放再申请
    - 好处:避免死锁;提高了封装性
    - 代码见[GetHoldCount.java](src/main/java/com/lyming/lock/reentrantlock/GetHoldCount.java)  
      [RecursionDemo.java](src/main/java/com/lyming/lock/reentrantlock/RecursionDemo.java)
4. 源码对比:可重入锁ReentrantLock以及非可重入锁ThreadPoolExecutor的Worker类  
    ![16](src/main/resources/课程图片/可重入锁和不可重入锁源码对比.png)
5. ReentrantLock的其他方法介绍(一般开发和调试的时候使用,上线后用的不多)
    - isHeldByCurrentThread:判断锁是否被当前线程持有
    - getQueueLength:返回当前正在等待这把锁的队列有多长
## 公平锁和非公平锁
1. 什么是公平和非公平
    - 公平指的是按照线程请求的顺序,来分配锁
    - 非公平指的是,`不完全`按照请求的顺序,`在一定情况下`可以插队,所以,非公平也同样不提倡插队,非公平指的是,`在合适的时机`插队,而不是盲目插队
    - 什么是合适的时机?  
      比如A,B,C三个线程,A持有锁,B请求这个锁,但是因为A还没有释放,所以B就去休息等待唤醒,当A执行完,释放锁,会唤醒B,但是这个时候C来请求这个锁,因为C一直没有休息,所以完全可能在B被唤醒之前,就持有了锁,并且执行
2. 为什么要有公平锁
    - java默认策略是非公平,上面的例子可以看出,非公平是为了提高效率,主要是`避免唤醒带来的空档期`,提高吞吐量,唤醒也是有开销的,
3. 公平的情况(以ReentrantLock为例)
    - ReentrantLock默认是非公平锁,但是创建的时候,参数选填true,那么就变成公平锁
4. 不公平的情况(以ReentrantLock为例)
5. 代码演示公平和非公平的效果
    - 代码见[FairLock.java](src/main/java/com/lyming/lock/reentrantlock/FairLock.java)
6. 特例(不遵守公平的情况)
    - 有一个方法叫tryLock(),它不遵守设定的公平的规则
    - 例如,当有线程执行tryLock()的时候,一旦有线程释放了锁,那么这个正在tryLock()的线程就能获取到锁,即使在它之前有其他线程在队列里面等待
7. 对比公平和不公平各自的优缺点
    ||优势|劣势|
    |-|-|-|
    |公平锁|个线程公平平等,每个线程在等待一段时间后,总有执行的机会|更慢,吞吐量小|
    |不公平锁|更快,吞吐量大|有可能产生线程饥饿,也就是某些线程在长时间内,始终得不到执行|
8. 源码分析
    ![对比公平锁和非公平锁源码.png](src/main/resources/课程图片/对比公平锁和非公平锁源码.png)
## 共享锁和排他锁:以ReentrantReadWriteLock读写锁为例(重点)
1. 什么是共享锁和排他锁
    - 排它锁又称为`独占锁`,`独享锁`,比如Synchronized
    - 共享锁,又称为`读锁`,获得共享锁之后,可以查看,但是无法修改和删除数据,其他线程此时也可以获取到共享锁,也可以查看但无法修改和删除数据
    - 共享锁和排他锁最典型的例子就是读写锁`ReentrantReadWriteLock`,其中读锁是共享锁,写锁是排他锁
2. 读写锁的作用
3. 读写锁的规则
4. ReentrantReadWriteLock具体用法
5. 读锁和写锁的交互方式
6. 总结
## 自旋锁和阻塞锁
## 可中断锁:可以相应中断的锁
## 锁优化
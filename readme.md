<h2>Time Slicing  </h2>
<ul>
<li>Time Slicing is also known as time-sharing or time division.</li>
<li>It is a technique used in multitasking operating systems to allow multiple thread to process to share a single CPU for execution.</li>
<li>Available CPU time  is sliced into small-time interval, to attempt to make some progress on the task it has to do.</li>
<li>Whether it complete it`s task or not in that time slice doesn't matter to the thread management system. </li>
<li>When the time is up, it has to yield to another thread and wait until it`s turn again. </li>
<li>Unfortunately,When your thread are sharing heap memory things can change during that wait. </li>
</ul>


<h2>Java Memory Model</h2>
<p>The java memory model, is a <b>specification</b> that defines some rules and behaviours for threads, to help control and manage shared access to data and operation</p>

<h3>Atomicity Of Operation</h3>
<p>Few operations are truly atomic</p>

<h3>Sychronization</h3>
<p>It is a process of controlling thread's access to shared resources.</p>